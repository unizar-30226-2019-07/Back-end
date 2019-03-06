package main.java.hello;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // Clase controladora
@RequestMapping(path="/users") // URL's empiezan con /users
public class UserController {
	
	@Autowired 
	private UsuarioRepositorio usuarios;
	
	@PutMapping(path="/add") // Solo peticiones de tipo PUT
	public @ResponseBody String addNewUser (@RequestParam String nombre, String contrasenya,
		String correo) {
		
		Usuario u = new Usuario();
		u.setNombre(nombre);
		u.setContrasenya(contrasenya);
		u.setCorreo(correo);
		usuarios.save(u);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Usuario> getAllProducto() {
		// Devuelve JSON con usuarios
		return usuarios.findAll();
	}
	
	@GetMapping(path="/user")
	public @ResponseBody Optional<Usuario> getUsuario(@RequestParam Integer id) {
		// Devuelve JSON con un usuario
		return usuarios.findById(id);
	}
}
