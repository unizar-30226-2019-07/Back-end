package main.java.chat;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // Clase controladora
@RequestMapping(path="/salaChat/*/") // URL's empiezan con /salaChat
public class ChatControlador {
	
	//@Autowired 
	//private ChatRepositorio chats;

	@DeleteMapping(path="/")
	public @ResponseBody String borrarChat () {
		
		// Eliminacion de un chat
		
		return "Eliminacion del chat correcta o incorrecta";
	}
	
	@PostMapping(path="/")
	public @ResponseBody String enviarMensaje (@RequestParam String mensaje, @RequestParam String claveUsuario) {
		
		// Enviacion de mensaje
		
		return "Envio del mensaje correcto o incorrecto";
	}
	
	@GetMapping(path="/")
	public @ResponseBody String obtenerChat () {
		
		// Obtencion de un chat
		
		return "Chat";
	}

}
