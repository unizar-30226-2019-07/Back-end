package selit.producto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import selit.producto.Producto;

@Entity
@Table(name="subasta", schema="selit")
public class Subasta extends Producto {

    @Column(name="f_finalizacion", columnDefinition="DATE")
    private String ending_date;

    @Column(name="precio")
    private float price;

	public Subasta() {

	}

	public String getEnding_date() {
		return ending_date;
	}

	public void setEnding_date(String ending_date) {
		this.ending_date = ending_date;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
