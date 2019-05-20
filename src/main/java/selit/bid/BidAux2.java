package selit.bid;

import selit.usuario.UsuarioAux;

/**
 * Representa a las pujas devueltas por la API.
 */
public class BidAux2 {
	
	/** Valor de la puja */
	private float amount;
	
	/** Pujador */
	private UsuarioAux bidder;
	
	/** Fecha de la puja */
	private String date;

	/**
	 * Constructor.
	 * @param amount Valor de la puja.
	 * @param bidder Pujador.
	 * @param date Fecha de la puja.
	 */
	public BidAux2(float amount, UsuarioAux bidder, String date) {
		this.amount = amount;
		this.bidder = bidder;
		this.date = date;
	}

	/**
	 * Constructor por defecto.
	 */
	public BidAux2() {

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
	 * Devuelve el pujador.
	 * @return Pujador.
	 */
	public UsuarioAux getBidder() {
		return bidder;
	}

	/**
	 * Cambia el pujador a bidder.
	 * @param bidder Nuevo pujador.
	 */
	public void setBidder(UsuarioAux bidder) {
		this.bidder = bidder;
	}

	/**
	 * Devuelve la fecha de la puja. 
	 * @return Fecha de la puja.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Cambia la fecha de la puja a date.
	 * @param date Nueva fecha de la puja.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
}
