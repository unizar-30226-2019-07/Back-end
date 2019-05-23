package selit.valoracion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	/**
	 * Devuelve la lista de valoraciones que estan relacionadas con el usuario
	 * identificado por id_anunciante o id_comprador.
	 * @param id_anunciante Identificador del comprador.
	 * @param id_comprador Identificador del anunciante.
	 * @return Lista de valoraciones que estan relacionadas con el usuario
	 * identificado por id_anunciante o id_comprador.
	 */
	@Query("from Valoracion where id_anunciante=:id_anunciante OR id_comprador=:id_comprador")
	public List<Valoracion> buscarPorIdUsuario(@Param("id_anunciante") Long id_anunciante, @Param("id_comprador") Long id_comprador);
	
	/**
	 * Elimina las valoraciones que estan relacionadas al usuario identificado 
	 * con id.
	 * @param id Identificador del usuario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Valoracion where id_anunciante=:id or id_comprador=:id")
	public void deleteByUsuario(@Param("id") Long id);
}
