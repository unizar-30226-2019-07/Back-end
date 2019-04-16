package selit.auctions;

import static selit.security.Constants.HEADER_AUTHORIZACION_KEY;
import static selit.security.Constants.SUPER_SECRET_KEY;
import static selit.security.Constants.TOKEN_BEARER_PREFIX;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioController;
import selit.usuario.UsuarioRepository;
import selit.verificacion.Verificacion;
import selit.verificacion.VerificacionRepository;
import selit.Location.Location;
import selit.mail.MailMail;
import selit.picture.Picture;
import selit.picture.PictureRepository;
import selit.producto.Anuncio;
import selit.producto.AnuncioAux;
import selit.security.TokenCheck;
import io.jsonwebtoken.Jwts;

@RestController   
@RequestMapping(path="/auctions"
		+ "") 
public class SubastaController {
	
	@Autowired
	public 
	UsuarioRepository usuarios;
	
	@Autowired
	private SubastaRepository subastas;	
	
	@Autowired public 
	PictureRepository pictures;
	
	@Autowired public 
	VerificacionRepository verificaciones;	

	
	public static BCryptPasswordEncoder bCryptPasswordEncoder;

	public SubastaController(UsuarioRepository usuarios, BCryptPasswordEncoder bCryptPasswordEncoder) {
		UsuarioController.usuarios = usuarios;
		UsuarioController.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PostMapping(path="")
	public @ResponseBody String anyadirSubasta(@RequestBody SubastaAux subastaAux, HttpServletRequest request, HttpServletResponse response) throws IOException { 

		// Se obtiene el correo del usuario que ha anyadido la subasta para 
		// encontrar su identificador y meterlo en la tabla de subasta como
		// el creador.
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		//Se comrprueba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
			if(subastaAux.getOwner_id().equals(u.getIdUsuario())) {
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
				LocalDateTime now = LocalDateTime.now();  
				
				Subasta subasta = new Subasta(dtf.format(now).toString(),subastaAux.getDescription(),subastaAux.getTitle(), subastaAux.getCloses(), subastaAux.getStartPrice(),u.getIdUsuario(),subastaAux.getCategory(),
								subastaAux.getLocation().getLat(),subastaAux.getLocation().getLng()); 
				
				// Se guarda la subasta.
				subasta = subastas.save(subasta);
				if (subastaAux.getMedia() != null) {
					for (Picture p: subastaAux.getMedia() ) {
						pictures.save(p);
					}
				}
				
				// Se contesta a la peticion con un mensaje de exito.
				response.setStatus(201);
				return "Nuevo subasta creada";
			}
			else {
				String error = "The user doesn't have enough permissions.";
				response.sendError(402, error);
				return null;
			}
			
		} 
		else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
	}

	@DeleteMapping(path="/{auction_id}")
	public @ResponseBody String eliminarSubasta(@PathVariable String auction_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		// Se compreba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
	
			// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
			Optional<Subasta> subasta = subastas.findById(Long.parseLong(auction_id));
			if ( !subasta.isPresent() ) {
				
				// Se devuelve error 404.
				response.sendError(404, "La subasta con id "+auction_id+" no existe");
				
				return null;
				
			} else {
				
				// Se comprueba que el usuario que realiza la peticion de eliminar es un administrador
				// o es el propietario del producto.
				Subasta subasta2 = subasta.get();
				if (u.getTipo().equals("administrador") || subasta2.getId_owner() == u.getIdUsuario()) {
					
					// Se elimina el producto.
					subastas.deleteById(Long.parseLong(auction_id));
					
					// Se devuelve mensaje de confirmacion.
					return "Anuncio eliminado";
					
				} else {
					
					// No es el administrador o el propietario del producto, se devuelve un error.
					String error = "You are not an administrator or the user is not you.";
					response.sendError(402, error);
					return null;
				}
			}
			
		} else {
			
			// El token es incorrecto.
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
		
	}
	
}
