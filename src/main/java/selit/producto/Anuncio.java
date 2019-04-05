package selit.producto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="anuncio", schema="selit")
public class Anuncio {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_producto")
    private Long idProducto;

	@Column(name="fecha_publicacion", columnDefinition="DATE")
    private String publicate_date;

    @Column(name="descripcion")
    private String description;

	@Column(name="titulo")
    private String title;

    @Column(name="posX")
    private float posX;
    
    @Column(name="posY")
    private float posY;


    @Column(name="precio")
    private float price;

	@Column(name="moneda")
    private String currency;

	@Column(name="nfavoritos")
    private int nfav;

    @Column(name="nvisitas")
    private int nvis;

    @Column(name="usuario_id_usuario")
    private Long id_owner;

    @Column(name="nombre_categoria")
    private String category;
    
    @Column(name="estado")
    private String status;
    
    public Anuncio() {
    	
    }
    
    public Anuncio(String publicate_date,String description,String title,float posX,float posY,
    		float price,String currency,int nfav,int nvis,Long id_owner,String category,String status) {
		super();
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.posX = posX;
		this.posY = posY;
		this.price = price;
		this.currency = currency;
		this.nfav = nfav;
		this.nvis = nvis;
		this.id_owner = id_owner;
		this.category = category;
		this.status = status;
	} 
    
	public Anuncio(Long idProducto,String publicate_date,String description,String title,float price){
	  this.idProducto = idProducto;
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

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
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

	public Long getId_owner() {
		return id_owner;
	}

	public void setId_owner(Long id_owner) {
		this.id_owner = id_owner;
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

}
