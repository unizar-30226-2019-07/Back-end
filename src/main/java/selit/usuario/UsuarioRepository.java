package selit.usuario;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query("from Usuario where nombre=:nombre")
	public Iterable<Usuario> buscarPorNombre(@Param("nombre") String nombre);
	
	@Query("from Usuario where email=:email")
	public Usuario buscarPorEmail(@Param("email") String nombre);
	
	@Query("from Usuario where id_usuario=:id_usuario")
	public Usuario buscarPorId(@Param("id_usuario") String id_usuario);
	
	@Query("select new Usuario(idUsuario, birth_date, rating, last_name, first_name)  from Usuario where email!=:email")
	public List<Usuario> findAllCommon(@Param("email") String nombre);
	
	@Query("select new Usuario(idUsuario, birth_date, rating, last_name, first_name)  from Usuario where id_usuario=:id_usuario")
	public Optional<Usuario> findUserCommon(@Param("id_usuario") String id_usuario);
	
	@Query("select idUsuario from Usuario where email=:email")
	public Integer buscarIdUsuario(@Param("email") String nombre);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Usuario set email=:email, nombre=:nombre, apellidos=:apellidos, "
			+ "sexo=:sexo, nacimiento=:nacimiento, posX=:posX, posY=:posY where id_usuario=:id_usuario")
	public void actualizarUsuario(@Param("email") String email, 
			@Param("nombre") String first_name, @Param("apellidos") String last_name,
			@Param("sexo") String gender, @Param("nacimiento") String birth_date,
			@Param("posX") float lat, @Param("posY") float lng,
			@Param("id_usuario") String id_usuario);
}	