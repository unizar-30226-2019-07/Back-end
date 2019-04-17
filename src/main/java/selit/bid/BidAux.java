package selit.bid;

public class BidAux {

	public float amount;
	
	public long bidder_id;

	public BidAux(float amount, long bidder_id) {
		this.amount = amount;
		this.bidder_id = bidder_id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public long getBidder_id() {
		return bidder_id;
	}

	public void setBidder_id(long bidder_id) {
		this.bidder_id = bidder_id;
	} 
	
}
