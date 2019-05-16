package selit.security;

public class Constants {

	// Spring Security

	public static final String LOGIN_URL = "/login";
	public static final String REGISTER_URL = "/users";
	public static final String VERIFY_URL = "/verify";
	public static final String PRODUCTO_URL = "/products/{product_id}";
	public static final String PRODUCTOS_URL = "/products";
	public static final String SUBASTA_URL = "/auctions/{auction_id}";
	public static final String SUBASTAS_URL = "/auctions";
	public static final String IMAGES_URL = "/pictures/{picture_id}";
	public static final String SUBASTAS_SELL_URL = "/auctions/{auction_id}/sell";
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";

	// JWT

	public static final String ISSUER_INFO = "https://selit.naval.cat/";
	public static final String SUPER_SECRET_KEY = "selitenterprise";
	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

}
