package selit.bid;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClavePrimaria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="usuario_id_usuario")
    private Long usuario_id_usuario;
	
	@Column(name="subasta_id_producto")
    private Long subasta_id_producto;
	
	@Column(name="subasta_id_usuario")
    private Long subasta_id_usuario;
	
	public ClavePrimaria() {
		
	}
	
	public ClavePrimaria(Long usuario_id_usuario, Long subasta_id_producto, Long subasta_id_usuario) {
		this.usuario_id_usuario = usuario_id_usuario;
		this.subasta_id_producto = subasta_id_producto;
		this.subasta_id_usuario = subasta_id_usuario;
	}

	public Long getUsuario_id_usuario() {
		return usuario_id_usuario;
	}

	public void setUsuario_id_usuario(Long usuario_id_usuario) {
		this.usuario_id_usuario = usuario_id_usuario;
	}

	public Long getSubasta_id_producto() {
		return subasta_id_producto;
	}

	public void setSubasta_id_producto(Long subasta_id_producto) {
		this.subasta_id_producto = subasta_id_producto;
	}

	public Long getSubasta_id_usuario() {
		return subasta_id_usuario;
	}

	public void setSubasta_id_usuario(Long subasta_id_usuario) {
		this.subasta_id_usuario = subasta_id_usuario;
	}

	@Override
	public String toString() {
		return "ClavePrimaria [usuario_id_usuario=" + usuario_id_usuario + ", subasta_id_producto="
				+ subasta_id_producto + ", subasta_id_usuario=" + subasta_id_usuario + "]";
	}
	
	
	
}
