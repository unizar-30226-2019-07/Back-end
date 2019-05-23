package selit.wishes;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio de los anuncios deseados en la base de datos 
 */
public interface WishesARepository extends JpaRepository<WishA, WishAId>{
	
	/**
	 * Devuelve la lista de anuncios deseados del usuario identificado con 
	 * idUsuario.
	 * @param idUsuario Identificador del usuario.
	 * @return Lista de anuncios deseados del usuario identificado con 
	 * idUsuario.
	 */
	@Query("from WishA where WishAId.idUsuario=:idUsuario")
	public List<WishA> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);
	
	/**
	 * Devuelve un objeto de tipo WishA si existe un anuncio deseado
	 * identificado con id_producto por un usuario identificado con id_usuario.
	 * @param id_usuario Identificador del usuario.
	 * @param id_producto Identificador del anuncio.
	 * @return Objeto de tipo WishA si existe un anuncio deseado
	 * identificado con id_producto por un usuario identificado con id_usuario.
	 */
	@Query("from WishA where id_producto=:id_producto AND id_usuario=:id_usuario")
	public WishA buscarInWishList(@Param("id_usuario") String id_usuario,@Param("id_producto") String id_producto);	
	
	/**
	 * Elimina los anuncios deseados cuyo propietario se identifica con id.
	 * @param id Identificador del propietario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from WishA where id_usuario=:id")
	public void deleteByUsuario(@Param("id") Long id);
	
}
