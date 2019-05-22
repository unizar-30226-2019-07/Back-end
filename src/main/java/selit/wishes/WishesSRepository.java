package selit.wishes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio de subastas deseadas en la base de datos
 */
public interface WishesSRepository extends JpaRepository<WishS, WishSId>{
	
	/**
	 * Devuelve la lista de subastas deseadas del usuario identificado con
	 * idUsuario.
	 * @param idUsuario Identificador del usuario.
	 * @return Lista de subastas deseadas del usuario identificado con
	 * idUsuario.
	 */
	@Query("from WishS where WishSId.idUsuario=:idUsuario")
	public List<WishS> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);
	
	/**
	 * Devuelve un objeto de tipo WishS si existe una subasta identificada con
	 * id_subasta deseada por un usuario identificado con id_usuario.
	 * @param id_usuario Identificador del usuario.
	 * @param id_subasta Identificador de la subasta.
	 * @return Objeto de tipo WishS si existe una subasta identificada con
	 * id_subasta deseada por un usuario identificado con id_usuario.
	 */
	@Query("from WishS where id_subasta=:id_subasta AND id_usuario=:id_usuario")
	public WishS buscarInWishList(@Param("id_usuario") String id_usuario,@Param("id_subasta") String id_subasta);
	
}
