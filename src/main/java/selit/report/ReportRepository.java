package selit.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReportRepository extends JpaRepository<Report, ReportId>{	
	@Query("from Report where id_evaluado=:id_evaluado and id_informador=:id_informador")
	public Report buscarPorId(@Param("id_evaluado") Long id_evaluado, @Param("id_informador") Long id_informador);
}
