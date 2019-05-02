package selit.wishes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Embeddable
public class WishAId  implements Serializable {

    @Column(name="id_usuario")
    private Long idUsuario;
    
    @Column(name="id_producto")
    private Long idProducto;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public WishAId(Long idUsuario, Long idProducto) {
		super();
		this.idUsuario = idUsuario;
		this.idProducto = idProducto;
	}
	
	public WishAId() {
		
	}
	
}
