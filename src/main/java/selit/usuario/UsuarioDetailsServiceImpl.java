package selit.usuario;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Interfaz para detalles de usuarios.
 */
@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	/** Repositorio de usuarios */
	private UsuarioRepository usuarioRepository;

	/**
	 * Constructor
	 * @param usuarioRepository Repositorio de usuarios.
	 */
	public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Devuelve la informacion del usuario con correo electronico username.
	 * @param username Correo electronico del usuario.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.buscarPorEmail(username);
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(usuario.getEmail(), usuario.getPassword(), emptyList());
	}
}