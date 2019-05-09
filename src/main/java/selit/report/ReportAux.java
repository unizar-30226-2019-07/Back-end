package selit.report;

public class ReportAux {
	
    private Long id_evaluado;
    
    private Long id_informador;
	
	private String descripcion;

	private String fecha_realizacion;

	private String estado_informe;

	private String asunto;
	
	public ReportAux() {
		
	}

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha_realizacion() {
		return fecha_realizacion;
	}

	public void setFecha_realizacion(String fecha_realizacion) {
		this.fecha_realizacion = fecha_realizacion;
	}

	public String getEstado_informe() {
		return estado_informe;
	}

	public void setEstado_informe(String estado_informe) {
		this.estado_informe = estado_informe;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	
	
}
