package selit.security;

import static selit.security.Constants.LOGIN_URL;
import static selit.security.Constants.REGISTER_URL;
import static selit.security.Constants.VERIFY_URL;
import static selit.security.Constants.PRODUCTO_URL;
import static selit.security.Constants.PRODUCTOS_URL;
import static selit.security.Constants.SUBASTA_URL;
import static selit.security.Constants.SUBASTAS_URL;
import static selit.security.Constants.IMAGES_URL;
import static selit.security.Constants.SUBASTAS_SELL_URL;
import selit.security.customAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configura la seguridad http
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	/** Servicio de datos de usuarios */
	private UserDetailsService userDetailsService;

	/**
	 * Constructor.
	 * @param userDetailsService Nuevo servicio de datos de usuarios.
	 */
	public WebSecurity(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Devuelve un nuevo cifrador de contrasenyas.
	 * @return Nuevo cifrador de contrasenyas.
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		/*
		 * 1. Se desactiva el uso de cookies
		 * 2. Se activa la configuración CORS con los valores por defecto
		 * 3. Se desactiva el filtro CSRF
		 * 4. Se indica que el login no requiere autenticación
		 * 5. Se indica que el resto de URLs esten securizadas
		 */
		httpSecurity
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.cors().and()
			.csrf().disable()
			.authorizeRequests().antMatchers(HttpMethod.POST, LOGIN_URL).permitAll().and()
			.authorizeRequests().antMatchers(HttpMethod.POST, REGISTER_URL).permitAll().and()
			.authorizeRequests().antMatchers(HttpMethod.POST, VERIFY_URL).permitAll().and()
			.authorizeRequests().antMatchers(HttpMethod.GET, PRODUCTOS_URL).permitAll().and()
			.authorizeRequests().antMatchers(HttpMethod.GET, PRODUCTO_URL).permitAll().and()
			.authorizeRequests().antMatchers(HttpMethod.GET, SUBASTAS_URL).permitAll().and()
			.authorizeRequests().antMatchers(HttpMethod.GET, SUBASTA_URL).permitAll().and()
			.authorizeRequests().antMatchers(HttpMethod.GET, IMAGES_URL).permitAll().and()
			.authorizeRequests().antMatchers(HttpMethod.PUT, SUBASTAS_SELL_URL).permitAll()
			.anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter( new customAuthenticationManager()))
				.addFilter(new JWTAuthorizationFilter( new customAuthenticationManager()));
	}


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Se define la clase que recupera los usuarios y el algoritmo para procesar las passwords
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	/**
	 * Devuelve una nueva configuracion CORS (Cross-Origin Resource Sharing).
	 * @return Nueva configuracion CORS (Cross-Origin Resource Sharing).
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
