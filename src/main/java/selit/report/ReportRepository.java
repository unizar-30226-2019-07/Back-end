package selit.report;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	/**
	 * Elimina los informes relacionados con el usuario identificado con id.
	 * @param id Identificador del usuario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Report where id_evaluado=:id or id_informador=:id")
	public void deleteByUsuario(@Param("id") Long id);
	
	/**
	 *  Devuelve una lista de informes cuyo identificador del usuario 
	 *  evaluado es id_evaluado.
	 * @param id_evaluado Identificador del usuario evaluado.
	 * @return Lista de informes cuyo identificador del usuario 
	 * evaluado es id_evaluado.
	 */
	@Query("from Report where id_evaluado=:id_evaluado")
	public List<Report> buscarPorIdEvaluado(@Param("id_evaluado") Long id_evaluado);
	
}
