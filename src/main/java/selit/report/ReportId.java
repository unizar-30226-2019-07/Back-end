package selit.report;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Representa el identificador del informe.
 */
@Embeddable
public class ReportId  implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Identificador del usuario evaluado */
	@Column(name="id_evaluado")
    private Long id_evaluado;
    
	/** Identificador del usuario informador */
    @Column(name="id_informador")
    private Long id_informador;
	
    /**
     * Devuelve el identificador del usuario evaluado.
     * @return Identificador del usuario evaluado.
     */
	public Long getId_evaluado() {
		return id_evaluado;
	}

	/**
	 * Cambia el identificador del usuario evaluado a id_evaluado.
	 * @param id_evaluado Nuevo identificador del usuario evaluado.
	 */
	public void setId_evaluado(Long id_evaluado) {
		this.id_evaluado = id_evaluado;
	}

	/**
	 * Devuelve el identificador del usuario informador.
	 * @return Identificador del usuario informador.
	 */
	public Long getId_informador() {
		return id_informador;
	}

	/**
	 * Cambia el identificador del usuario informador a id_informador.
	 * @param id_informador Nuevo identificador del usuario informador.
	 */
	public void setId_informador(Long id_informador) {
		this.id_informador = id_informador;
	}


	/**
	 * Constructor.
	 * @param id_evaluado Identificador del usuario evaluado.
	 * @param id_informador Identificador del usuario informador.
	 */
	public ReportId(Long id_evaluado, Long id_informador) {
		super();
		this.id_evaluado = id_evaluado;
		this.id_informador = id_informador;
	}

	/**
	 * Constructor por defecto.
	 */
	public ReportId() {
		
	}
	
}
