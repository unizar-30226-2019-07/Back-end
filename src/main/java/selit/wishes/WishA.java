package selit.wishes;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity 
@Table(name="se_interesa_a", schema="selit")
public class WishA{

	@EmbeddedId
	private WishAId WishAId;

	public WishA(selit.wishes.WishAId wishAId) {
		super();
		WishAId = wishAId;
	}
	
	public WishA() {
		
	}

	public WishAId getWishAId() {
		return WishAId;
	}

	public void setWishAId(WishAId wishAId) {
		WishAId = wishAId;
	}

}
