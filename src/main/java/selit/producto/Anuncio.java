package selit.producto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a los productos guardados en la base de datos.
 */
@Entity
@Table(name="anuncio", schema="selit")
public class Anuncio {

	/** Identificador del producto */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_producto")
    private Long idProducto;

    /** Fecha de publicacion del anuncio */
	@Column(name="fecha_publicacion", columnDefinition="DATE")
    private String publicate_date;

	/** Descripcion del producto */
    @Column(name="descripcion")
    private String description;

    /** Titulo del anuncio */
	@Column(name="titulo")
    private String title;

	/** Latitud de la ubicacion del anuncio */
    @Column(name="posX")
    private float posX;

    /** Longitud de la ubicacion del anuncio */
    @Column(name="posY")
    private float posY;

    /** Precio del producto */
    @Column(name="precio")
    private float price;

    /** Tipo de moneda del precio del producto */
	@Column(name="moneda")
    private String currency;

	/** Numero de favoritos del anuncio */
	@Column(name="nfavoritos")
    private Long nfav;

	/** Numero de visitas del anuncio */
    @Column(name="nvisitas")
    private Long nvis;

    /** Identificador del poseedor del producto */
    @Column(name="usuario_id_usuario")
    private Long id_owner;

    /** Categoria del anuncio */
    @Column(name="nombre_categoria")
    private String category;

    /** Estado del anuncio (en venta o vendido) */
    @Column(name="estado")
    private String status;
    
    /** Identificador del comprador del producto */
    @Column(name="id_comprador")
    private Long id_buyer;

    /**
     * Constructor por defecto.
     */
    public Anuncio() {
    	
    }

    /**
     * Constructor.
     * @param publicate_date Fecha de publicacion del anuncio. 
     * @param description Descripcion del producto.
     * @param title Titulo del producto.
     * @param posX Latitud de la ubicacion del anuncio.
     * @param posY Longitud de la ubicacion del anuncio.
     * @param price Precio del producto.
     * @param currency Moneda del precio del producto.
     * @param nfav Numero de favoritos del anuncio.
     * @param nvis Numero de visitas del anuncio.
     * @param id_owner Identificador del propietario del producto.
     * @param category Categoria del producto.
     * @param status Estado (en venta o vendido) del producto.
     */
    public Anuncio(String publicate_date,String description,String title,float posX,float posY,
    		float price,String currency,Long nfav,Long nvis,Long id_owner,String category,String status) {
		super();
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.posX = posX;
		this.posY = posY;
		this.price = price;
		this.currency = currency;
		this.nfav = nfav;
		this.nvis = nvis;
		this.id_owner = id_owner;
		this.category = category;
		this.status = status;
	}
    
  /**
   * Constructor.
   * @param id_producto Identificador del producto.
   * @param publicate_date Fecha de publicacion del anuncio.
   * @param description Descripcion del producto.
   * @param title Titulo del producto.
   * @param posX Latitud de la ubicacion del anuncio. 
   * @param posY Longitud de l ubicacion del anuncio.
   * @param price Precio del producto.
   * @param currency Moneda del precio del producto.
   * @param nfav Numero de favoritos del anuncio.
   * @param nvis Numero de visitas del anuncio.
   * @param id_owner Identificador del propietario del producto.
   * @param category Categoria del producto.
   * @param status Estado (en venta o vendido) del producto.
   */
  public Anuncio(Long id_producto,String publicate_date,String description,String title,float posX,float posY,
      float price,String currency,Long nfav,Long nvis,Long id_owner,String category,String status) {
  super();
  this.idProducto = id_producto;
  this.publicate_date = publicate_date;
  this.description = description;
  this.title = title;
  this.posX = posX;
  this.posY = posY;
  this.price = price;
  this.currency = currency;
  this.nfav = nfav;
  this.nvis = nvis;
  this.id_owner = id_owner;
  this.category = category;
  this.status = status;
}
  
  /**
   * Constructor.
   * @param id_producto Identificador del producto.
   * @param publicate_date Fecha de publicacion del anuncio.
   * @param description Descripcion del producto.
   * @param title Titulo del producto.
   * @param posX Latitud de la ubicacion del anuncio.
   * @param posY Longitud de la ubicacion del anuncio.
   * @param price Precio del producto.
   * @param currency Moneda del precio del producto.
   * @param nfav Numero de favoritos del anuncio.
   * @param nvis Numero de visitas del anuncio.
   * @param id_owner Identificador del propietario del producto.
   * @param category Categoria del producto.
   * @param status Estado del producto (en venta o vendido).
   * @param id_buyer Identificador del comprador del producto.
   */
  public Anuncio(Long id_producto,String publicate_date,String description,String title,float posX,float posY,
	      float price,String currency,Long nfav,Long nvis,Long id_owner,String category,String status, Long id_buyer) {
	  super();
	  this.idProducto = id_producto;
	  this.publicate_date = publicate_date;
	  this.description = description;
	  this.title = title;
	  this.posX = posX;
	  this.posY = posY;
	  this.price = price;
	  this.currency = currency;
	  this.nfav = nfav;
	  this.nvis = nvis;
	  this.id_owner = id_owner;
	  this.category = category;
	  this.status = status;
	  this.id_buyer = id_buyer;
	}

  	/**
  	 * Constructor.
  	 * @param idProducto Identificador del producto.
  	 * @param publicate_date Fecha de publicacion del anuncio.
  	 * @param description Descripcion del producto.
  	 * @param title Titulo del anuncio.
  	 * @param price Precio del producto.
  	 */
	public Anuncio(Long idProducto,String publicate_date,String description,String title,float price){
	  this.idProducto = idProducto;
	  this.publicate_date = publicate_date;
	  this.description = description;
	  this.title = title;
	  this.price = price;
	}

	/**
	 * Cambia el identificador del producto a idProducto.
	 * @param idProducto Nuevo identificador del producto.
	 */
	public void setId_producto(Long idProducto) {
		this.idProducto = idProducto;
	}

	/** 
	 * Devuelve el identificador del producto.
	 * @return Identificador del producto.
	 */
    public Long getId_producto() {
		return idProducto;
	}

    /**
     * Devuelve la fecha de publicacion del anuncio.
     * @return Fecha de publicacion del anuncio.
     */
    public String getPublicate_date() {
		return publicate_date;
	}

    /**
     * Cambia la fecha de publicacion del anuncio a publicate_date.
     * @param publicate_date Nueva fecha de publicacion del anuncio.
     */
	public void setPublicate_date(String publicate_date) {
		this.publicate_date = publicate_date;
	}

	/**
	 * Devuelve la descripcion del producto.
	 * @return Descripcion del producto.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Cambia la descripcion del producto a description.
	 * @param description Nueva descripcion del producto.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** 
	 * Devuelve el titulo del producto.
	 * @return Titulo del producto.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Cambia el titulo del producto a title.
	 * @param title Nuevo titulo del producot.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Devuelve la latitud de la posicion del anuncio.
	 * @return Latitud de la posicion del anuncio.
	 */
	public float getPosX() {
		return posX;
	}

	/**
	 * Cambia la latitud de la posicion del anuncio a posX.
	 * @param posX Nueva latitud de la posicion del anuncio.
	 */
	public void setPosX(float posX) {
		this.posX = posX;
	}

	/**
	 * Devuelve la longitud de la posicion del anuncio.
	 * @return Longitud de la posicion del anuncio.
	 */
	public float getPosY() {
		return posY;
	}

	/**
	 * Cambia la longitud de la posicion del anuncio a posY.
	 * @param posY Nueva longitud de la posicion del anuncio.
	 */
	public void setPosY(float posY) {
		this.posY = posY;
	}

	/**
	 * Devuelve el precio del producto.
	 * @return Precio del producto.
	 */
	public float getPrice() {
		return price;
	}

	/** 
	 * Cambia el precio del producto a precio.
	 * @param price Nuevo precio del producto.
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Devuelve la moneda del precio del producto.
	 * @return Moneda del precio del producto.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Cambia la moneda del precio del producto a currency.
	 * @param currency Nueva moneda del precio del producto.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Devuelve el numero de favoritos del anuncio.
	 * @return Numero de favoritos del anuncio.
	 */
	public Long getNfav() {
		return nfav;
	}

	/**
	 * Cambia el numero de favoritos del anuncio a nfav.
	 * @param nfav Nuevo numero de favoritos del anuncio.
	 */
	public void setNfav(Long nfav) {
		this.nfav = nfav;
	}

	/**
	 * Devuelve el numero de visitas del anuncio.
	 * @return Numero de visitas del anuncio.
	 */
	public Long getNvis() {
		return nvis;
	}

	/**
	 * Cambia el numero de visitas del anuncio a nvis.
	 * @param nvis Nuevo numero de visitas del anuncio.
	 */
	public void setNvis(Long nvis) {
		this.nvis = nvis;
	}

	/**
	 * Devuelve el identificador del propietario del producto.
	 * @return Identificador del propietario del producto.
	 */
	public Long getId_owner() {
		return id_owner;
	}

	/**
	 * Cambia el identificador del propietario del producto.
	 * @param id_owner Identificador del propietario del producto.
	 */
	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
	}

	/**
	 * Devuelve la categoria del producto.
	 * @return Categoria del producto.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Cambia la categoria del producto a category.
	 * @param category Nueva categoria del producto.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Devuelve el estado (en venta o vendido) del producto.
	 * @return Estado (en venta o vendido) del producto.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Cambia el estado (en venta o vendido) del producto a status.
	 * @param status Nuevo estado (en venta o vendido) del producto.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Devuelve el identificador del comprador del producto.
	 * @return Identificador del comprador del producto.
	 */
	public Long getId_buyer() {
		return id_buyer;
	}

	/**
	 * Cambia el identificador del comprador del producto a id_buyer.
	 * @param id_buyer Nuevo identificador del comprador del producto.
	 */
	public void setId_buyer(Long id_buyer) {
		this.id_buyer = id_buyer;
	}

}
