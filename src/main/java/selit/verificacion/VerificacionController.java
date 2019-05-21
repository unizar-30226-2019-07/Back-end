package selit.verificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import selit.usuario.UsuarioController;

/**
 * Controlador de las operaciones relacionadas con verificaciones
 */
@RestController   
@RequestMapping(path="/verify") 
public class VerificacionController {
	
	/** Repositorio de verificaciones */
	@Autowired public 
	VerificacionRepository verificaciones;	
	
	/**
	 * Verifica al usuario, activando su cuenta y eliminando la verificacion de
	 * la base de datos.
	 * @param random Cadena aleatoria asociada al usuario.
	 * @return "OK".
	 */
	@PostMapping(path="")
	public @ResponseBody String verificarUsuario (@RequestParam(name = "random") String random) {
		Long idUsuario = verificaciones.buscarPorRandom(random);
		verificaciones.deleteRandom(random);
		UsuarioController.usuarios.updateStatus(idUsuario,"activa");
		return "OK";
	}
	
}
