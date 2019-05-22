package selit.wishes;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/** 
 * Representa a los anuncios deseados en la base de datos 
 */
@Entity 
@Table(name="se_interesa_a", schema="selit")
public class WishA{

	/** Identificador del anuncio deseado */
	@EmbeddedId
	private WishAId WishAId;
	
	/**
	 * Constructor.
	 * @param wishAId Identificador del anuncio deseado
	 */
	public WishA(selit.wishes.WishAId wishAId) {
		super();
		WishAId = wishAId;
	}
	
	/**
	 * Constructor por defecto.
	 */
	public WishA() {
		
	}

	/**
	 * Devuelve el identificador del anuncio deseado.
	 * @return Identificador del anuncio deseado.
	 */
	public WishAId getWishAId() {
		return WishAId;
	}

	/**
	 * Cambia el identificador del anuncio deseado a wishAId.
	 * @param wishAId Nuevo identificador del anuncio deseado.
	 */
	public void setWishAId(WishAId wishAId) {
		WishAId = wishAId;
	}

}
