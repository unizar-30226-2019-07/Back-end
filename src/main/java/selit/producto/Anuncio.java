package selit.producto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import selit.producto.Producto;

@Entity
@Table(name="anuncio", schema="selit")
public class Anuncio extends Producto {

    @Column(name="precio")
    private float price;

	@Column(name="nfavoritos")
    private String nfav;

    @Column(name="nvisitas")
    private float nvis;

	public Anuncio() {

	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getNfav() {
		return nfav;
	}

	public void setNfav(String nfav) {
		this.nfav = nfav;
	}

	public float getNvis() {
		return nvis;
	}

	public void setNvis(float nvis) {
		this.nvis = nvis;
	}
	

}
