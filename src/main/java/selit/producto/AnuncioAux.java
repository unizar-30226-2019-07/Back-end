package selit.producto;


import java.util.List;

import selit.Location.Location;
import selit.picture.Picture;
import selit.usuario.Usuario;

public class AnuncioAux {

	private Long idProducto;
	
    private String publicate_date;

    private String description;

    private String title;

    private Location location;

    private float price;

    private String currency;

    private int nfav;

    private int nvis;

    private Long owner_id;

    private String category;
    
    private String status;
    
    private Usuario owner;
    
    private double distance;
    
    private List<Picture> media;
    
    public AnuncioAux() {
    	
    }
    
    public AnuncioAux(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,int nfav,int nvis,Long owner_id,String category, String status, Usuario owner, double distance) {
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
    
	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public List<Picture> getMedia() {
		return media;
	}

	public void setMedia(List<Picture> media) {
		this.media = media;
	}

	public AnuncioAux(String publicate_date,String description,String title,float price){
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

	public void setLocation(Location location) {
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

	public int getNfav() {
		return nfav;
	}

	public void setNfav(int nfav) {
		this.nfav = nfav;
	}

	public int getNvis() {
		return nvis;
	}

	public void setNvis(int nvis) {
		this.nvis = nvis;
	}

	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
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
	
	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
