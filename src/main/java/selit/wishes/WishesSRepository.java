package selit.wishes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface WishesSRepository extends JpaRepository<WishS, WishSId>{
	
	@Query("from WishS where WishSId.idUsuario=:idUsuario")
	public List<WishS> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);
	
	@Query("from WishS where id_subasta=:id_subasta AND id_usuario=:id_usuario")
	public WishS buscarInWishList(@Param("id_usuario") String id_usuario,@Param("id_subasta") String id_subasta);
	
}
