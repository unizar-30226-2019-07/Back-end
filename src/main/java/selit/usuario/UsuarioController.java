package selit.usuario;

import static selit.security.Constants.HEADER_AUTHORIZACION_KEY;
import static selit.security.Constants.SUPER_SECRET_KEY;
import static selit.security.Constants.TOKEN_BEARER_PREFIX;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioRepository;

import io.jsonwebtoken.Jwts;

@RestController   
@RequestMapping(path="/users") 
public class UsuarioController {
	
	@Autowired
	public 
	static UsuarioRepository usuarios;
	
	
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsuarioController(UsuarioRepository usuarios, BCryptPasswordEncoder bCryptPasswordEncoder) {
		UsuarioController.usuarios = usuarios;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	
	@PostMapping(path="")
	public @ResponseBody String anyadirUsuario (@RequestBody Usuario usuario) {
		
		// El objeto usuario pasado en el cuerpo de la peticion tiene los 
		// atributos email, password y first_name. El resto de los atributos no 
		// nulos se deben rellenar.

		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
		usuario.setLast_name("");
		usuario.setStatus("activa");
		usuario.setTipo("usuario");
		usuario.setRating(0);
		usuario.setPosX((float) 0.0);
		usuario.setPosY((float) 0.0);
		
		// Se guarda al usuario.
		usuarios.save(usuario);
		
		// Se contesta a la peticion con un mensaje de exito.
		return "Nuevo usuario creado";
	}
	
	@GetMapping(path="")
	public @ResponseBody Iterable<Usuario> obtenerUsuarios(HttpServletRequest request) {
		//Obtengo que usuario es el que realiza la petición
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();

		// Se devuelve con la lista de usuarios en la base de datos.
		return usuarios.findAll();
	}
	
	
	@GetMapping(path="/{user_id}")
	public @ResponseBody Optional<Usuario> obtenerUsuario(@PathVariable String user_id) {
		
		// Se devuelve el usuario con el id indicado en la ruta.
		return usuarios.findById(Long.parseLong(user_id));
	}
	
	@PutMapping(path="/{user_id}")
	public @ResponseBody String actualizarUsuario(@PathVariable String user_id) {
		
		// Se actualiza el usuario.
		
		return "OK";
	}
	
	@DeleteMapping(path="/{user_id}")
	public @ResponseBody String eliminarUsuario(@PathVariable String user_id) {
		
		// Se elimina el usuario
		usuarios.deleteById(Long.parseLong(user_id));
		
		// Se devuelve mensaje de confirmacion.
		return "OK";
	}
	
	// NO LO PIDEN, ESTA DE EJEMPLO.
	
	@GetMapping(path="/nombre")
	public @ResponseBody Iterable<Usuario> obtenerUsuarios(@RequestParam String nombre) {
		
		// Se devuelve con la lista de usuarios cuyo nombre es nombre de la
		// base de datos.
		return usuarios.buscarPorNombre(nombre);
	}
	
}
