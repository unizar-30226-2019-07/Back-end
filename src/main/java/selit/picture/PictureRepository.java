package selit.picture;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureRepository extends JpaRepository<Picture, Long>{
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("INSERT into Picture(:name, :tipo, :tamanyo, :content)")
	public Picture insertarImagen(@Param("name") String name,@Param("tipo") String tipo,
			@Param("tamanyo") Integer tamano, @Param("content") byte[] content);

}	