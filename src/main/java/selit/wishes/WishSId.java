package selit.wishes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/** 
 * Representa los identificadores de las subastas deseadas
 */
@Embeddable
public class WishSId  implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Identificador del usuario */
	@Column(name="id_usuario")
    private Long idUsuario;
    
	/** Identificador de la subasta */
    @Column(name="id_subasta")
    private Long idSubasta;

    /**
     * Devuelve el identificador del usuario.
     * @return Identificador del usuario.
     */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Cambia el identificador del usuario a idUsuario.
	 * @param idUsuario Nuevo identificador del usuario.
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Devuelve el identificador de la subasta.
	 * @return Identificador de la subasta.
	 */
	public Long getIdSubasta() {
		return idSubasta;
	}

	/**
	 * Cambia el identificador de la subasta a idSubasta. 
	 * @param idSubasta Nuevo identificador de la subasta.
	 */
	public void setIdSubasta(Long idSubasta) {
		this.idSubasta = idSubasta;
	}

	/**
	 * Constructor.
	 * @param idUsuario Identificador del usuario.
	 * @param idSubasta Identificador de la subasta.
	 */
	public WishSId(Long idUsuario, Long idSubasta) {
		super();
		this.idUsuario = idUsuario;
		this.idSubasta = idSubasta;
	}
	
	/**
	 * Constructor por defecto.
	 */
	public WishSId() {
		
	}
	
}
