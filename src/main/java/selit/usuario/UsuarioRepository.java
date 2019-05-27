package selit.usuario;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Respositorio de usuarios en la base de datos.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	/**
	 * Devuelve los usuarios con nombre nombre.
	 * @param nombre Nombre de los usuarios.
	 * @return Lista de usuarios con nombre nombre.
	 */
	@Query("from Usuario where nombre=:nombre")
	public Iterable<Usuario> buscarPorNombre(@Param("nombre") String nombre);
	
	/**
	 * Devuelve el usuario con correo electronico email.
	 * @param nombre Correo electronico del usuario.
	 * @return Usuario con correo electronico email.
	 */
	@Query("from Usuario where email=:email")
	public Usuario buscarPorEmail(@Param("email") String nombre);
	
	/**
	 * Devuelve la informacion comun del usuario con correo electronico email.
	 * @param nombre Correo electronico del usuario.
	 * @return Usuario unicamente con informacion comun con correo electronico 
	 * email.
	 */
	@Query("select new Usuario(idUsuario, birth_date, rating, last_name, first_name, email, posX, posY, idImagen) from Usuario where email=:email")
	public Usuario buscarPorEmailCommon(@Param("email") String nombre);
	
	/**
	 * Devuelve el usuario identificado con id_usuario.
	 * @param id_usuario Identificador del usuario.
	 * @return Usuario con identificador id_usuario.
	 */
	@Query("from Usuario where id_usuario=:id_usuario")
	public Usuario buscarPorId(@Param("id_usuario") String id_usuario);
	
	/**
	 * Devuelve la lista de todos los usuarios unicamente con informacion comun.
	 * @return Lista de todos los usuarios unicamente con informacion comun.
	 */
	@Query("select new Usuario(idUsuario, birth_date, rating, last_name, first_name, email, posX, posY, idImagen)  from Usuario")
	public List<Usuario> findAllCommon();
	
	/**
	 * Devuelve la informacion comun del usuario identificado con id_usuario. 
	 * @param id_usuario Identificador del usuario.
	 * @return Informacion comun del usuario identificado con id_usuario.
	 */
	@Query("select new Usuario(idUsuario, birth_date, rating, last_name, first_name, email, posX, posY, idImagen)  from Usuario where id_usuario=:id_usuario")
	public Optional<Usuario> findUserCommon(@Param("id_usuario") String id_usuario);
	
	/**
	 * Devuelve el identificador del usuario con correo electronico nombre.
	 * @param nombre Correo electronico del usuario.
	 * @return Identificador del usuario con correo electronico nombre.
	 */
	@Query("select idUsuario from Usuario where email=:email")
	public Long buscarIdUsuario(@Param("email") String nombre);
	
	/**
	 * Devuelve la contrasenya cifrada del usuario con identificador id_usuario.
	 * @param id_usuario Identificador del usuario.
	 * @return Contrasenya cifrada del usuario identificado con id_usuario.
	 */
	@Query("select password from Usuario where id_usuario=:id_usuario")
	public String searchPassword(@Param("id_usuario") String id_usuario);
	
	/**
	 * Devuelve la lista de usuarios ordenador por sort.
	 * @param sort Forma de ordenar la lista.
	 * @return Lista de usuarios odenados por sort.
	 */
	@Query("from Usuario")
	public List<Usuario> buscarUsuariosOrdenados(Sort sort);
	
	/**
	 * Devuelve la lista de usuarios unicamente con informacion comun ordenados
	 * por sort.
	 * @param sort Forma de ordenar la lista.
	 * @return Lista de usuarios unicamente con informacion comun ordenados por
	 * sort.
	 */
	@Query("select new Usuario(idUsuario, birth_date, rating, last_name, first_name, email, posX, posY, idImagen)  from Usuario")
	public List<Usuario> buscarUsuariosOrdenadosCommon(Sort sort);
	
	/**
	 * Devuelve la pagina page de la lista de usuarios.
	 * @param page Pagina de la lista de usuarios.
	 * @return Pagina page de la lista de usuarios.
	 */
	@Query("from Usuario")
	public List<Usuario> buscarUsuariosPagina(Pageable page);
	
	/**
	 * Devuelve la pagina page de la lista de usuarios unicamente con
	 * informacion comun.
	 * @param page Pagina de la lista de usuarios
	 * @return Pagina page de la lista de usuarios unicamente con
	 * informacion comun.
	 */
	@Query("select new Usuario(idUsuario, birth_date, rating, last_name, first_name, email, posX, posY, idImagen)  from Usuario")
	public List<Usuario> buscarUsuariosPaginaCommon(Pageable page);
	
	/**
	 * Cambia la contrasenya cifrada del usuario identificado con id_usuario a 
	 * password.
	 * @param password Nueva contrasenya cifrada.
	 * @param id_usuario Identificador del usuario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Usuario set contrasena=:contrasena where id_usuario=:id_usuario")
	public void changePassword(@Param("contrasena") String password,
			@Param("id_usuario") String id_usuario);
	
	/**
	 * Actualiza la informacion del usuario identificado con id_usuario.
	 * @param email Nuevo correo electronico del usuario.
	 * @param first_name Nuevo nombre del usuario.
	 * @param last_name Nuevos apellidos del usuario.
	 * @param gender Nuevo genero del usuario.
	 * @param birth_date Nueva fecha de nacimiento del usuario.
	 * @param lat Nueva latitud de la ubicacion del usuario.
	 * @param lng Nueva longitud de la ubicacion del usuario.
	 * @param id_usuario Identificador del usuario.
	 * @param id_imagen Nuevo identificador de la imagen del usuario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Usuario set email=:email, nombre=:nombre, apellidos=:apellidos, "
			+ "sexo=:sexo, nacimiento=:nacimiento, posX=:posX, posY=:posY, id_imagen=:id_imagen where id_usuario=:id_usuario")
	public void actualizarUsuario(@Param("email") String email, 
			@Param("nombre") String first_name, @Param("apellidos") String last_name,
			@Param("sexo") String gender, @Param("nacimiento") String birth_date,
			@Param("posX") float lat, @Param("posY") float lng,
			@Param("id_usuario") String id_usuario, @Param("id_imagen") Long id_imagen);
	
	/**
	 * Cambia el estado (activada o bloqueada) de la cuenta del usuario
	 * identificado con id_usuario.
	 * @param id_usuario Identificador del usuario.
	 * @param status Nuevo estado (activada o bloqueada) de la cuenta del 
	 * usuario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Usuario set status=:status where id_usuario=:id_usuario")
	public void updateStatus(@Param("id_usuario") Long id_usuario,@Param("status") String status);
	
	/**
	 * Cambia idImagen a null para un usuario
	 * @param id_usuario Identificador del usuario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Usuario set idImagen=null where id_usuario=:id_usuario")
	public void setImagenNull(@Param("id_usuario") String id_usuario);
	
	/**
	 * Cambia la valoracion del usuario identificado con id_usuario.
	 * @param id_usuario Identificador del usuario.
	 * @param calificacion Nueva calificacion del usuario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Usuario set calificacion=:calificacion where id_usuario=:id_usuario")
	public void updateRating(@Param("id_usuario") String id_usuario,@Param("calificacion") float calificacion);
}	