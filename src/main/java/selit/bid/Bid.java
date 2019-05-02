package selit.bid;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
@Table(name="participa", schema="selit")
public class Bid {

	@EmbeddedId
	private ClavePrimaria clave;
	
	@Column(name="puja")
    private float puja;
	
	@Column(name="fecha", columnDefinition="DATE")
    private String fecha;

	public Bid(ClavePrimaria clave, float puja, String fecha) {
		this.clave = clave;
		this.puja = puja;
		this.fecha = fecha;
	}

	public Bid() {
		
	}
	
	public float getPuja() {
		return puja;
	}

	public void setPuja(float puja) {
		this.puja = puja;
	}

	public ClavePrimaria getClave() {
		return clave;
	}

	public void setClave(ClavePrimaria clave) {
		this.clave = clave;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	
}
