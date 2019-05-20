package selit.bid;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clave primaria de las pujas.
 */
@Embeddable
public class ClavePrimaria implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Identificador del usuario que realiza la puja. */
	@Column(name="usuario_id_usuario")
    private Long usuario_id_usuario;
	
	/** Identificador de la subasta */
	@Column(name="subasta_id_producto")
    private Long subasta_id_producto;
	
	/** Identificador del propietario de la subasta */
	@Column(name="subasta_id_usuario")
    private Long subasta_id_usuario;
	
	/** 
	 * Constructor por defecto. 
	 * */
	public ClavePrimaria() {
		
	}
	
	/**
	 * Constructor.
	 * @param usuario_id_usuario Identificador del usuario que realiza la puja.
	 * @param subasta_id_producto Identificador de la subasta.
	 * @param subasta_id_usuario Identificador del propietario de la subasta.
	 */
	public ClavePrimaria(Long usuario_id_usuario, Long subasta_id_producto, Long subasta_id_usuario) {
		this.usuario_id_usuario = usuario_id_usuario;
		this.subasta_id_producto = subasta_id_producto;
		this.subasta_id_usuario = subasta_id_usuario;
	}

	/**
	 * Devuelve el identificador del usuario que realiza la puja.
	 * @return Identificador del usuario que realiza la puja.
	 */
	public Long getUsuario_id_usuario() {
		return usuario_id_usuario;
	}

	/**
	 * Cambia el identificador del usuario que realiza la puja a
	 * usuario_id_usuario.
	 * @param usuario_id_usuario Nuevo identificador del usuario que realiza
	 * la puja.
	 */
	public void setUsuario_id_usuario(Long usuario_id_usuario) {
		this.usuario_id_usuario = usuario_id_usuario;
	}

	/**
	 * Devuelve el identificador de la subasta.
	 * @return Identificador de la subasta.
	 */
	public Long getSubasta_id_producto() {
		return subasta_id_producto;
	}

	/**
	 * Cambia el identificador de la subasta a subasta_id_producto.
	 * @param subasta_id_producto Nuevo identificador de la subasta.
	 */
	public void setSubasta_id_producto(Long subasta_id_producto) {
		this.subasta_id_producto = subasta_id_producto;
	}

	/**
	 * Devuelve el identificador del propietario de la subasta.
	 * @return Identificador del propietario de la subasta.
	 */
	public Long getSubasta_id_usuario() {
		return subasta_id_usuario;
	}

	/**
	 * Cambia el identificador del propietario de la subasta a 
	 * subasta_id_usuario.
	 * @param subasta_id_usuario Nuevo identificador del propietario de la 
	 * subasta-
	 */
	public void setSubasta_id_usuario(Long subasta_id_usuario) {
		this.subasta_id_usuario = subasta_id_usuario;
	}

	/**
	 * Reemplazo del metodo toString().
	 */
	@Override
	public String toString() {
		return "ClavePrimaria [usuario_id_usuario=" + usuario_id_usuario + ", subasta_id_producto="
				+ subasta_id_producto + ", subasta_id_usuario=" + subasta_id_usuario + "]";
	}
	
	
	
}
