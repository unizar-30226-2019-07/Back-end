package selit.auctions;

import selit.bid.BidAux2;
import selit.media.Media;
import java.util.List;
import selit.Location.Location;
import selit.usuario.UsuarioAux;

/** 
 * Representa a las subastas devueltas por la API 
 * */
public class SubastaAux2 {
	
	/** Identificador de la subasta */
    private Long idSubasta;

    /** Titulo de la subasta */
    private String title;
     
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
    private double distance;
    
    /** Categoria del producto */
    private String category;
        
    /** Lista de imagenes del producto */
    private List<Media> media;
    
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
	
	/** TODO: ??? */
	private boolean in_wishlist;
	
    /** Estado de la subasta (vendido o en venta) */
    private String status;
   
    /**
     * Constructor.
     * @param idSubasta Identificador de la subasta.
     * @param published Fecha de publicacion de la subasta.
     * @param description Descripcion del producto.
     * @param title  Titulo de la subasta.
     * @param location Ubicacion de la subasta.
     * @param startPrice Precio inicial de la subasta.
     * @param endDate Fecha de finalizacion de la subasta.
     * @param category Categoria del producto.
     * @param owner Propietario del producto.
     * @param lastBid Ultima puja de la subasta.
     * @param nfav Numero de favoritos de la subasta.
     * @param nvis Numero de visitas de la subasta.
     * @param media Lista de imagenes del producto.
     */
	public SubastaAux2(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, UsuarioAux owner, BidAux2 lastBid, Long nfav, Long nvis, List<Media> media) {
    	this.idSubasta = idSubasta; 
    	this.published = published;
    	this.description = description;
    	this.title = title;
    	this.location = location;
    	this.startPrice = startPrice;
    	this.endDate = endDate;
    	this.category = category;
    	this.owner = owner;
    	this.lastBid = lastBid;
		this.nfav = nfav;
		this.nvis = nvis;
		this.media = media;
    }
	
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
	 * @param media Lista de imagenes del producto.
	 * @param in_wishlist ???
	 * @param distance Distancia a la subasta.
	 * @param currency Moneda del precio de la subasta.
	 * @param status Estado (vendido o en venta) de la subasta.
	 */
	public SubastaAux2(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, UsuarioAux owner, BidAux2 lastBid, Long nfav, Long nvis, List<Media> media, boolean in_wishlist,double distance,String currency, String status) {
    	this.idSubasta = idSubasta; 
    	this.published = published;
    	this.description = description;
    	this.title = title;
    	this.location = location;
    	this.startPrice = startPrice;
    	this.endDate = endDate;
    	this.category = category;
    	this.owner = owner;
    	this.lastBid = lastBid;
		this.nfav = nfav;
		this.nvis = nvis;
		this.media = media;
		this.in_wishlist = in_wishlist;
		this.distance = distance;
		this.currency = currency;
		this.status = status;
    }
	
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
	 * @param media Lista de imagenes del producto.
	 * @param in_wishlist ???
	 * @param currency Moneda del precio de la subasta.
	 * @param status Estado (vendido o en venta) de la subasta.
	 */
	public SubastaAux2(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, UsuarioAux owner, BidAux2 lastBid, Long nfav, Long nvis, List<Media> media, boolean in_wishlist,String currency, String status) {
    	this.idSubasta = idSubasta; 
    	this.published = published;
    	this.description = description;
    	this.title = title;
    	this.location = location;
    	this.startPrice = startPrice;
    	this.endDate = endDate;
    	this.category = category;
    	this.owner = owner;
    	this.lastBid = lastBid;
		this.nfav = nfav;
		this.nvis = nvis;
		this.media = media;
		this.in_wishlist = in_wishlist;
		this.currency = currency;
		this.status = status;
    }
    
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
	 * @param lastBid Ultima puja de la subasta.
	 * @param nfav Numero de favoritos de la subasta.
	 * @param nvis Numero de visitas de la subasta.
	 * @param media Lista de imagenes del producto.
	 */
	public SubastaAux2(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, BidAux2 lastBid, Long nfav, Long nvis, List<Media> media) {
    	this.idSubasta = idSubasta;
    	this.published = published;
    	this.description = description;
    	this.title = title;
    	this.location = location;
    	this.startPrice = startPrice;
    	this.endDate = endDate;
    	this.category = category;
    	this.lastBid = lastBid;
		this.nfav = nfav;
		this.nvis = nvis;
		this.media = media;
    }
    
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
	 * @param lastBid Ultima puja de la subasta.
	 * @param nfav Numero de favoritos de la subasta.
	 * @param nvis Numero de visitas de la subasta.
	 * @param media Lista de imagenes del producto.
	 * @param in_wishlist ???
	 * @param distance Distancia a la subasta.
	 * @param currency Moneda del precio de la subasta.
	 */
	public SubastaAux2(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, BidAux2 lastBid, Long nfav, Long nvis, List<Media> media, boolean in_wishlist,double distance,String currency) {
    	this.idSubasta = idSubasta;
    	this.published = published;
    	this.description = description;
    	this.title = title;
    	this.location = location;
    	this.startPrice = startPrice;
    	this.endDate = endDate;
    	this.category = category;
    	this.lastBid = lastBid;
		this.nfav = nfav;
		this.nvis = nvis;
		this.media = media;
		this.in_wishlist = in_wishlist;
		this.distance = distance;
		this.currency = currency;
    }
    
	/**
	 * Constructor por defecto.
	 */
    public SubastaAux2() {
    	
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
	public double getDistance() {
		return distance;
	}

	/**
	 * Cambia la distancia a la subasta a distance.
	 * @param distance Nueva distancia a la subasta.
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Devuelve la lista de imagenes del producto.
	 * @return Lista de imagenes del producto.
	 */
	public List<Media> getMedia() {
		return media;
	}

	/**
	 * Cambia la lista de imagenes del producto a media.
	 * @param media Nueva lista de imagenes del producto.
	 */
	public void setMedia(List<Media> media) {
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
	
	/**
	 * ???
	 * @return ???
	 */
    public boolean getIn_wishlist() {
		return in_wishlist;
	}

    /**
     * ???
     * @param in_wishlist ???
     */
	public void setIn_wishlist(boolean in_wishlist) {
		this.in_wishlist = in_wishlist;
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
    
}

