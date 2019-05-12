package selit.producto;

import java.util.List;
import selit.Location.Location;
import selit.media.Media;
import selit.usuario.UsuarioAux;

public class AnuncioAux2 {

	private Long idProducto;
	
    private String publicate_date;

    private String description;

    private String title;

    private Location location;

    private float price;

    private String currency;

    private Long nfav;

    private Long nvis;

    private String category;
    
    private String status;
    
    private UsuarioAux owner;
    
    private double distance;
    
    private List<Media> media;
    
    private boolean in_wishlist;
    
    private UsuarioAux buyer;
    

	public AnuncioAux2() {
    	
    }
	
    public AnuncioAux2(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,Long nfav,Long nvis,String category, String status, UsuarioAux owner, double distance, List<Media> media) {
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
		this.category = category;
		this.status = status;
		this.owner = owner;
		this.distance = distance;
		this.media = media;
	} 
    
    public AnuncioAux2(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,Long nfav,Long nvis,String category, String status, UsuarioAux owner, double distance, List<Media> media,boolean in_wishlist) {
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
		this.category = category;
		this.status = status;
		this.owner = owner;
		this.distance = distance;
		this.media = media;
		this.in_wishlist = in_wishlist;
	} 
    
    public AnuncioAux2(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,Long nfav,Long nvis,String category, String status, UsuarioAux owner, List<Media> media,boolean in_wishlist) {
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
		this.category = category;
		this.status = status;
		this.owner = owner;
		this.media = media;
		this.in_wishlist = in_wishlist;
	} 
    
    public AnuncioAux2(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,Long nfav,Long nvis,String category, String status, UsuarioAux owner, double distance, List<Media> media,boolean in_wishlist, UsuarioAux buyer) {
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
		this.category = category;
		this.status = status;
		this.owner = owner;
		this.distance = distance;
		this.media = media;
		this.in_wishlist = in_wishlist;
		this.buyer = buyer;
	} 
    
    public AnuncioAux2(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,Long nfav,Long nvis,String category, String status, UsuarioAux owner, List<Media> media,boolean in_wishlist, UsuarioAux buyer) {
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
		this.category = category;
		this.status = status;
		this.owner = owner;
		this.media = media;
		this.in_wishlist = in_wishlist;
		this.buyer = buyer;
	} 
    
	public AnuncioAux2(String publicate_date,String description,String title,float price){
	  this.publicate_date = publicate_date;
	  this.description = description;
	  this.title = title;
	  this.price = price;
	}
	
	public void setId_producto(Long idProducto) {
		this.idProducto = idProducto;
	}

    public Long getId_producto() {
		return idProducto;
	}
    

    public String getPublicate_date() {
		return publicate_date;
	}

	public void setPublicate_date(String publicate_date) {
		this.publicate_date = publicate_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Location getLocation() {
		return location;
	}

	public void setPosX(Location location) {
		this.location = location;
	}

		
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getNfav() {
		return nfav;
	}

	public void setNfav(Long nfav) {
		this.nfav = nfav;
	}

	public Long getNvis() {
		return nvis;
	}

	public void setNvis(Long nvis) {
		this.nvis = nvis;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public UsuarioAux getOwner() {
		return owner;
	}

	public void setOwner(UsuarioAux owner) {
		this.owner = owner;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public List<Media> getMedia() {
		return media;
	}

	public void setMedia(List<Media> media) {
		this.media = media;
	}
	
    public boolean getIn_wishlist() {
		return in_wishlist;
	}

	public void setI_wishlistn(boolean in_wishlist) {
		this.in_wishlist = in_wishlist;
	}

	public UsuarioAux getBuyer() {
		return buyer;
	}

	public void setBuyer(UsuarioAux buyer) {
		this.buyer = buyer;
	}

}
