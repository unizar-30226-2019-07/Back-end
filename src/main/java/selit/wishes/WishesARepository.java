package selit.wishes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface WishesARepository extends JpaRepository<WishA, WishAId>{
	
	@Query("from WishA where WishAId.idUsuario=:idUsuario")
	public List<WishA> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);
	
}
