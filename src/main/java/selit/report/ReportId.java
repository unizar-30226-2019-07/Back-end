package selit.report;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReportId  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="id_evaluado")
    private Long id_evaluado;
    
    @Column(name="id_informador")
    private Long id_informador;
	
	public Long getId_evaluado() {
		return id_evaluado;
	}



	public void setId_evaluado(Long id_evaluado) {
		this.id_evaluado = id_evaluado;
	}



	public Long getId_informador() {
		return id_informador;
	}



	public void setId_informador(Long id_informador) {
		this.id_informador = id_informador;
	}


	public ReportId(Long id_evaluado, Long id_informador) {
		super();
		this.id_evaluado = id_evaluado;
		this.id_informador = id_informador;
	}

	public ReportId() {
		
	}
	
}
