package selit.producto;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

	@Query("select new Anuncio(idProducto, publicate_date, description, title,posX,posY,price,currency,nfav,nvis,id_owner,category,status)"
			+ "  from Anuncio where id_producto=:id_producto")
	public Optional<Anuncio> findAnuncioCommon(@Param("id_producto") String id_producto);
	
	@Query(value = "select id_producto,fecha_publicacion,descripcion,titulo,posX,posY,precio,moneda,nfavoritos,nvisitas,usuario_id_usuario,nombre_categoria,estado,"+
			"( 6371 * acos( cos( radians(?1) ) * cos( radians( anuncio.posX ) )" + 
			"   * cos( radians(anuncio.posY) - radians(?2)) + sin(radians(?1))" + 
			"   * sin( radians(anuncio.posX)))) AS distance  from anuncio WHERE id_producto = ?3", nativeQuery = true)
	public Anuncio selectDistanceAll(@Param("posX") String lat,@Param("posY") String lng, @Param("id_producto") BigInteger id_producto);

	@Query(value = "select ( 6371 * acos( cos( radians(?1) ) * cos( radians( anuncio.posX ) )" + 
			"   * cos( radians(anuncio.posY) - radians(?2)) + sin(radians(?1))" + 
			"   * sin( radians(anuncio.posX)))) AS distancia  from anuncio WHERE id_producto = ?3", nativeQuery = true)
	public double selectDistance(@Param("posX") String lat,@Param("posY") String lng, @Param("id_producto") String id_producto);

	@Query(value = "select id_producto,fecha_publicacion,descripcion,titulo,posX,posY,precio,moneda,nfavoritos,nvisitas,usuario_id_usuario,nombre_categoria,estado, "+ "( 6371 * acos( cos( radians(?1) ) * cos( radians( anuncio.posX ) )" +
			   "* cos( radians(anuncio.posY) - radians(?2)) + sin(radians(?1))" +
			   "* sin( radians(anuncio.posX)))) AS distancia " + "FROM anuncio " +
			"WHERE ( 6371 * acos( cos( radians(?1) ) * cos( radians( anuncio.posX ) )" +
			   "* cos( radians(anuncio.posY) - radians(?2)) + sin(radians(?1))" +
			   "* sin( radians(anuncio.posX)))) <= ?3 ORDER BY ?#{#sort}", nativeQuery = true)
	public List<Anuncio> selectAnuncioCommonDistance(@Param("posX") String lat,@Param("posY") String lng,@Param("distance") String distance, Sort sort);
	
	@Query("select idProducto from Anuncio where nombre_categoria=:nombre_categoria")
	public List<Long> selectAnuncioCommonCategory(@Param("nombre_categoria") String category);
	
	@Query(value = "select id_producto from anuncio where LOCATE(?1,titulo)", nativeQuery = true)
	public List<BigInteger> selectAnuncioCommonSearch(@Param("search") String search);
	
	@Query("select idProducto from Anuncio where price >= :priceFrom")
	public List<Long> selectAnuncioCommonPriceFrom(@Param("priceFrom") float priceFrom);
	
	@Query("select idProducto from Anuncio where price <= :priceTo")
	public List<Long> selectAnuncioCommonPriceTo(@Param("priceTo") float priceTo);
	
	@Query("select idProducto from Anuncio where publicate_date >= :publishedFrom")
	public List<Long> selectAnuncioCommonPublishedFrom(@Param("publishedFrom") String publishedFrom);
	
	@Query("select idProducto from Anuncio where publicate_date <= :publishedTo")
	public List<Long> selectAnuncioCommonPublishedTo(@Param("publishedTo") String publishedTo);
	
	@Query("select idProducto from Anuncio where id_owner = :owner")
	public List<Long> selectAnuncioCommonOwner(@Param("owner") Long owner);
	
	@Query("select idProducto from Anuncio where status = :status")
	public List<Long> selectAnuncioCommonStatus(@Param("status") String status);
	
	@Query("from Anuncio where id_producto=:id_producto")
	public Anuncio buscarPorId(@Param("id_producto") String id_producto);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Anuncio set fecha_publicacion=:fecha_publicacion, descripcion=:descripcion, titulo=:titulo, status=:status, "
			+ "posX=:posX, posY=:posY, precio=:precio, moneda=:moneda,"
			+ "usuario_id_usuario=:usuario_id_usuario,nombre_categoria=:nombre_categoria where id_producto=:id_producto")
	public void actualizarAnuncio(@Param("fecha_publicacion") String publicate_date, 
			@Param("descripcion") String description, @Param("titulo") String title,
			@Param("posX") float posX, @Param("posY") float posY,
			@Param("precio") float price,@Param("moneda") String currency, 
			@Param("usuario_id_usuario") Long id_owner,
			@Param("nombre_categoria") String category, @Param("id_producto") String id_producto,
			@Param("status") String status);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Anuncio set nfavoritos=:nfavoritos where id_producto=:id_producto")
	public void actualizarNFav(@Param("nfavoritos") Long nfav, @Param("id_producto") Long id_producto);
}
