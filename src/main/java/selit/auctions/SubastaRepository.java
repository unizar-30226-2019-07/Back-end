package selit.auctions;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubastaRepository extends JpaRepository<Subasta, Long>{
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Subasta set fecha_publicacion=:fecha_publicacion, descripcion=:descripcion, titulo=:titulo, estado=:estado, "
			+ "posX=:posX, posY=:posY, precio_salida=:precio_salida, moneda=:moneda, fecha_finalizacion=:fecha_finalizacion,"
			+ "usuario_id_usuario=:usuario_id_usuario,nombre_categoria=:nombre_categoria where id_subasta=:id_subasta")
	public void actualizarSubasta(@Param("fecha_publicacion") String publicate_date, 
			@Param("descripcion") String description, @Param("titulo") String title,
			@Param("posX") float posX, @Param("posY") float posY,
			@Param("precio_salida") float price,@Param("moneda") String currency, 
			@Param("fecha_finalizacion") String endDate, @Param("usuario_id_usuario") Long id_owner,
			@Param("nombre_categoria") String category, @Param("id_subasta") String id_subasta,
			@Param("estado") String estado);
}	