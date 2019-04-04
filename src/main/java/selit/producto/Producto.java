package selit.producto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

import selit.usuario.Usuario;

@Entity
@Table(name="producto", schema="selit")
public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_producto")
    private Long idProducto;

    @Column(name="f_publicacion", columnDefinition="DATE")
    private String publicate_date;

    @Column(name="descripcion")
    private String description;

	@Column(name="titulo")
    private String title;

    @Column(name="posX")
    private float posX;

    @Column(name="posY")
    private float posY;

	@Column(name="moneda")
    private String currency;

    @Column(name="id_vendedor")
    private Long id_owner;

    @Column(name="nombre_categoria")
    private String category;
    
    @ManyToOne
    private Usuario usuario;

	public Producto() {

	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
