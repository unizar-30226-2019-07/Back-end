package selit.bid;

import java.util.Date;

import selit.usuario.Usuario;

public class Bid {
	
	private float amount;
	
	private Usuario bidder;
	
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
