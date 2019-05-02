package selit.bid;

import selit.usuario.Usuario;

public class BidAux2 {
	
	private float amount;
	
	private Usuario bidder;
	
	private String date;

	
	
	public BidAux2(float amount, Usuario bidder, String date) {
		this.amount = amount;
		this.bidder = bidder;
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Usuario getBidder() {
		return bidder;
	}

	public void setBidder(Usuario bidder) {
		this.bidder = bidder;
	}

	public String getString() {
		return date;
	}

	public void setString(String date) {
		this.date = date;
	}
	
}
