package selit.picture;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio de imagenes en la base de datos.
 */
public interface PictureRepository extends JpaRepository<Picture, Long>{
	
	/**
	 * Devuelve la lista de identificadores de imagenes que estan en el
	 * producto identificado por id_producto.
	 * @param id_producto Identificador del producto.
	 * @return Lista de identificadores de imagenes que estan en el
	 * producto identificado por id_producto.
	 */
	@Query(value = "select id_imagen from imagen WHERE id_producto = ?1", nativeQuery = true)
	public List<BigInteger> findIdImages( @Param("id_producto") String id_producto);
	
	/**
	 * Devuelve la lista de identificadores de imagenes que estan en la
	 * subasta identificada por id_subasta.
	 * @param id_subasta Identificador del producto.
	 * @return Lista de identificadores de imagenes que estan en la
	 * subasta identificada por id_subasta.
	 */
	@Query(value = "select id_imagen from imagen WHERE id_subasta = ?1", nativeQuery = true)
	public List<BigInteger> findIdImagesSub( @Param("id_subasta") String id_subasta);
	
	/**
	 * Elimina las imagenes relacionadas con el anuncio identificado con id.
	 * @param id Identificador del anuncio.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Picture where id_producto=:id")
	public void deleteByProducto(@Param("id") Long id);
	
	/**
	 * Elimina las imagenes relacionadas con la subasta identificada con id.
	 * @param id Identificador de la subasta.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Picture where id_subasta=:id")
	public void deleteBySubasta(@Param("id") Long id);
}	