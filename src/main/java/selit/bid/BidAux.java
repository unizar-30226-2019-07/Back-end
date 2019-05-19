package selit.bid;

/**
 * Representa a las pujas.
 */
public class BidAux {

	/** Valor de la puja */
	public float amount;
	
	/** Identificador del pujador */
	public long bidder_id;

	/**
	 * Constructor
	 * @param amount Valor de la puja.
	 * @param bidder_id Identificador del pujador.
	 */
	public BidAux(float amount, long bidder_id) {
		this.amount = amount;
		this.bidder_id = bidder_id;
	}

	/**
	 * Devuelve el valor de la puja.
	 * @return Valor de la puja.
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * Cambia el valor de la puja a amount.
	 * @param amount Nuevo valor de la puja.
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * Devuelve el identificador del pujador.
	 * @return Identificador del pujador.
	 */
	public long getBidder_id() {
		return bidder_id;
	}

	/**
	 * Cambia el identificador del pujador a bidder_id.
	 * @param bidder_id Nuevo identificador del pujador.
	 */
	public void setBidder_id(long bidder_id) {
		this.bidder_id = bidder_id;
	} 
	
}
