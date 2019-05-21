package selit.valoracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** 
 * Repositorio de valoraciones en la base de datos
 */
public interface ValoracionesRepository extends JpaRepository<Valoracion, Long>{	
	
	/**
	 * Devuelve el listado de valoraciones del anunciante identificado por
	 * id_anunciante.
	 * @param id_anunciante Identificador del anunciante.
	 * @return Listado de valoraciones del anunciante identificado por
	 * id_anunciante.
	 */
	@Query("from Valoracion where id_anunciante=:id_anunciante")
	public List<Valoracion> buscarPorIdAnunciante(@Param("id_anunciante") Long id_anunciante);
}
