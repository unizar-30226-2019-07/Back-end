package selit.security;

import java.util.ArrayList;
import java.util.List;

import selit.usuario.Usuario;

public class TokenCheck {
	
	private static List<String> BlackListToken = new ArrayList<String>();
	
	private static boolean checkToken(String token){
		return BlackListToken.contains(token);
	}
	
	private static void addToken(String token) {
		BlackListToken.add(token);
	}
	
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
