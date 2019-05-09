package selit.report;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
@Table(name="informe", schema="selit")
public class Report {
	
	@EmbeddedId
	private ReportId ReportId;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="fecha_realizacion", columnDefinition="DATE")
	private String fecha_realizacion;
	
	@Column(name="estado_informe")
	private String estado_informe;
	
	@Column(name="asunto")
	private String asunto;
	
	public Report() {
		
	}

	public Report(selit.report.ReportId reportId, String descripcion, String fecha_realizacion, String estado_informe,
			String asunto) {
		super();
		ReportId = reportId;
		this.descripcion = descripcion;
		this.fecha_realizacion = fecha_realizacion;
		this.estado_informe = estado_informe;
		this.asunto = asunto;
	}

	public ReportId getReportId() {
		return ReportId;
	}

	public void setReportId(ReportId reportId) {
		ReportId = reportId;
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
