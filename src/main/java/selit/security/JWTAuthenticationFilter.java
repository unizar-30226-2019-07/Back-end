package selit.security;

import static selit.security.Constants.HEADER_AUTHORIZACION_KEY;
import static selit.security.Constants.ISSUER_INFO;
import static selit.security.Constants.SUPER_SECRET_KEY;
import static selit.security.Constants.TOKEN_BEARER_PREFIX;
import static selit.security.Constants.TOKEN_EXPIRATION_TIME;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ResponseBody;

import selit.usuario.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import selit.usuario.UsuarioController;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private customAuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(customAuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Usuario credenciales = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					credenciales.getEmail(), credenciales.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
				.setSubject(auth.getPrincipal().toString())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY).compact();
		
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		
		response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + " " + token);
	}
}
