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

    @Column(name="ubicacion")
    private String location;
    
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
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
    
}
