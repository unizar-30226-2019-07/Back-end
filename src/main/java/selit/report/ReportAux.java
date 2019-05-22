package selit.report;

/**
 * Representa a los informes devueltos por la API
 */
public class ReportAux {
	
	/** Identificador del usuario evaluado. */
    private Long id_evaluado;
    
    /** Identificador del usuario informador */
    private Long id_informador;
	
    /** Descripcion del informe */
	private String descripcion;

	/** Fecha de realizacion del informe */
	private String fecha_realizacion;

	/** Estado (pendiente o resuelto) del informe */
	private String estado_informe;

	/** Asunto del informe */
	private String asunto;
	
	/**
	 * Constructor por defecto.
	 */
	public ReportAux() {
		
	}
	
	/**
	 * Constructor.
	 * @param id_evaluado Identificador del usuario evaluado.
	 * @param id_informador Identificador del usuario informador.
	 * @param descripcion Descripcion del informe.
	 * @param fecha_realizacion Fecha de realizacion del informe.
	 * @param estado_informe Estado (pendiente o resuelto) del informe.
	 * @param asunto Asunto del informe.
	 */
	public ReportAux(Long id_evaluado, Long id_informador, String descripcion, String fecha_realizacion,
			String estado_informe, String asunto) {
		super();
		this.id_evaluado = id_evaluado;
		this.id_informador = id_informador;
		this.descripcion = descripcion;
		this.fecha_realizacion = fecha_realizacion;
		this.estado_informe = estado_informe;
		this.asunto = asunto;
	}

	/**
	 * Devuelve el identificador del usuario evaluado.
	 * @return Identificador del ususario evaluado.
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
	 * @param id_informador Nuevo identificador del usuario identificador.
	 */
	public void setId_informador(Long id_informador) {
		this.id_informador = id_informador;
	}

	/**
	 * Devuelve la descripcion del informe.
	 * @return Descripcion del informe.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Cambia la descripcion del informe a descripcion.
	 * @param descripcion Nuevo descripcion del informe.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve la fecha de realizacion del informe.
	 * @return Fecha de realizacion del informe.
	 */
	public String getFecha_realizacion() {
		return fecha_realizacion;
	}

	/**
	 * Cambia la fecha de realizacion del informe a fecha_realizacion.
	 * @param fecha_realizacion Nueva fecha de realizacion del informe.
	 */
	public void setFecha_realizacion(String fecha_realizacion) {
		this.fecha_realizacion = fecha_realizacion;
	}

	/**
	 * Devuelve el estado (resuelto o pendiente) del informe.
	 * @return Estado (resuelto o pendiente) del informe.
	 */
	public String getEstado_informe() {
		return estado_informe;
	}

	/**
	 * Cambia el estado (resuelto o pendiente) del informe a estado_informe.
	 * @param estado_informe Nuevo estado (resuelto o pendiente) del informe.
	 */
	public void setEstado_informe(String estado_informe) {
		this.estado_informe = estado_informe;
	}

	/**
	 * Devuelve el asunto del informe.
	 * @return Asunto del informe.
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Cambia el asunto del informe a asunto.
	 * @param asunto Nuevo asunto del informe.
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
}
