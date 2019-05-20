package selit.picture;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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
}	