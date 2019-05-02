package selit.auctions;

import selit.bid.BidAux2;
import java.util.List;
import selit.Location.Location;
import selit.picture.Picture;
import selit.usuario.UsuarioAux;

public class SubastaAux {
	
    private Long idSubasta;
    
    private String type;

    private String title;
    
    private Long owner_id;
    
    private String endDate;
    
    private UsuarioAux owner;
    
    private String description;

    private String published;
	
    private Location location;

    private Long distance;
    
    private String category;
        
    private List<Picture> media;
    
    private float startPrice;
    
    private String currency;
    
    private BidAux2 lastBid;
    
    public SubastaAux(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, UsuarioAux owner, BidAux2 lastBid) {
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
    }
    
    // Con id_usuario en ved del usuario
    public SubastaAux(Long idSubasta,String published,String description, String title,Location location,float startPrice,String endDate,
    		String category, Long owner_id, BidAux2 lastBid) {
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
    }
    
    public SubastaAux() {
    	
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

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Picture> getMedia() {
		return media;
	}

	public void setMedia(List<Picture> media) {
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

	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}	
    
}

