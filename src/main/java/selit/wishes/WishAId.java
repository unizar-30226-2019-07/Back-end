package selit.wishes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/** 
 * Representa los identificadores de los anuncios deseados 
 */
@Embeddable
public class WishAId  implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Identificador del usuario */
	@Column(name="id_usuario")
    private Long idUsuario;
    
	/** Identificador del anuncio */
    @Column(name="id_producto")
    private Long idProducto;

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
	 * Devuelve el identificador del anuncio.
	 * @return Identificador del anuncio.
	 */
	public Long getIdProducto() {
		return idProducto;
	}

	/**
	 * Cambia el identificador del anuncio a idProducto.
	 * @param idProducto Nuevo identificador del anuncio.
	 */
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Constructor.
	 * @param idUsuario Identificador del usuario.
	 * @param idProducto Identificador del anuncio.
	 */
	public WishAId(Long idUsuario, Long idProducto) {
		super();
		this.idUsuario = idUsuario;
		this.idProducto = idProducto;
	}
	
	/**
	 * Constructor por defecto.
	 */
	public WishAId() {
		
	}
	
}
