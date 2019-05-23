package selit.bid;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio de las pujas en la base de datos.
 */
public interface BidRepository extends  JpaRepository<Bid, ClavePrimaria> {
	
	/**
	 * Devueve las pujas cuya subasta es identificada por id_subasta, ordenadas
	 * por sort.
	 * @param id_subasta Identificador de la subasta.
	 * @param sort Forma de ordenar la lista devuelta.
	 * @return Pujas cuya subasta es identificada por id_subasta, ordenadas por
	 * sort.
	 */
	@Query("from Bid where subasta_id_producto=:id_subasta")
	public List<Bid> findById_subasta(@Param("id_subasta") Long id_subasta, Sort sort);
	
	
	@Query("from Bid where subasta_id_producto=:id_subasta")
	public List<Bid> findById_subasta(@Param("id_subasta") Long id_subasta);
	
	/**
	 * Devuelve las pujas en las que ha participado el usuario identificado con
	 * id.
	 * @param id Identificador del usuario.
	 * @return Pujas en las que ha participado el usuario identificado con
	 * id.
	 */
	@Query("from Bid where usuario_id_usuario=:id")
	public List<Bid> findByIdUsuario(@Param("id") Long id);
	
	/**
	 * Elimina las pujas realizadas por el usuario identificado con id.
	 * @param id Identificador del usuario.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Bid where usuario_id_usuario=:id")
	public void deleteByUsuario(@Param("id") Long id);
	
}
