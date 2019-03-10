package main.java.producto;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // Clase controladora
@RequestMapping(path="/producto/*/") // URL's empiezan con /products
public class ProductoControlador {
	
	//@Autowired 
	//private ProductoRepositorio productos;

	@DeleteMapping(path="/")
	public @ResponseBody String borrarProducto () {
		
		// Eliminacion del producto
		
		return "Eliminacion del producto correcta o incorrecta";
	}
	
	@GetMapping(path="/")
	public @ResponseBody String obtenerProducto (@RequestParam String idUsuario) {
		
		// Obtencion de informacion del producto
		
		return "Informacion del producto";
	}
	
	@PostMapping(path="/pujar")
	public @ResponseBody String realizarPuja (@RequestParam String idUsuario, @RequestParam String cantidad) {
		
		// Realizacion de puja
		
		return "Realizacion de puja correcta o incorrecta";
	}

}
