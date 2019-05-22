package selit.security;

import java.util.ArrayList;
import java.util.List;

import selit.usuario.Usuario;

/**
 * Proporciona operaciones para los tokens
 */
public class TokenCheck {
	
	/** Lista negra de tokens */
	private static List<String> BlackListToken = new ArrayList<String>();
	
	/**
	 * Devuelve true si el token token esta en la lista negra.
	 * @param token Token a comprobar.
	 * @return True si el token token esta en la lista negra.
	 */
	private static boolean checkToken(String token){
		return BlackListToken.contains(token);
	}
	
	/**
	 * Anyade el token token a la lista negra.
	 * @param token Token a anyadir a la lista negra.
	 */
	private static void addToken(String token) {
		BlackListToken.add(token);
	}
	
	/**
	 * Comprueba si el token token del usuario u es valido.
	 * @param token Token a comprobar.
	 * @param u Usuario propietario del token.
	 * @return True si el token es correcto.
	 */
	public static boolean checkAccess(String token, Usuario u) {
		Boolean access = true;
		if(checkToken(token)) {
			access = false;
		}
		else if(u == null ||!u.getStatus().equals("activa")) {
			access = false;
			addToken(token);
		}
		return access;
	}
	
}
