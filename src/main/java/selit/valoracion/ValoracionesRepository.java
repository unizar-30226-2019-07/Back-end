package selit.valoracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ValoracionesRepository extends JpaRepository<Valoracion, Long>{	
	@Query("from Valoracion where id_anunciante=:id_anunciante")
	public List<Valoracion> buscarPorIdAnunciante(@Param("id_anunciante") Long id_anunciante);
}
