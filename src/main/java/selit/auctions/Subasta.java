package selit.auctions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a las subastas guardadas en la base de datos.
 */
@Entity 
@Table(name="subasta", schema="selit")
public class Subasta {
	
	/** Identificador de la subasta */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_subasta")
    private Long idSubasta;

    /** Fecha de publicacion de la subasta */
	@Column(name="fecha_publicacion", columnDefinition="DATE")
    private String publicate_date;

	/** Descripcion del producto */
    @Column(name="descripcion")
    private String description;

    /** Titulo de la subasta */
	@Column(name="titulo")
    private String title;
	
	/** Fecha de finalizacion de la subasta */
	@Column(name="fecha_finalizacion", columnDefinition="DATE")
    private String fecha_finalizacion;
	
	/** Precio inicial de la subasta */
	@Column(name="precio_salida")
    private Float startPrice;
	
	/** Identificador del ropietario del producto */ 
    @Column(name="usuario_id_usuario")
    private Long id_owner;
    
    /** Categoria del producto */
    @Column(name="nombre_categoria")
    private String category;

    /** Latitud de la ubicacion de la subasta */
    @Column(name="posX")
    private float posX;

    /** Longitud de la ubicacion de la subasta */
    @Column(name="posY")
    private float posY;
    
    /** Estado de la subasta (vendido o en venta) */
    @Column(name="estado")
    private String status;
    
    /** Moneda del precio de la subasta */
    @Column(name="moneda")
    private String currency;
    
    /** Numero de favoritos de la subasta */
	@Column(name="nfavoritos")
    private Long nfav;

	/** Numero de visitas de la subasta */
    @Column(name="nvisitas")
    private Long nvis;
    
    /**
     * Constructor por defecto.
     */
	public Subasta() {

    }

	/**
	 * Constructor.
	 * @param idSubasta Identificador de la subasta.
	 * @param publicate_date Fecha de publicacion de la subasta.
	 * @param description Descripcion del producto.
	 * @param title Titulo de la subasta.
	 * @param fecha_finalizacion Fecha de finalizacion de la subasta.
	 * @param startPrice Precio inicial de la subasta.
	 * @param id_owner Identificador del propietario del producto.
	 * @param category Categoria del producto.
	 * @param posX Latitud de la ubicacion de la subasta.
	 * @param posY Longitud de la ubicacion de la subasta.
	 * @param nfav Numero de favoritos de la subasta.
	 * @param nvis Numero de visitas de la subasta.
	 */
	public Subasta(Long idSubasta, String publicate_date, String description, String title, String fecha_finalizacion,
			Float startPrice, Long id_owner, String category, float posX, float posY, Long nfav, Long nvis) {
		this.idSubasta = idSubasta;
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.fecha_finalizacion = fecha_finalizacion;
		this.startPrice = startPrice;
		this.id_owner = id_owner;
		this.category = category;
		this.posX = posX;
		this.posY = posY;
		this.nfav = nfav;
		this.nvis = nvis;
	}

	/**
	 * Constructor.
	 * @param publicate_date Fecha de publicacion de la subasta.
	 * @param description Descripcion del producto.
	 * @param title Titulo de la subasta. 
	 * @param fecha_finalizacion Fecha de inicializacion de la subasta.
	 * @param startPrice Precio inicial de la subasta.
	 * @param id_owner Identificador del propietario del producto.
	 * @param category Categoria del producto.
	 * @param posX Latitud de la ubicacion de la subasta.
	 * @param posY Longitud de la ubicacion de la subasta.
	 * @param status Estado  (en venta o vendido) de la subasta.
	 * @param currency Moneda del precio de la subasta.
	 * @param nfav Numero de favoritos de la subasta.
	 * @param nvis Numero de visitas de la subasta.
	 */
	public Subasta( String publicate_date, String description, String title, String fecha_finalizacion, Float startPrice,
			Long id_owner, String category, float posX, float posY, String status, String currency, Long nfav, Long nvis) {
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.fecha_finalizacion = fecha_finalizacion;
		this.startPrice = startPrice;
		this.id_owner = id_owner;
		this.category = category;
		this.posX = posX;
		this.posY = posY;
		this.status = status;
		this.currency = currency;
		this.nfav = nfav;
		this.nvis = nvis;
	}
	
	/**
	 * Constructor.
	 * @param idSubasta Identificador de la subasta.
	 * @param publicate_date Fecha de publicacion de la subasta.
	 * @param description Descripcion del producto.
	 * @param title Titulo de la subasta.
	 * @param fecha_finalizacion Fecha de inicializacion de la subasta.
	 * @param startPrice Precio inicial de la subasta.
	 * @param id_owner Identificador del propietario del producto.
	 * @param category Categoria del producto.
	 * @param posX Latitud de la ubicacion de la subasta.
	 * @param posY Longitud de la ubicacion de la subasta.
	 * @param currency Moneda del precio de la subasta.
	 * @param nfav Numero de favoritos de la subasta.
	 * @param nvis Numero de visitas de la subasta.
	 * @param status Estado (vendido o en venta) de la subasta.
	 */
	public Subasta(Long idSubasta, String publicate_date, String description, String title, String fecha_finalizacion,
			Float startPrice, Long id_owner, String category, float posX, float posY, String currency, Long nfav, Long nvis, String status) {
		this.idSubasta = idSubasta;
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.fecha_finalizacion = fecha_finalizacion;
		this.startPrice = startPrice;
		this.id_owner = id_owner;
		this.category = category;
		this.posX = posX;
		this.posY = posY;
		this.currency = currency;
		this.nfav = nfav;
		this.nvis = nvis;
		this.status = status;
	}

	/**
	 * Devuelve el identificador de la subasta.
	 * @return Identificador de la subasta.
	 */
	public Long getIdSubasta() {
		return idSubasta;
	}
	
	/**
	 * Cambia el identificador de la subasta a idSubasta.
	 * @param idSubasta Nuevo identificador de la subasta.
	 */
	public void setIdSubasta(Long idSubasta) {
		this.idSubasta = idSubasta;
	}

	/**
	 * Devuelve la fecha de publicacion de la subasta.
	 * @return Fecha de publicacion de la subasta.
	 */
	public String getPublicate_date() {
		return publicate_date;
	}

	/**
	 * Cambia la fecha de publicacion de la subasta a publicate_date.
	 * @param publicate_date Nueva fecha de publicacion de la subasta.
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
	 * Devuelve el titulo de la subasta.
	 * @return Titulo de la subasta.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Cambia el titulo de la subasta a title.
	 * @param title Nuevo titulo de la subasta.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Devuelve la fecha de finalizacion de la subasta.
	 * @return Fecha de finalizacion de la subasta.
	 */
	public String getFecha_finalizacion() {
		return fecha_finalizacion;
	}

	/**
	 * Cambia la fecha de finalizacion de la subasta a fecha_finalizacion.
	 * @param fecha_finalizacion Nueva fecha de finalizacion de la subasta.
	 */
	public void setFecha_finalizacion(String fecha_finalizacion) {
		this.fecha_finalizacion = fecha_finalizacion;
	}

	/**
	 * Devuelve el identificador del propietario del producto.
	 * @return Identificador del propietario del producto.
	 */
	public Long getId_owner() {
		return id_owner;
	}

	/**
	 * Cambia el identificador del propietario del producto a id_owner.
	 * @param id_owner Nuevo identificador del propietario del producto.
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
	 * Devuelve la latitud de la ubicacion de la subasta.
	 * @return Latitud de la ubicacion de la subasta.
	 */
	public float getPosX() {
		return posX;
	}

	/**
	 * Cambia la latitud de la ubicacion de la subasta a posX.
	 * @param posX Nueva latitud de la ubicacion de la subasta.
	 */
	public void setPosX(float posX) {
		this.posX = posX;
	}

	/**
	 * Devuelve la longitud de la ubicacion de la subasta.
	 * @return Longitud de la ubicacion de la subasta.
	 */
	public float getPosY() {
		return posY;
	}

	/**
	 * Cambia la longitud de la ubicacion de la subasta a posY.
	 * @param posY Nueva longitud de la ubicacion de la subasta.
	 */
	public void setPosY(float posY) {
		this.posY = posY;
	}

	/**
	 * Devuelve el precio inicial de la subasta.
	 * @return Precio inicial de la subasta.
	 */
	public Float getStartPrice() {
		return startPrice;
	}

	/**
	 * Cambia el precio inicial de la subasta a startPrice.
	 * @param startPrice Nuevo precio inicial de la subasta.
	 */
	public void setStartPrice(Float startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * Devuelve el estado (vendido o en venta) de la subasta.
	 * @return Estado (vendido o en venta) de la subasta.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Cambia el estado (vendido o en venta) de la subasta a status.
	 * @param status Nuevo estado (vendido o en venta) de la subasta.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Devuelve la moneda del precio de la subasta.
	 * @return Moneda del precio de la subasta.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Cambia la moneda del precio de la subasta a curreny.
	 * @param currency Nueva moneda del precio de la subasta.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	/**
	 * Devuelve el numero de favoritos de la subasta.
	 * @return Numero de favoritos de la subasta.
	 */
    public Long getNfav() {
		return nfav;
	}

    /**
     * Cambia el numero de favoritos de la subasta a nfav.
     * @param nfav Nuevo numero de favoritos de la subasta.
     */
	public void setNfav(Long nfav) {
		this.nfav = nfav;
	}

	/** 
	 * Devuelve el numero de visitas de la subasta.
	 * @return Numero de visitas de la subasta.
	 */
	public Long getNvis() {
		return nvis;
	}

	/**
	 * Cambia el numero de visitas de la subasta a nvis.
	 * @param nvis Nuevo numero de visitas de la subasta.
	 */
	public void setNvis(Long nvis) {
		this.nvis = nvis;
	}
    
}

