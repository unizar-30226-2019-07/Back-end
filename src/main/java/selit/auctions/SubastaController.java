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
import selit.usuario.UsuarioAux;
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
				
				Subasta subasta = new Subasta(dtf.format(now).toString(),subastaAux.getDescription(),subastaAux.getTitle(), subastaAux.getEndDate(), subastaAux.getStartPrice(),u.getIdUsuario(),subastaAux.getCategory(),
								subastaAux.getLocation().getLat(),subastaAux.getLocation().getLng(),"en venta",subastaAux.getCurrency()); 
				
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
	
	@GetMapping(path="/{auction_id}")
	public @ResponseBody SubastaAux obtenerSubasta(@PathVariable String auction_id, @RequestParam (name = "lat", required = false) String lat,
			@RequestParam (name = "lng", required = false) String lng, HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
		Optional<Subasta> subasta = subastas.findById(Long.parseLong(auction_id));
		if ( !subasta.isPresent() ) {
			
			// Se devuelve error 404.
			response.sendError(404, "La subasta con id "+auction_id+" no existe");
			
			return null;
			
		} else {
			Subasta aaux = subasta.get();
			Location loc = new Location(aaux.getPosX(),aaux.getPosY());
			Usuario userFind = usuarios.buscarPorId(aaux.getId_owner().toString());
			userFind = usuarios.buscarPorEmailCommon(userFind.getEmail());
			Location loc2 = new Location(userFind.getPosX(),userFind.getPosY());
			
			UsuarioAux rUser = new UsuarioAux(userFind.getIdUsuario(),userFind.getGender(),userFind.getBirth_date(),
					loc2,userFind.getRating(),userFind.getStatus(),userFind.getPassword(),userFind.getEmail(),
					userFind.getLast_name(),userFind.getFirst_name(),userFind.getTipo(),new Picture(userFind.getIdImagen()));
			
			SubastaAux rSubasta;
			
			rSubasta = new SubastaAux(aaux.getidSubasta(),aaux.getPublicate_date(),aaux.getDescription(),
					aaux.getTitle(),loc,aaux.getStartPrice(),aaux.getFecha_finalizacion(),aaux.getCategory(),
					rUser);
			
			return rSubasta;
			
		}
		
	}
	
	@PutMapping(path="/{auction_id}")
	public @ResponseBody String actualizarSubasta(@PathVariable String auction_id, HttpServletRequest request,@RequestBody SubastaAux subasta, HttpServletResponse response) throws IOException { 

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
			// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
			Optional<Subasta> subasta2 = subastas.findById(Long.parseLong(auction_id));
			if ( !subasta2.isPresent() ) {
				
				// Se devuelve error 404.
				response.sendError(404, "La subasta con id "+auction_id+" no existe");
				
				return null;
				
			} else {
				// Se comprueba que el usuario que realiza la peticion de actualizar es un administrador
				// o es el propietario del producto.
				Subasta subasta3 = subasta2.get();
				
				if(subasta3.getStatus().equals("en venta")) {
					if (u.getTipo().equals("administrador") || subasta3.getId_owner() == u.getIdUsuario()) {
						
						// Se actualiza el producto.
						subastas.actualizarSubasta(subasta3.getPublicate_date(),subasta.getDescription(),subasta.getTitle(), 
								subasta.getLocation().getLat(),subasta.getLocation().getLng(),subasta.getStartPrice(),subasta.getCurrency(),
								subasta.getEndDate(), subasta.getOwner_id(),subasta.getCategory(),auction_id,subasta.getStatus());
						
						// Se devuelve mensaje de confirmacion.
						return "Anuncio actualizado";
						
					} else {
						
						// No es el administrador o el propietario del producto, se devuelve un error.
						String error = "You can't update this product.";
						response.sendError(402, error);
						return null;
					}
				}
				else {
					String error = "You can´t update this product, it has already been sold.";
					response.sendError(409, error);
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
