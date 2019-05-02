package selit.wishes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Embeddable
public class WishSId  implements Serializable {

    @Column(name="id_usuario")
    private Long idUsuario;
    
    @Column(name="id_subasta")
    private Long idSubasta;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdSubasta() {
		return idSubasta;
	}

	public void setIdSubasta(Long idSubasta) {
		this.idSubasta = idSubasta;
	}

	public WishSId(Long idUsuario, Long idSubasta) {
		super();
		this.idUsuario = idUsuario;
		this.idSubasta = idSubasta;
	}
	
	public WishSId() {
		
	}
	
}
