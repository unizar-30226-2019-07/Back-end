package selit.bid;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Representa a las pujas en la base de datos.
 */
@Entity 
@Table(name="participa", schema="selit")
public class Bid {

	/** Clave primaria embedida */
	@EmbeddedId
	private ClavePrimaria clave;
	
	/** Valor de la puja */
	@Column(name="puja")
    private float puja;
	
	/** Fecha de la puja */
	@Column(name="fecha", columnDefinition="DATE")
    private String fecha;

	/** 
	 * Constructor.
	 * @param clave Clave primaria de la puja.
	 * @param puja Valor de la puja.
	 * @param fecha Fecha de la puja.
	 */
	public Bid(ClavePrimaria clave, float puja, String fecha) {
		this.clave = clave;
		this.puja = puja;
		this.fecha = fecha;
	}

	/**
	 * Constructor por defecto de la puja.
	 */
	public Bid() {
		
	}
	
	/**
	 * Devuelve el valor de la puja.
	 * @return Valor de la puja.
	 */
	public float getPuja() {
		return puja;
	}

	/**
	 * Cambia el valor de la puja a puja.
	 * @param puja Nuevo valor de la puja.
	 */
	public void setPuja(float puja) {
		this.puja = puja;
	}

	/**
	 * Devuelve la clave primaria de la puja.
	 * @return Clave primaria de la puja.
	 */
	public ClavePrimaria getClave() {
		return clave;
	}

	/**
	 * Cambia la clave primaria a clave.
	 * @param clave Nueva clave primaria.
	 */
	public void setClave(ClavePrimaria clave) {
		this.clave = clave;
	}

	/**
	 * Devuelve la fecha de la puja.
	 * @return Fecha de la puja.
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Cambia la fecha de la puja a fecha.
	 * @param fecha Nueva fecha de la puja.
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
