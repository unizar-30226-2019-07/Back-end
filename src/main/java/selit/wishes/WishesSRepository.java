package selit.wishes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface WishesSRepository extends JpaRepository<WishS, WishSId>{
	
	@Query("from WishS where WishSId.idUsuario=:idUsuario")
	public List<WishS> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);
	
}
