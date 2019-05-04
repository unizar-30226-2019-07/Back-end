package selit.picture;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureRepository extends JpaRepository<Picture, Long>{
	@Query(value = "select id_imagen from imagen WHERE id_producto = ?1", nativeQuery = true)
	public List<BigInteger> findIdImages( @Param("id_producto") String id_producto);
	
	@Query(value = "select id_imagen from imagen WHERE id_subasta = ?1", nativeQuery = true)
	public List<BigInteger> findIdImagesSub( @Param("id_subasta") String id_subasta);
}	