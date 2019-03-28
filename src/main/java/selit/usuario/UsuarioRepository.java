package selit.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(String username);
	
	@Query("from Usuario where nombre=:nombre")
	public Iterable<Usuario> buscarPorNombre(@Param("nombre") String nombre);
	
	@Query("from Usuario where email=:email")
	public Usuario buscarPorEmail(@Param("email") String nombre);
}