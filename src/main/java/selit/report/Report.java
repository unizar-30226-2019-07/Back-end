package selit.report;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/** 
 * Representa a los informes en la base de datos 
 */
@Entity 
@Table(name="informe", schema="selit")
public class Report {
	
	/** Identificador del informe */
	@EmbeddedId
	private ReportId ReportId;
	
	/** Descripcion del informe */
	@Column(name="descripcion")
	private String descripcion;
	
	/** Fecha de realizacion del informe */
	@Column(name="fecha_realizacion", columnDefinition="DATE")
	private String fecha_realizacion;
	
	/** Estado del informe (pendiente o resuelto) */
	@Column(name="estado_informe")
	private String estado_informe;
	
	/** Asunto del informe */
	@Column(name="asunto")
	private String asunto;
	
	/** 
	 * Constructor por defecto.
	 */
	public Report() {
		
	}

	/**
	 * Constructor.
	 * @param reportId Identificador del reporte.
	 * @param descripcion Descripcion del reporte.
	 * @param fecha_realizacion Fecha de realizacion del reporte.
	 * @param estado_informe Estado (pendiente o resuelto) del informe.
	 * @param asunto Asunto del informe.
	 */
	public Report(selit.report.ReportId reportId, String descripcion, String fecha_realizacion, String estado_informe,
			String asunto) {
		super();
		ReportId = reportId;
		this.descripcion = descripcion;
		this.fecha_realizacion = fecha_realizacion;
		this.estado_informe = estado_informe;
		this.asunto = asunto;
	}

	/**
	 * Devuelve el identificador del informe.
	 * @return Identificador del informe.
	 */
	public ReportId getReportId() {
		return ReportId;
	}

	/**
	 * Cambia el identificador del informe a reportId.
	 * @param reportId Nuevo identificador del informe.
	 */
	public void setReportId(ReportId reportId) {
		ReportId = reportId;
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
