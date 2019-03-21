package selit.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioRepository;

@Controller   
@RequestMapping(path="/users") 
public class UsuarioControlador {
	
	@Autowired 
	private UsuarioRepository userRepository;
	
	@PostMapping(path="")
	public @ResponseBody String anyadirUsuario (@RequestBody Usuario usuario) {

		// El objeto usuario pasado en el cuerpo de la peticion tiene los 
		// atributos email, password y first_name. El resto de los atributos no 
		// nulos se deben rellenar.
		usuario.setLast_name("");
		usuario.setStatus("activa");
		usuario.setTipo("usuario");
		usuario.setPosX((float) 0.0);
		usuario.setPosY((float) 0.0);
		
		// Se guarda al usuario.
		userRepository.save(usuario);
		
		// Se contesta a la peticion con un mensaje de exito.
		return "Guardado";
	}
	
	@GetMapping(path="")
	public @ResponseBody Iterable<Usuario> obtenerUsuarios() {
		
		// Se devuelve con la lista de usuarios en la base de datos.
		return userRepository.findAll();
	}
	
}
