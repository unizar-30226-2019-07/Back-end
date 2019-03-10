package main.java.usuario;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // Clase controladora
@RequestMapping(path="/usuario/*/") // URL's empiezan con /usuario y seguido de /id_usuario
public class UsuarioControlador {
	
	//@Autowired 
	//private UsuarioRepositorio usuarios;
	
	@PostMapping(path="/calificaciones/id") // Solo peticiones de tipo POST
	public @ResponseBody String calificacionesId (@RequestParam String puntuacion, @RequestParam String texto
			, @RequestParam String sesion, @RequestParam String idProducto) {
		
		// Calificar un producto de un usuario
		
		return "Calificacion correcta o incorrecta";
	}
	
	@PostMapping(path="/informes") // Solo peticiones de tipo POST
	public @ResponseBody String informes (@RequestParam String informe, @RequestParam String sesion) {
		
		// Insertar informe a un usuario
		
		return "Insercion de informe correcta o incorrecta";
	}
	
	@PostMapping(path="/deseados") // Solo peticiones de tipo POST
	public @ResponseBody String insertarProductoDeseado (@RequestParam String idProducto, @RequestParam String fecha) {
		
		// Insertar producto deseado
		
		return "Insercion de producto a deseados correcta o incorrecta";
	}
	
	@DeleteMapping(path="/deseados/*/") // Solo peticiones de tipo DELETE
	public @ResponseBody String borrarProductoDeseado () {
		
		// Borrado producto deseado
		
		return "Borrado de producto deseado correcta o incorrecta";
	}
	
	@GetMapping(path="/deseados/") // Solo peticiones de tipo GET
	public @ResponseBody String obtenerProductoDeseados () {
		
		// Obtencion productos deseados
		
		return "Lista de productos deseados del usuario";
	}
	
	@PutMapping(path="/") // Solo peticiones de tipo PUT
	public @ResponseBody String modificarUsuario (@RequestParam String info) {
		
		// Modificar usuario
		
		return "Modificacion de usuario correcta o incorrecta";
	}
	
	@DeleteMapping(path="/") // Solo peticiones de tipo DELETE
	public @ResponseBody String borrarUsuario () {
		
		// Borrar usuario
		
		return "Borrado de usuario correcta o incorrecta";
	}

}
