package selit.report;

import selit.usuario.UsuarioAux;

/**
 * Representa a los informes devueltos por la API
 */
public class ReportAux2 {
	
	/** Identificador del usuario evaluado. */
    private UsuarioAux Evaluado;
    
    /** Identificador del usuario informador */
    private UsuarioAux Informador;
	
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
	public ReportAux2() {
		
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
	public ReportAux2(UsuarioAux Evaluado, UsuarioAux Informador, String descripcion, String fecha_realizacion,
			String estado_informe, String asunto) {
		super();
		this.Evaluado = Evaluado;
		this.Informador = Informador;
		this.descripcion = descripcion;
		this.fecha_realizacion = fecha_realizacion;
		this.estado_informe = estado_informe;
		this.asunto = asunto;
	}

	/**
	 * Devuelve el usuario evaluador.
	 * @return Usuario evaluador.
	 */
	public UsuarioAux getEvaluado() {
		return Evaluado;
	}

	/**
	 * Cambia el usuario evaluador.
	 * @param Informador Nuevo usuario evaluador.
	 */
	public void setEvaluado(UsuarioAux Evaluado) {
		this.Evaluado = Evaluado;
	}

	/**
	 * Devuelve el usuario informador.
	 * @return Usuario informador.
	 */
	public UsuarioAux getInformador() {
		return Informador;
	}

	/**
	 * Cambia el usuario informador.
	 * @param Informador Nuevo usuario informador.
	 */
	public void setInformador(UsuarioAux Informador) {
		this.Informador = Informador;
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
