package selit.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio de informes en la base de datos
 */
public interface ReportRepository extends JpaRepository<Report, ReportId>{
	
	/**
	 *  Devuelve el informe cuyo identificador del usuario informador es
	 *  id_informador y el identificador del usuario evaluado es id_evaluado.
	 * @param id_evaluado Identificador del usuario evaluado.
	 * @param id_informador Identificadro del usuario informador.
	 * @return Informe cuyo identificador del usuario informador es
	 *  id_informador y el identificador del usuario evaluado es id_evaluado.
	 */
	@Query("from Report where id_evaluado=:id_evaluado and id_informador=:id_informador")
	public Report buscarPorId(@Param("id_evaluado") Long id_evaluado, @Param("id_informador") Long id_informador);
	
}
