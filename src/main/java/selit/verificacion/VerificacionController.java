package selit.verificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import selit.usuario.UsuarioController;

@RestController   
@RequestMapping(path="/verify") 
public class VerificacionController {
	
	@Autowired public 
	VerificacionRepository verificaciones;	
	
	@PostMapping(path="")
	public @ResponseBody String verificarUsuario (@RequestParam(name = "random") String random) {
		Long idUsuario = verificaciones.buscarPorRandom(random);
		verificaciones.deleteRandom(random);
		UsuarioController.usuarios.updateStatus(idUsuario,"activa");
		return "OK";
	}
}
