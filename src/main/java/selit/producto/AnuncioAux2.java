package selit.producto;


import selit.Location.Location;
import selit.usuario.Usuario;
import selit.usuario.UsuarioLoc;

public class AnuncioAux2 {

	private Long idProducto;
	
    private String publicate_date;

    private String description;

    private String title;

    private Location location;

    private float price;

    private String currency;

    private int nfav;

    private int nvis;

    private String category;
    
    private String status;
    
    private UsuarioLoc owner;
    
    private double distance;
    
    public AnuncioAux2() {
    	
    }
    
    public AnuncioAux2(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,int nfav,int nvis,String category, String status, UsuarioLoc owner, double distance) {
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
	} 
    
    public AnuncioAux2(Long idProducto,String publicate_date,String description,String title,Location location,
    		float price,String currency,int nfav,int nvis,String category, String status, UsuarioLoc owner) {
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
	
	public UsuarioLoc getOwner() {
		return owner;
	}

	public void setOwner(UsuarioLoc owner) {
		this.owner = owner;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
