package selit.auctions;

import selit.bid.BidAux2;
import selit.media.Media;
import java.util.List;
import selit.Location.Location;
import selit.usuario.UsuarioAux;

public class SubastaAux2 {
	
    private Long idSubasta;

    private String title;
     
    private String endDate;
    
    private UsuarioAux owner;
    
    private String description;

    private String published;
	
    private Location location;

    private double distance;
    
    private String category;
        
    private List<Media> media;
    
    private float startPrice;
    
    private String currency;
    
    private BidAux2 lastBid;
    
    private Long nfav;

	private Long nvis;
	
	private boolean in_wishlist;
   

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
	
	public SubastaAux2(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, UsuarioAux owner, BidAux2 lastBid, Long nfav, Long nvis, List<Media> media, boolean in_wishlist,double distance,String currency) {
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
    }
	
	public SubastaAux2(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, UsuarioAux owner, BidAux2 lastBid, Long nfav, Long nvis, List<Media> media, boolean in_wishlist,String currency) {
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
    }
    
    // Con id_usuario en vez del usuario
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
    
    // Con id_usuario en vez del usuario
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
    
    public SubastaAux2() {
    	
    }

	public Long getIdSubasta() {
		return idSubasta;
	}

	public void setIdSubasta(Long idSubasta) {
		this.idSubasta = idSubasta;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public UsuarioAux getOwner() {
		return owner;
	}

	public void setOwner(UsuarioAux owner) {
		this.owner = owner;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
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

	public float getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(float startPrice) {
		this.startPrice = startPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BidAux2 getLastBid() {
		return lastBid;
	}

	public void setLastBid(BidAux2 lastBid) {
		this.lastBid = lastBid;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	
    public boolean getIn_wishlist() {
		return in_wishlist;
	}

	public void setIn_wishlist(boolean in_wishlist) {
		this.in_wishlist = in_wishlist;
	}
    
}

