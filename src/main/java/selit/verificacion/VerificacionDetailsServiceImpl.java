package selit.verificacion;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import selit.usuario.Usuario;
import selit.usuario.UsuarioRepository;


@Service
public class VerificacionDetailsServiceImpl{

	private VerificacionRepository verificacionRepository;

	public VerificacionDetailsServiceImpl(VerificacionRepository verificacionRepository) {
		this.verificacionRepository = verificacionRepository;
	}

}