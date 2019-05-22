package selit.wishes;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Representa a las subastas deseadas en la base de datos
 */
@Entity 
@Table(name="se_interesa_s", schema="selit")
public class WishS{

	/** Identificador de la subasta deseada */
	@EmbeddedId
	private WishSId WishSId;

	/**
	 * Constructor.
	 * @param wishSId Identificador de la subasta deseada.
	 */
	public WishS(WishSId wishSId) {
		super();
		WishSId = wishSId;
	}
	
	/**
	 * Constructor por defecto.
	 */
	public WishS() {
		
	}

	/**
	 * Devuelve el identificador de la subasta deseada.
	 * @return Identificador de la subasta deseada.
	 */
	public WishSId getWishSId() {
		return WishSId;
	}

	/**
	 * Cambia el identificador de la subasta deseada a whishSId.
	 * @param wishSId Nuevo identificador de la subasta deseada.
	 */
	public void setWishSId(WishSId wishSId) {
		WishSId = wishSId;
	}

}
