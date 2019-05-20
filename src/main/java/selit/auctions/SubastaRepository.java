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

/**
 * Repositorio de las subastas de la base de datos.
 */
public interface SubastaRepository extends JpaRepository<Subasta, Long>{
	
	/**
	 * Devuelve la informacion comun de la subasta con identificador id_subasta.
	 * @param id_subasta Identificador de la subasta.
	 * @return informacion comun de la subasta con identificador id_subasta.
	 */
	@Query("select new Subasta(idSubasta, publicate_date, description, title, fecha_finalizacion, startPrice, id_owner, category, posX, posY, currency, nfav, nvis, status)"
			+ " from Subasta where idSubasta=:id_subasta")
	public Optional<Subasta> findSubastaCommon(@Param("id_subasta") Long id_subasta);
	
	/**
	 * Devuelve la informacion comun de las subastas con distancia menor a
	 * distance entre la ubicacion de la subasta y la ubicacion definida por lat
	 * y lng, ordenada por sort.
	 * @param lat Latitud de la ubicacion.
	 * @param lng Longitud de la ubicacion.
	 * @param distance Distancia maxima.
	 * @param sort Forma de ordenar las subastas devueltas..
	 * @return Informacion comun de las subastas con distancia menor a
	 * distance entre la ubicacion de la subasta y la ubicacion definida por lat
	 * y lng, ordenada por sort.
	 */
	@Query(value = "select id_subasta,fecha_publicacion, fecha_finalizacion, descripcion,titulo,posX,posY,moneda,usuario_id_usuario,nombre_categoria,estado, precio_salida,nfavoritos,nvisitas,estado, "+ "( 6371 * acos( cos( radians(?1) ) * cos( radians( subasta.posX ) )" +
			   "* cos( radians(subasta.posY) - radians(?2)) + sin(radians(?1))" +
			   "* sin( radians(subasta.posX)))) AS distancia " + "FROM subasta " +
			"WHERE ( 6371 * acos( cos( radians(?1) ) * cos( radians( subasta.posX ) )" +
			   "* cos( radians(subasta.posY) - radians(?2)) + sin(radians(?1))" +
			   "* sin( radians(subasta.posX)))) <= ?3 ORDER BY ?#{#sort}", nativeQuery = true)
	public List<Subasta> selectSubastaCommonDistance(@Param("posX") String lat,@Param("posY") String lng,@Param("distance") String distance, Sort sort);
	
	/**
	 * Devuelve la distancia entre la subasta identificada por id_producto y la
	 * ubicacion definida por lat y lng.
	 * @param lat Latitud de la ubicacion. 
	 * @param lng Longitud de la ubicacion.
	 * @param id_producto Identificador de la subasta.
	 * @return Distancia entre la subasta identificada por id_producto y la
	 * ubicacion definida por lat y lng.
	 */
	@Query(value = "select ( 6371 * acos( cos( radians(?1) ) * cos( radians( subasta.posX ) )" + 
			"   * cos( radians(subasta.posY) - radians(?2)) + sin(radians(?1))" + 
			"   * sin( radians(subasta.posX)))) AS distancia  from subasta WHERE id_subasta = ?3", nativeQuery = true)
	public double selectDistance(@Param("posX") String lat,@Param("posY") String lng, @Param("id_producto") String id_producto);

	/**
	 * Devuelve una lista de identificadores de las subastas con categoria category.
	 * @param category Categoria de la subasta.
	 * @return Lista de identificadores de las subastas con categoria category.
	 */
	@Query("select idSubasta from Subasta where nombre_categoria=:nombre_categoria")
	public List<Long> selectSubastaCommonCategory(@Param("nombre_categoria") String category);
	
	/**
	 * Devuelve una lista de identificadores de las subastas con el termino 
	 * search en su titulo.
	 * @param search Termino a buscar.
	 * @return Lista de identificadores de las subastas con el termino 
	 * search en su titulo.
	 */
	@Query(value = "select id_subasta from subasta where LOCATE(?1,titulo)", nativeQuery = true)
	public List<BigInteger> selectSubastaCommonSearch(@Param("search") String search);
	
	/**
	 * Devuelve una lista de identificadores de las subastas con precio inicial
	 * mayor o igual que priceFrom.
	 * @param priceFrom Precio inicial minimo.
	 * @return Lista de identificadores de las subastas con precio inicial
	 * mayor o igual que priceFrom.
	 */
	@Query("select idSubasta from Subasta where startPrice >= :priceFrom")
	public List<Long> selectSubastaCommonPriceFrom(@Param("priceFrom") float priceFrom);
	
	/**
	 * Devuelve una lista de identificadores de subastas con precio inicial
	 * menor o igual a priceTo.
	 * @param priceTo Precio inicial maximo.
	 * @return Lista de identificadores de subastas con precio inicial
	 * menor o igual a priceTo.
	 */
	@Query("select idSubasta from Subasta where startPrice <= :priceTo")
	public List<Long> selectSubastaCommonPriceTo(@Param("priceTo") float priceTo);
	
	/**
	 * Devuelve una lista de identificadores de subastas con fecha de 
	 * publicacion mayor o igual que publishedFrom.
	 * @param publishedFrom Fecha de publicacion minima.
	 * @return Lista de identificadores de subastas con fecha de publicacion
	 * mayor o igual que publishedFrom.
	 */
	@Query("select idSubasta from Subasta where publicate_date >= :publishedFrom")
	public List<Long> selectSubastaCommonPublishedFrom(@Param("publishedFrom") String publishedFrom);
	
	/**
	 * Devuelve una lista de identificadores de subastas con fecha de 
	 * publicacion menor o igual que publishedTo.
	 * @param publishedTo Fecha de publicacion maxima.
	 * @return Lista de identificadores de subastas con fecha de publicacion
	 * menor o igual que publishedTo.
	 */
	@Query("select idSubasta from Subasta where publicate_date <= :publishedTo")
	public List<Long> selectSubastaCommonPublishedTo(@Param("publishedTo") String publishedTo);
	
	/**
	 * Devuelve una lista de identificadores de subastas cuyo propietario es
	 * owner.
	 * @param owner Propietario de las subastas.
	 * @return Lista de identificadores de subastas cuyo propietario es
	 * owner.
	 */
	@Query("select idSubasta from Subasta where id_owner = :owner")
	public List<Long> selectSubastaCommonOwner(@Param("owner") Long owner);
	
	/**
	 * Devuelve una lista de identificadores de subastas con el mismo estado a
	 * status.
	 * @param status Estado de la subasta.
	 * @return Lista de identificadores de subastas con el mismo estado a
	 * status.
	 */
	@Query("select idSubasta from Subasta where status = :status")
	public List<Long> selectSubastaCommonStatus(@Param("status") String status);
	
	/**
	 * Devuelve la subasta identificada por id_subasta.
	 * @param id_subasta Identificador de la subasta.
	 * @return Subasta identificada por id_subasta.
	 */
	@Query("from Subasta where id_subasta=:id_subasta")
	public Subasta buscarPorId(@Param("id_subasta") String id_subasta);
	
	/**
	 * Actualiza la subasta identificada por id_subasta.
	 * @param publicate_date Nueva fecha de publicacion de la subasta.
	 * @param description Nueva descripcion de la subasta.
	 * @param title Nuevo titulo de la subasta.
	 * @param posX Nueva latitud de la ubicacion de la subasta.
	 * @param posY Nueva longitud de la ubicacion de la subasta.
	 * @param price Nuevo precio inicial de la subasta.
	 * @param currency Nueva moneda del precio de la subasta.
	 * @param endDate Nueva fecha de terminacion de la subasta.
	 * @param id_owner Nuevo identificador del propietario de la subasta. 
	 * @param category Nueva categoria de la subasta.
	 * @param id_subasta Identificador de la subasta.
	 */
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
   			);
	
	/**
	 * Actualiza el numero de favoritos de la subasta identificada por 
	 * id_subasta a nfav.
	 * @param nfav Nuevo numero de favoritos de la subasta.
	 * @param id_subasta Identificador de la subasta.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Subasta set nfavoritos=:nfavoritos where id_subasta=:id_subasta")
	public void actualizarNFav(@Param("nfavoritos") Long nfav, @Param("id_subasta") Long id_subasta);
	
	/**
	 * Actualiza el numero de visitas de la subasta identificada por
	 * id_subasta a nvis.
	 * @param nvis Nuevo numero de visitas de la subasta.
	 * @param id_subasta Identificador de la subasta.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Subasta set nvisitas=:nvisitas where id_subasta=:id_subasta")
	public void actualizarNVis(@Param("nvisitas") Long nvis, @Param("id_subasta") Long id_subasta);	
	
	/**
	 * Actualiza el estado a status de la subasta identificada por id_subasta.
	 * @param status Nuevo estado de la subasta.
	 * @param id_subasta Identificador de la subasta.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Subasta set status=:status where id_subasta=:id_subasta")
	public void actualizarStatus(@Param("status") String status, @Param("id_subasta") Long id_subasta);	
}	