package selit.auctions;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubastaRepository extends JpaRepository<Subasta, Long>{
	
	@Query("select new Subasta(idSubasta, publicate_date, description, title, fecha_finalizacion, startPrice, id_owner, category, posX, posY, currency)"
			+ " from Subasta where idSubasta=:id_subasta")
	public Optional<Subasta> findSubastaCommon(@Param("id_subasta") Long id_subasta);
	
	//public Optional<Subasta> findSubastaByIdSubasta(@Param("idSubasta") Long id_subasta);
	
	@Query(value = "select id_subasta,fecha_publicacion, fecha_finalizacion, descripcion,titulo,posX,posY,moneda,usuario_id_usuario,nombre_categoria,estado, precio_salida, "+ "( 6371 * acos( cos( radians(?1) ) * cos( radians( subasta.posX ) )" +
			   "* cos( radians(subasta.posY) - radians(?2)) + sin(radians(?1))" +
			   "* sin( radians(subasta.posX)))) AS distancia " + "FROM subasta " +
			"WHERE ( 6371 * acos( cos( radians(?1) ) * cos( radians( subasta.posX ) )" +
			   "* cos( radians(subasta.posY) - radians(?2)) + sin(radians(?1))" +
			   "* sin( radians(subasta.posX)))) <= ?3 ORDER BY ?#{#sort}", nativeQuery = true)
	public List<Subasta> selectSubastaCommonDistance(@Param("posX") String lat,@Param("posY") String lng,@Param("distance") String distance, Sort sort);

	@Query("select idSubasta from Subasta where nombre_categoria=:nombre_categoria")
	public List<Long> selectSubastaCommonCategory(@Param("nombre_categoria") String category);
	
	@Query(value = "select id_subasta from subasta where LOCATE(?1,titulo)", nativeQuery = true)
	public List<BigInteger> selectSubastaCommonSearch(@Param("search") String search);
	
	@Query("select idSubasta from Subasta where startPrice >= :priceFrom")
	public List<Long> selectSubastaCommonPriceFrom(@Param("priceFrom") float priceFrom);
	
	@Query("select idSubasta from Subasta where startPrice <= :priceTo")
	public List<Long> selectSubastaCommonPriceTo(@Param("priceTo") float priceTo);
	
	@Query("select idSubasta from Subasta where publicate_date >= :publishedFrom")
	public List<Long> selectSubastaCommonPublishedFrom(@Param("publishedFrom") String publishedFrom);
	
	@Query("select idSubasta from Subasta where publicate_date <= :publishedTo")
	public List<Long> selectSubastaCommonPublishedTo(@Param("publishedTo") String publishedTo);
	
	@Query("select idSubasta from Subasta where id_owner = :owner")
	public List<Long> selectSubastaCommonOwner(@Param("owner") Long owner);
	
	@Query("select idSubasta from Subasta where status = :status")
	public List<Long> selectSubastaCommonStatus(@Param("status") String status);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Subasta set fecha_publicacion=:fecha_publicacion, descripcion=:descripcion, titulo=:titulo, "
			+ "posX=:posX, posY=:posY, precio_salida=:precio_salida, moneda=:moneda, fecha_finalizacion=:fecha_finalizacion,"
			+ "usuario_id_usuario=:usuario_id_usuario,nombre_categoria=:nombre_categoria where id_subasta=:id_subasta")
	public void actualizarSubasta(@Param("fecha_publicacion") String publicate_date, 
			@Param("descripcion") String description, 
			@Param("titulo") String title,
			@Param("posX") float posX, 
			@Param("posY") float posY,
			@Param("precio_salida") float price,
			@Param("moneda") String currency, 
			@Param("fecha_finalizacion") String endDate, 
			@Param("usuario_id_usuario") Long id_owner,
			@Param("nombre_categoria") String category, 
			@Param("id_subasta") String id_subasta
   			);}	