package selit.producto;

import java.util.List;

import selit.Location.Location;
import selit.picture.Picture;
import selit.usuario.Usuario;

/**
 * Representa a los productos.
 */
public class AnuncioAux {

	/** Identificador del producto */
	private Long idProducto;
	
    /** Fecha de publicacion del anuncio */
    private String publicate_date;

	/** Descripcion del producto */
    private String description;

    /** Titulo del anuncio */
    private String title;

	/** Ubicacion del anuncio */
    private Location location;

    /** Precio del producto */
    private float price;

    /** Tipo de moneda del precio del producto */
    private String currency;

	/** Numero de favoritos del anuncio */
    private Long nfav;

	/** Numero de visitas del anuncio */
    private Long nvis;

    /** Identificador del propietario del producto */
    private Long owner_id;

    /** Categoria del anuncio */
    private String category;
    
    /** Estado del anuncio (en venta o vendido) */
    private String status;
    
    /** Propietario del producto */
    private Usuario owner;
    
    // TODO: distance?
    private double distance;
    
    /** Lista de imagenes del producto */
    private List<Picture> media;
    
    /** Identificador del comprador del producto */
    private Long buyer_id;
    
    /** Comprador del producto */
    private Usuario buyer;
    
    /**
     * Constructor por defecto.
     */
    public AnuncioAux() {
    	
    }
    
    /**
     * Constructor.
     * @param idProducto Identificador del producto.
     * @param publicate_date Fecha de publicacion del anuncio.
     * @param description Descripcion del producto.
     * @param title Titulo del producto.
     * @param location Ubicacion del anuncio.
     * @param price Precio del producto.
     * @param currency Moneda del precio del producto.
     * @param nfav Numero de favoritos del anuncio.
     * @param nvis Numero de visitas del anuncio.
     * @param owner_id Identificador del propietario del producto.
     * @param category Categoria del producto.
     * @param status Estado (en venta o vendido) del producto.
     * @param owner Propietario del producto.
     * @param distance ???
     */
    public AnuncioAux(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,Long nfav,Long nvis,Long owner_id,String category, String status, Usuario owner, double distance) {
		super();
		this.idProducto = idProducto;
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.location = location;
		this.price = price;
		this.currency = currency;
		this.nfav = nfav;
		this.nvis = nvis;
		this.owner_id = owner_id;
		this.category = category;
		this.status = status;
		this.owner = owner;
		this.distance = distance;
	} 
    
    /**
     * Constructor.
     * @param idProducto Identificador del producto.
     * @param publicate_date Fecha de publicacion del anuncio.
     * @param description Descripcion del producto.
     * @param title Titulo del producto.
     * @param location Ubicacion del anuncio.
     * @param price Precio del producto.
     * @param currency Moneda del precio del producto.
     * @param nfav Numero de favoritos del anuncio.
     * @param nvis Numero de visitas del anuncio.
     * @param owner_id Identificador del propietario del producto.
     * @param category Categoria del producto.
     * @param status Estado (en venta o vendido) del producto.
     * @param buyer_id Identificador del comprador del producto.
     * @param distance ???
     */
    public AnuncioAux(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,Long nfav,Long nvis,Long owner_id,String category, String status, Long buyer_id, double distance) {
		super();
		this.idProducto = idProducto;
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.location = location;
		this.price = price;
		this.currency = currency;
		this.nfav = nfav;
		this.nvis = nvis;
		this.owner_id = owner_id;
		this.category = category;
		this.status = status;
		this.buyer_id = buyer_id;
		this.distance = distance;
	} 
    
	/** 
	 * Devuelve el identificador del producto.
	 * @return Identificador del producto.
	 */
	public Long getIdProducto() {
		return idProducto;
	}

	/**
	 * Cambia el identificador del producto a idProducto.
	 * @param idProducto Nuevo identificador del producto.
	 */
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Devuelve la lista de imagenes del producto.
	 * @return Lista de imagenes del producto.
	 */
	public List<Picture> getMedia() {
		return media;
	}

	/**
	 * Cambia la lista de imagenes del producto a media.
	 * @param media Nueva lista de imagenes del producto.
	 */
	public void setMedia(List<Picture> media) {
		this.media = media;
	}

	/**
	 * Constructor.
	 * @param publicate_date Fecha de publicacion del anuncio.
	 * @param description Descripcion del producto.
	 * @param title Titulo del producto.
	 * @param price Precio del producto.
	 */
	public AnuncioAux(String publicate_date,String description,String title,float price){
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
	 * Devuelve la ubicacion del anuncio.
	 * @return Ubicacion del anuncio.
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Cambia la ubicacion del anuncio a location.
	 * @param location Nueva ubicacion del anuncio.
	 */
	public void setLocation(Location location) {
		this.location = location;
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
	public Long getOwner_id() {
		return owner_id;
	}

	/**
	 * Cambia el identificador del propietario del producto.
	 * @param owner_id Identificador del propietario del producto.
	 */
	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
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
	 * Devuelve el propietario del producto.
	 * @return Propietario del producto.
	 */
	public Usuario getOwner() {
		return owner;
	}

	/**
	 * Cambia el propietario del producto a owner.
	 * @param owner Nuevo propietario del producto.
	 */
	public void setOwner(Usuario owner) {
		this.owner = owner;
	}
	
	/**
	 * ???
	 * @return ??
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * ???
	 * @param distance ???
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Devuelve el comprador del producto.
	 * @return Comprador del producto.
	 */
	public Usuario getBuyer() {
		return buyer;
	}

	/**
	 * Cambia el comprador del producto a buyer.
	 * @param buyer Nuevo comprador del producto.
	 */
	public void setBuyer(Usuario buyer) {
		this.buyer = buyer;
	}

	/**
	 * Devuelve el identificador del comprador del producto.
	 * @return Identificador del comprador del producto.
	 */
	public Long getBuyer_id() {
		return buyer_id;
	}

	/**
	 * Cambia el identificador del comprador del producto a id_buyer.
	 * @param buyer_id Nuevo identificador del comprador del producto.
	 */
	public void setBuyer_id(Long buyer_id) {
		this.buyer_id = buyer_id;
	}

}
