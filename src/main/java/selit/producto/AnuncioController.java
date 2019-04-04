package selit.producto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static selit.security.Constants.HEADER_AUTHORIZACION_KEY;
import static selit.security.Constants.SUPER_SECRET_KEY;
import static selit.security.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioController;
import selit.usuario.UsuarioRepository;
import selit.producto.AnuncioRepository;
import selit.security.TokenCheck;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(path="/products")
public class AnuncioController {

	@Autowired
	public
	static AnuncioRepository anuncios;
	
	public AnuncioController(AnuncioRepository productos) {
		AnuncioController.anuncios = productos;
	}

	@PostMapping(path="")
	public @ResponseBody String anyadirAnuncio (@RequestBody Anuncio anuncio, HttpServletRequest request, HttpServletResponse response) throws IOException { 

		// Se obtiene el correo del usuario que ha anyadido el producto para 
		// encontrar su identificador y meterlo en la tabla de anuncios como
		// el creador.
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = UsuarioController.usuarios.buscarPorEmail(user);
		anuncio.setId_owner(u.getIdUsuario());
		
		//Se comrprueba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
		
			// Se definen los valores por defecto de las columnas obligatorias.
			anuncio.setNfav(0);
			anuncio.setNvis(0);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
			LocalDateTime now = LocalDateTime.now();  
			anuncio.setPublicate_date(dtf.format(now).toString());
			if (anuncio.getTitle() == null) {
				anuncio.setTitle("");
			}
			anuncio.setLocation("ubicacion");
			 
			// Se guarda el anuncio.
			anuncios.save(anuncio);
	
			// Se contesta a la peticion con un mensaje de exito.
			response.setStatus(201);
			return "Nuevo producto creado";
		} else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
	}
	
	@DeleteMapping(path="/{product_id}")
	public @ResponseBody String eliminarAnuncio(@PathVariable String product_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		
		Usuario u = new Usuario();
		u = UsuarioController.usuarios.buscarPorEmail(user);
		
		// Se compreba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
	
			// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
			Optional<Anuncio> anuncio = anuncios.findById(Long.parseLong(product_id));
			if ( !anuncio.isPresent() ) {
				
				// Se devuelve error 404.
				response.sendError(404, "El producto con id "+product_id+" no existe");
				
				return null;
				
			} else {
				
				// Se comprueba que el usuario que realiza la peticion de eliminar es un administrador
				// o es el propietario del producto.
				Anuncio anuncio2 = anuncio.get();
				if (u.getTipo().equals("administrador") || anuncio2.getId_owner() == u.getIdUsuario()) {
					
					// Se elimina el producto.
					anuncios.deleteById(Long.parseLong(product_id));
					
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
