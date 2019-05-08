package selit.valoracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ValoracionesRepository extends JpaRepository<Valoracion, ValoracionesId>{
	@Query("from Valoracion where valoracionesId.id_comprador=:email")
	public List<Valoracion> buscarPorIdComprador(@Param("id_comprador") String id_comprador);
}
