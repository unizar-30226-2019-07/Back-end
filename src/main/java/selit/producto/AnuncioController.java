package selit.producto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import selit.Location.Location;
import selit.producto.AnuncioRepository;
import selit.security.TokenCheck;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(path="/products")
public class AnuncioController {

	@Autowired
	public
	AnuncioRepository anuncios;
	
	@Autowired
	public 
	UsuarioRepository usuarios;
	
	public AnuncioController(AnuncioRepository productos) {
		anuncios = productos;
	}

	@PostMapping(path="")
	public @ResponseBody String anyadirAnuncio (@RequestBody AnuncioAux anuncio, HttpServletRequest request, HttpServletResponse response) throws IOException { 

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
		
		//Se comrprueba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
		
			// Se definen los valores por defecto de las columnas obligatorias.
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
			LocalDateTime now = LocalDateTime.now();  

			Anuncio anun = new Anuncio(dtf.format(now).toString(),anuncio.getDescription(),anuncio.getTitle(),
							anuncio.getLocation().getLat(),anuncio.getLocation().getLng(),anuncio.getPrice(),
							anuncio.getCurrency(),0,0,u.getIdUsuario(),anuncio.getCategory(),"en venta"); 
			// Se guarda el anuncio.
			anuncios.save(anun);
	
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

	@GetMapping(path="/{product_id}")
	public @ResponseBody AnuncioAux obtenerAnuncio(@PathVariable String product_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Obtengo que usuario es el que realiza la petición
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
				
			}
			else {
				Optional<Usuario>  u2;
				Anuncio aaux = anuncio.get();
				Location loc = new Location(aaux.getPosX(),aaux.getPosY());
				if(u.getTipo().equals("administrador") || u.getIdUsuario().equals(anuncio.get().getId_owner())) {					
					u2 = usuarios.findById(u.getIdUsuario());							
				}
				else {
					u2 = usuarios.findUserCommon(u.getIdUsuario().toString());
				}
				AnuncioAux rAnuncio = new AnuncioAux(aaux.getPublicate_date(),aaux.getDescription(),
						aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
						aaux.getNfav(),aaux.getNvis(),u.getIdUsuario(),aaux.getCategory(),aaux.getStatus(),
						u2.get());
				return rAnuncio;
				
			}
			
		} 
		else {
			
			// El token es incorrecto.
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
	}
	
	@PutMapping(path="/{product_id}")
	public @ResponseBody String actualizarAnuncio(@PathVariable String product_id, 
						HttpServletRequest request, @RequestBody AnuncioAux anuncio, 
						HttpServletResponse response) throws IOException {
		//Obtengo que usuario es el que realiza la petición
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
			Optional<Anuncio> anuncio2 = anuncios.findById(Long.parseLong(product_id));
			if ( !anuncio2.isPresent() ) {
				
				// Se devuelve error 404.
				response.sendError(404, "El producto con id "+product_id+" no existe");
				
				return null;
				
			} else {
				// Se comprueba que el usuario que realiza la peticion de actualizar es un administrador
				// o es el propietario del producto.
				Anuncio anuncio3 = anuncio2.get();
				
				if(anuncio3.getStatus().equals("en venta")) {
					if (u.getTipo().equals("administrador") || anuncio3.getId_owner() == u.getIdUsuario()) {
						
						// Se actualiza el producto.
						anuncios.actualizarAnuncio(anuncio3.getPublicate_date(),anuncio.getDescription(),
								anuncio.getTitle(),anuncio.getLocation().getLat(),anuncio.getLocation().getLng(),
								anuncio.getPrice(),anuncio.getCurrency(),anuncio.getNfav(),
								anuncio.getNvis(),anuncio3.getId_owner(),anuncio.getCategory(),product_id,anuncio.getStatus());
						
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
