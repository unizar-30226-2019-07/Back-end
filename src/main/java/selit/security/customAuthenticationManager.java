package selit.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import selit.usuario.UsuarioRepository;
import selit.usuario.Usuario;
import selit.usuario.UsuarioController;

public class customAuthenticationManager implements AuthenticationManager {

	UsuarioRepository usuarios;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    String email = authentication.getPrincipal() + "";
	    String password = authentication.getCredentials() + "";
	    Usuario user = UsuarioController.usuarios.buscarPorEmail(email);
	    if (user == null) {
	        throw new BadCredentialsException("1000");
	    }
	    // Comprobar contrasena!
	    return new UsernamePasswordAuthenticationToken(email, password);

	}

}
