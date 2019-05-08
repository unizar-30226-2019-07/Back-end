package selit.valoracion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ValoracionesId  implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="id_valoracion")
    private Long id_valoracion;
	
	@Column(name="id_comprador")
    private Long id_comprador;
	
	@Column(name="id_anunciante")
    private Long id_anunciante;
	
	public ValoracionesId(Long id_valoracion, Long id_comprador, Long id_anunciante) {
		super();
		this.id_valoracion = id_valoracion;
		this.id_comprador = id_comprador;
		this.id_anunciante = id_anunciante;
	}

	public Long getId_valoracion() {
		return id_valoracion;
	}

	public void setId_valoracion(Long id_valoracion) {
		this.id_valoracion = id_valoracion;
	}

	public Long getId_comprador() {
		return id_comprador;
	}

	public void setId_comprador(Long id_comprador) {
		this.id_comprador = id_comprador;
	}

	public Long getId_anunciante() {
		return id_anunciante;
	}

	public void setId_anunciante(Long id_anunciante) {
		this.id_anunciante = id_anunciante;
	}
}
