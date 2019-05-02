package selit.wishes;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity 
@Table(name="se_interesa_s", schema="selit")
public class WishS{

	@EmbeddedId
	private WishSId WishSId;

	public WishS(WishSId wishSId) {
		super();
		WishSId = wishSId;
	}
	
	public WishS() {
		
	}

	public WishSId getWishSId() {
		return WishSId;
	}

	public void setWishSId(WishSId wishSId) {
		WishSId = wishSId;
	}

}
