package selit.auctions;

import selit.bid.Bid;

import java.util.List;
import selit.Location.Location;
import selit.picture.Picture;
import selit.usuario.Usuario;

public class SubastaAux {
	
    private Long id;
    
    private String type;

    private String title;
    
    private Long owner_id;
    
    private String closes;
    
    private Usuario owner;
    
    private String description;

    private String published;
	
    private Location location;

    private Long distance;
    
    private String category;
    
    private String status;
    
    private List<Picture> media;
    
    private float startPrice;
    
    private String currency;
    
    private Bid lastBid;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Bid getLastBid() {
		return lastBid;
	}

	public void setLastBid(Bid lastBid) {
		this.lastBid = lastBid;
	}

	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	public String getCloses() {
		return closes;
	}

	public void setCloses(String closes) {
		this.closes = closes;
	}
	
	
    
}

