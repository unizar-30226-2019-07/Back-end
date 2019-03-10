package main.java.hello;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // Clase controladora
@RequestMapping(path="/") // URL's empiezan con /
public class MainControlador {
	
	// Atributos
	
	//@Autowired
	//private UsuarioRepositorio usuarios;
	
	/*
	 * Falta poner cabeceras a todo
	 */
	@PostMapping(path="/registro") // Solo peticiones de tipo POST
	public @ResponseBody String registro (@RequestParam String nombre, @RequestParam String apellidos
			, @RequestParam String email, @RequestParam String contrasenya, @RequestParam String ubicacion
			, @RequestParam String sexo, @RequestParam String fecha) {
		
		// Registrar usuario
		
		return "Usuario registrado correctamente o incorrectamente";
	}
	
	@GetMapping(path="/sesion")
	public @ResponseBody String sesion (@RequestParam String email, @RequestParam String contrasenya) {
		
		// Obtener sesion
		
		return "Sesion";
	}
	
	@GetMapping(path="/productosEnVentaUsuario")
	public @ResponseBody String productosEnVentaUsuario (@RequestParam String sesion, @RequestParam String id) {
		
		// Obtener productos
		
		return "Productos del usuario "+id;
	}
	
	@GetMapping(path="/productos")
	public @ResponseBody String obtenerProductos () {
		
		// Obtener productos
		
		return "Lista de productos";
	}
	
	@PostMapping(path="/annadirObjeto")
	public @ResponseBody String insertarProducto () {
		
		// Insertar producto
		
		return "Insercion del producto correcta o incorrecta";
	}
	
	@PutMapping(path="/annadirProducto")
	public @ResponseBody String modificarProducto () {
		
		// Modificar producto
		
		return "Modificacion del producto correcta o incorrecta";
	}
	
	@PostMapping(path="/salaChat")
	public @ResponseBody String crearChat (@RequestParam String idUsuarioComprador, @RequestParam String idUsuarioVendedor
										, @RequestParam String idProducto) {
		
		// Insertar chat
		
		return "Creacion del chat correcta o incorrecta";
	}
}
