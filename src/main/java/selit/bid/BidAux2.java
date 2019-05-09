package selit.bid;

import selit.usuario.UsuarioAux;

public class BidAux2 {
	
	private float amount;
	
	private UsuarioAux bidder;
	
	private String date;

	
	
	public BidAux2(float amount, UsuarioAux bidder, String date) {
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

	public UsuarioAux getBidder() {
		return bidder;
	}

	public void setBidder(UsuarioAux bidder) {
		this.bidder = bidder;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
