package selit.auctions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="subasta", schema="selit")
public class Subasta {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_subasta")
    private Long idSubasta;

	@Column(name="fecha_publicacion", columnDefinition="DATE")
    private String publicate_date;

    @Column(name="descripcion")
    private String description;

	@Column(name="titulo")
    private String title;
	
	@Column(name="fecha_finalizacion", columnDefinition="DATE")
    private String fecha_finalizacion;
	
	@Column(name="precio_salida")
    private Float startPrice;
	
    @Column(name="usuario_id_usuario")
    private Long id_owner;
    
    @Column(name="nombre_categoria")
    private String category;

    @Column(name="posX")
    private float posX;

    @Column(name="posY")
    private float posY;
    
    public Subasta() {

    }

	public Subasta(Long idSubasta, String publicate_date, String description, String title, String fecha_finalizacion,
			Float startPrice, Long id_owner, String category, float posX, float posY) {
		this.idSubasta = idSubasta;
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.fecha_finalizacion = fecha_finalizacion;
		this.startPrice = startPrice;
		this.id_owner = id_owner;
		this.category = category;
		this.posX = posX;
		this.posY = posY;
	}

	public Subasta( String publicate_date, String description, String title, String fecha_finalizacion, Float startPrice,
			Long id_owner, String category, float posX, float posY) {
		this.publicate_date = publicate_date;
		this.description = description;
		this.title = title;
		this.fecha_finalizacion = fecha_finalizacion;
		this.startPrice = startPrice;
		this.id_owner = id_owner;
		this.category = category;
		this.posX = posX;
		this.posY = posY;
	}

	public Long getidSubasta() {
		return idSubasta;
	}

	public void setidSubasta(Long idSubasta) {
		this.idSubasta = idSubasta;
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

	public String getFecha_finalizacion() {
		return fecha_finalizacion;
	}

	public void setFecha_finalizacion(String fecha_finalizacion) {
		this.fecha_finalizacion = fecha_finalizacion;
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

	public Float getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Float startPrice) {
		this.startPrice = startPrice;
	}
  
    
}
