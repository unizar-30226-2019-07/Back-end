package selit.auctions;

import selit.bid.BidAux2;
import java.util.List;
import selit.Location.Location;
import selit.picture.Picture;
import selit.usuario.UsuarioAux;

/**
 * Representa a las subastas.
 */
public class SubastaAux {
	
	/** Identificador de la subasta */
    private Long idSubasta;
    
    /** Estado de la subasta (vendido o en venta) */
    private String type;

    /** Titulo de la subasta */
    private String title;
    
	/** Identificador del ropietario del producto */ 
    private Long owner_id;
    
	/** Fecha de finalizacion de la subasta */
    private String endDate;
    
    /** Propietario del producto */
    private UsuarioAux owner;
    
	/** Descripcion del producto */
    private String description;

    /** Fecha de publicacion de la subasta */
    private String published;
	
    /** Ubicacion de la subasta */
    private Location location;

    /** Distancia a la subasta */
    private Long distance;
    
    /** Categoria del producto */
    private String category;
        
    /** Lista de imagenes del producto */
    private List<Picture> media;
    
	/** Precio inicial de la subasta */
    private float startPrice;
    
    /** Moneda del precio de la subasta */
    private String currency;
    
    /** Ultima puja de la subasta */
    private BidAux2 lastBid;
    
    /** Numero de favoritos de la subasta */
    private Long nfav;

	/** Numero de visitas de la subasta */
	private Long nvis;
    
	/**
	 * Constructor.
	 * @param idSubasta Identificador de la subasta.
	 * @param published Fecha de publicacion de la subasta.
	 * @param description Descripcion del producto.
	 * @param title Titulo de la subasta.
	 * @param location Ubicacion de la subasta.
	 * @param startPrice Precio inicial de la subasta.
	 * @param endDate Fecha de finalizacion de la subasta.
	 * @param category Categoria del producto.
	 * @param owner Propietario del producto.
	 * @param lastBid Ultima puja de la subasta.
	 * @param nfav Numero de favoritos de la subasta.
	 * @param nvis Numero de visitas de la subasta.
	 */
    public SubastaAux(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, UsuarioAux owner, BidAux2 lastBid, Long nfav, Long nvis) {
    	this.idSubasta = idSubasta;
    	this.published = published;
    	this.description = description;
    	this.title = title;
    	this.location = location;
    	this.startPrice = startPrice;
    	this.endDate = endDate;
    	this.category = category;
    	this.owner = owner;
    	if (owner!=null) {
    		this.owner_id = owner.getIdUsuario();
    	} else {
    		this.owner_id = null;
    	}
    	this.lastBid = lastBid;
		this.nfav = nfav;
		this.nvis = nvis;
    }
    
    /**
     * 
     * @param idSubasta Identificador de la subasta.
     * @param published Fecha de publicacion de la subasta.
     * @param description Descripcion del producto.
     * @param title Titulo de la subasta.
     * @param location Ubicacion de la subasta.
     * @param startPrice Precio inicial de la subasta.
     * @param endDate Fecha de finalizacion de la subasta.
     * @param category Categoria del producto.
     * @param owner_id Identificador del propietario del producto.
     * @param lastBid Ultima puja de la subasta.
     * @param nfav Numero de favoritos de la subasta.
     * @param nvis Numero de visitas de la subasta.
     */
    public SubastaAux(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, Long owner_id, BidAux2 lastBid, Long nfav, Long nvis) {
    	this.idSubasta = idSubasta;
    	this.published = published;
    	this.description = description;
    	this.title = title;
    	this.location = location;
    	this.startPrice = startPrice;
    	this.endDate = endDate;
    	this.category = category;
    	this.owner_id = owner_id;
    	this.lastBid = lastBid;
		this.nfav = nfav;
		this.nvis = nvis;
    }
    
    /**
     * Constructor por defecto.
     */
    public SubastaAux() {
    	
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
	 * Devuelve la ubicacion de la subasta.
	 * @return Ubicacion de la subasta.
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Cambia la ubicacion de la subasta a location.
	 * @param location Nueva ubicacion de la subasta.
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Devuelve el propietario del producto.
	 * @return Propietario del producto.
	 */
	public UsuarioAux getOwner() {
		return owner;
	}

	/** 
	 * Cambia el propietario del producto a owner.
	 * @param owner Nuevo propietario del producto.
	 */
	public void setOwner(UsuarioAux owner) {
		this.owner = owner;
	}

	/**
	 * Devuelve la fecha de publicacion de la subasta.
	 * @return Fecha de publicacion de la subasta.
	 */
	public String getPublished() {
		return published;
	}

	/**
	 * Cambia la fecha de publicacion de la subasta a published.
	 * @param published Nueva fecha de publicacion de la subasta.
	 */
	public void setPublished(String published) {
		this.published = published;
	}

	/**
	 * Devuelve la distancia a la subasta.
	 * @return Distancia a la subasta.
	 */
	public Long getDistance() {
		return distance;
	}

	/**
	 * Cambia la distancia a la subasta a distance.
	 * @param distance Nueva distancia a la subasta.
	 */
	public void setDistance(Long distance) {
		this.distance = distance;
	}

	/**
	 * Devuelve el estado (vendido o en venta) de la subasta.
	 * @return Estado (vendido o en venta) de la subasta.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Cambia el estado (vendido o en venta) de la subasta a type.
	 * @param type Nuevo estado (vendido o en venta) de la subasta.
	 */
	public void setType(String type) {
		this.type = type;
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
	 * Devuelve el precio inicial de la subasta.
	 * @return Precio inicial de la subasta.
	 */
	public float getStartPrice() {
		return startPrice;
	}

	/**
	 * Cambia el precio inicial de la subasta a startPrice.
	 * @param startPrice Nuevo precio inicial de la subasta.
	 */
	public void setStartPrice(float startPrice) {
		this.startPrice = startPrice;
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
	 * Devuelve la ultima puja de la subasta.
	 * @return Ultima puja de la subasta.
	 */
	public BidAux2 getLastBid() {
		return lastBid;
	}

	/**
	 * Cambia la ultima puja de la subasta a lastBid.
	 * @param lastBid Nueva ultima puja de la subasta.
	 */
	public void setLastBid(BidAux2 lastBid) {
		this.lastBid = lastBid;
	}

	/**
	 * Devuelve el identificador del propietario del producto.
	 * @return Identificador del propietario del producto.
	 */
	public Long getOwner_id() {
		return owner_id;
	}

	/**
	 * Cambia el identificador del propietario del producto a id_owner.
	 * @param owner_id Nuevo identificador del propietario del producto.
	 */
	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	/**
	 * Devuelve la fecha de finalizacion de la subasta.
	 * @return Fecha de finalizacion de la subasta.
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Cambia la fecha de finalizacion de la subasta a endDate.
	 * @param endDate Nueva fecha de finalizacion de la subasta.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

