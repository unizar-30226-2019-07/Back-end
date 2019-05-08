package selit.valoracion;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity 
@Table(name="valoracion", schema="selit")
public class Valoracion {

	@EmbeddedId
	private ValoracionesId valoracionesId;
	
	@Column(name="valor")
	private float valor;
	
	@Column(name="comentario")
	private String comentario;

	@Column(name="id_subasta")
	private Long id_subasta;
	
	@Column(name="id_producto")
	private Long id_producto;
	
	public Valoracion() {
		
	}

	public Valoracion(ValoracionesId valoracionesId, float valor, String comentario, Long id_subasta,
			Long id_producto) {
		super();
		this.valoracionesId = valoracionesId;
		this.valor = valor;
		this.comentario = comentario;
		this.id_subasta = id_subasta;
		this.id_producto = id_producto;
	}

	public ValoracionesId getValoracionesId() {
		return valoracionesId;
	}

	public void setValoracionesId(ValoracionesId valoracionesId) {
		this.valoracionesId = valoracionesId;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Long getId_subasta() {
		return id_subasta;
	}

	public void setId_subasta(Long id_subasta) {
		this.id_subasta = id_subasta;
	}

	public Long getId_producto() {
		return id_producto;
	}

	public void setId_producto(Long id_producto) {
		this.id_producto = id_producto;
	}
	
	
}
