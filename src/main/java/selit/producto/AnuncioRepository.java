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

/**
 * Repositorio de anuncios en la base de datos.
 */
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

	/**
	 * Devuelve la informacion comun del anuncio identificado con id_producto.
	 * @param id_producto Identificador del producto.
	 * @return Informacion comun del anuncio identificado con id_producto.
	 */
	@Query("select new Anuncio(idProducto, publicate_date, description, title,posX,posY,price,currency,nfav,nvis,id_owner,category,status, id_buyer)"
			+ "  from Anuncio where id_producto=:id_producto")
	public Optional<Anuncio> findAnuncioCommon(@Param("id_producto") String id_producto);
	
	/**
	 * Devuelve el anuncio identificado con id_producto con la distancia entre
	 * el anuncio y la ubicacion definida por lat y long.
	 * @param lat Latitud de la ubicacion del usuario que realiza la peticion.
	 * @param lng Longitud de la ubicacion del usuario que realiza la peticion.
	 * @param id_producto Identificador del anuncio.
	 * @return Anuncio identificado con id_producto con la distancia entre
	 * el anuncio y la ubicacion definida por lat y long.
	 */
	@Query(value = "select id_producto,fecha_publicacion,descripcion,titulo,posX,posY,precio,moneda,nfavoritos,nvisitas,usuario_id_usuario,nombre_categoria,estado,"+
			"( 6371 * acos( cos( radians(?1) ) * cos( radians( anuncio.posX ) )" + 
			"   * cos( radians(anuncio.posY) - radians(?2)) + sin(radians(?1))" + 
			"   * sin( radians(anuncio.posX)))) AS distance  from anuncio WHERE id_producto = ?3", nativeQuery = true)
	public Anuncio selectDistanceAll(@Param("posX") String lat,@Param("posY") String lng, @Param("id_producto") BigInteger id_producto);

	/**
	 * Devuelve la distancia entre el anuncio identificado con id_producto y la
	 * ubicacion definida por lat y long.
	 * @param lat Latitud de la ubicacion.
	 * @param lng Longitud de la ubicacion.
	 * @param id_producto Identificador del anuncio.
	 * @return Distancia entre el anuncio identificado con id_producto y la
	 * ubicacion definida por lat y long.
	 */
	@Query(value = "select ( 6371 * acos( cos( radians(?1) ) * cos( radians( anuncio.posX ) )" + 
			"   * cos( radians(anuncio.posY) - radians(?2)) + sin(radians(?1))" + 
			"   * sin( radians(anuncio.posX)))) AS distancia  from anuncio WHERE id_producto = ?3", nativeQuery = true)
	public double selectDistance(@Param("posX") String lat,@Param("posY") String lng, @Param("id_producto") String id_producto);

	/**
	 * Devuelve la lista de anuncios unicamente con informacion comun que tenga
	 * una distancia menor a distance entre el anuncio y la ubicacion definida
	 * por lat y lng, ordenada por sort.
	 * @param lat Latitud de la ubicacion.
	 * @param lng Longitud de la ubicacion.
	 * @param distance Distancia maxima.
	 * @param sort Forma de ordenar la lista.
	 * @return Lista de anuncios unicamente con informacion comun que tenga
	 * una distancia menor a distance entre el anuncio y la ubicacion definida
	 * por lat y lng, ordenada por sort.
	 */
	@Query(value = "select id_producto,fecha_publicacion,descripcion,titulo,posX,posY,precio,moneda,nfavoritos,nvisitas,usuario_id_usuario,nombre_categoria,estado,id_comprador, "+ "( 6371 * acos( cos( radians(?1) ) * cos( radians( anuncio.posX ) )" +
			   "* cos( radians(anuncio.posY) - radians(?2)) + sin(radians(?1))" +
			   "* sin( radians(anuncio.posX)))) AS distancia " + "FROM anuncio " +
			"WHERE ( 6371 * acos( cos( radians(?1) ) * cos( radians( anuncio.posX ) )" +
			   "* cos( radians(anuncio.posY) - radians(?2)) + sin(radians(?1))" +
			   "* sin( radians(anuncio.posX)))) <= ?3 ORDER BY ?#{#sort}", nativeQuery = true)
	public List<Anuncio> selectAnuncioCommonDistance(@Param("posX") String lat,@Param("posY") String lng,@Param("distance") String distance, Sort sort);
	
	/**
	 * Devuelve la lista de identificadores de anuncios de la categoria
	 * category.
	 * @param category Categoria de los anuncios.
	 * @return Lista de identificadores de anuncios de la categoria
	 * category.
	 */
	@Query("select idProducto from Anuncio where nombre_categoria=:nombre_categoria")
	public List<Long> selectAnuncioCommonCategory(@Param("nombre_categoria") String category);
	
	/**
	 * Devuelve la lista de identifiadores de anuncios que contienen el termino
	 * search en su titulo.
	 * @param search Termino en el titulo de los anuncios.
	 * @return Lista de identifiadores de anuncios que contienen el termino
	 * search en su titulo.
	 */
	@Query(value = "select id_producto from anuncio where LOCATE(?1,titulo)", nativeQuery = true)
	public List<BigInteger> selectAnuncioCommonSearch(@Param("search") String search);
	
	/**
	 * Devuelve la lista de identificadores de anuncios con precio mayor que
	 * priceFrom.
	 * @param priceFrom Precio minimo de los anuncios.
	 * @return Lista de identificadores de anuncios con precio mayor que
	 * priceFrom.
	 */
	@Query("select idProducto from Anuncio where price >= :priceFrom")
	public List<Long> selectAnuncioCommonPriceFrom(@Param("priceFrom") float priceFrom);
	
	/**
	 * Devuelve la lista de identificadores de anuncios con precio menor que
	 * priceTo.
	 * @param priceTo Precio maximo de los anuncios.
	 * @return Lista de identificadores de anuncios con precio menor que
	 * priceTo.
	 */
	@Query("select idProducto from Anuncio where price <= :priceTo")
	public List<Long> selectAnuncioCommonPriceTo(@Param("priceTo") float priceTo);
	
	/**
	 * Devuelve la lista de identificadores de anuncios con fecha de publicacion
	 * mayor o igual a publishedFrom.
	 * @param publishedFrom Fecha de publicacion minima de los anuncios.
	 * @return Lista de identificadores de anuncios con fecha de publicacion
	 * mayor o igual a publishedFrom.
	 */
	@Query("select idProducto from Anuncio where publicate_date >= :publishedFrom")
	public List<Long> selectAnuncioCommonPublishedFrom(@Param("publishedFrom") String publishedFrom);
	
	/**
	 * Devuelve la lista de identificadores de anuncios con fecha de publicacion
	 * menor o igual a publishedTo.
	 * @param publishedTo Fecha de publicacion minima de los anuncios.
	 * @return Lista de identificadores de anuncios con fecha de publicacion
	 * menor o igual a publishedTo.
	 */
	@Query("select idProducto from Anuncio where publicate_date <= :publishedTo")
	public List<Long> selectAnuncioCommonPublishedTo(@Param("publishedTo") String publishedTo);
	
	/**
	 * Devuelve la lista de identificadores de anuncios cuyo propietario es
	 * identificado por owner.
	 * @param owner Identificador del propietario de los anuncios.
	 * @return Lista de identificadores de anuncios cuyo propietario es
	 * identificado por owner.
	 */ 
	@Query("select idProducto from Anuncio where id_owner = :owner")
	public List<Long> selectAnuncioCommonOwner(@Param("owner") Long owner);
	
	/**
	 * Devuelve la lista de identificadores de anuncios con estado igual a 
	 * status.
	 * @param status Estado de los anuncios.
	 * @return Lista de identificadores de anuncios con estado igual a 
	 * status.
	 */
	@Query("select idProducto from Anuncio where status = :status")
	public List<Long> selectAnuncioCommonStatus(@Param("status") String status);
	
	/**
	 * Devuelve el anuncio identificado por id_producto.
	 * @param id_producto Identificador del producto.
	 * @return Anuncio identificado por id_producto.
	 */
	@Query("from Anuncio where id_producto=:id_producto")
	public Anuncio buscarPorId(@Param("id_producto") String id_producto);
	
	/**
	 *  Actualiza el anuncio identificado por id_producto.
	 * @param publicate_date Nueva fecha de publicacion del anuncio.
	 * @param description Nueva descripcion del producto.
	 * @param title Nuevo titulo del producto.
	 * @param posX Nueva latitud de la ubicacion del anuncio.
	 * @param posY Nueva longitud de la ubicacion del anuncio.
	 * @param price Nuevo precio del producto.
	 * @param currency Nueva moneda del precio del producto.
	 * @param id_owner Nuevo identificador del comprador del producto.
	 * @param category Nueva categoria del producto.
	 * @param id_producto Identificador del producto.
	 * @param status Nuevo estado del producto.
	 */
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
	
	/**
	 * Actualiza el numero de favoritos del anuncio identificado por 
	 * id_producto.
	 * @param nfavoritos Nuevo numero de favoritos del anuncio.
	 * @param id_producto Identificador del anuncio.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Anuncio set nfavoritos=:nfavoritos where id_producto=:id_producto")
	public void actualizarNFav(@Param("nfavoritos") Long nfavoritos, @Param("id_producto") Long id_producto);
	
	/**
	 * Actualiza el numero de visitas del anuncio identificado por 
	 * id_producto.
	 * @param nvisitas Nuevo numero de visitas del anuncio.
	 * @param id_producto Identificador del anuncio.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Anuncio set nvisitas=:nvisitas where id_producto=:id_producto")
	public void actualizarNVis(@Param("nvisitas") Long nvisitas, @Param("id_producto") Long id_producto);
	
	/**
	 * Actualiza el identificador del comprador del producto identificado por 
	 * id_producto.
	 * @param buyer_id Nuevo identificador del comprador del producto.
	 * @param id_producto Identificador del anuncio.
	 */
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Anuncio set id_comprador=:buyer_id, estado='vendido' where id_producto=:id_producto")
	public void actualizarVendido(@Param("buyer_id") Long buyer_id, @Param("id_producto") Long id_producto);
}
