package selit.usuario;

import static selit.security.Constants.HEADER_AUTHORIZACION_KEY;
import static selit.security.Constants.SUPER_SECRET_KEY;
import static selit.security.Constants.TOKEN_BEARER_PREFIX;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioRepository;
import selit.verificacion.Verificacion;
import selit.verificacion.VerificacionRepository;
import selit.Location.Location;
import selit.mail.MailMail;
import selit.security.TokenCheck;

import io.jsonwebtoken.Jwts;



@RestController   
@RequestMapping(path="/users") 
public class UsuarioController {
	
	@Autowired
	public 
	static UsuarioRepository usuarios;	
	
	@Autowired public 
	VerificacionRepository verificaciones;	
	
	public static BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsuarioController(UsuarioRepository usuarios, BCryptPasswordEncoder bCryptPasswordEncoder) {
		UsuarioController.usuarios = usuarios;
		UsuarioController.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PostMapping(path="")
	public @ResponseBody String anyadirUsuario (@RequestBody UsuarioLoc usuario, HttpServletResponse response) throws IOException {
		
		// El objeto usuario pasado en el cuerpo de la peticion tiene los 
		// atributos email, password y first_name. El resto de los atributos no 
		// nulos se deben rellenar.

		
        Usuario test = usuarios.buscarPorEmail(usuario.getEmail());
        if(test != null) {
        	String error = "The email already exists.";
			response.sendError(409, error);
			return null;
        }
        
		//Generar RANDOM
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        
        //Convierto para guardar el usuario en la BD
        Usuario user = new Usuario(usuario.getLocation().getLat(),usuario.getLocation().getLng(),
        		bCryptPasswordEncoder.encode(usuario.getPassword()),usuario.getEmail(),
        		usuario.getLast_name(),usuario.getFirst_name(),"pendiente","usuario",0);
		// Se guarda al usuario.

		usuarios.save(user);
        
        Long idUsuario = usuarios.buscarIdUsuario(usuario.getEmail());
		
        Verificacion verificacion = new Verificacion();
        verificacion.setIdUsuario(idUsuario);
        verificacion.setRandom(saltStr);
        
        //Se guarda la verificacion
        verificaciones.save(verificacion);
       
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Spring-Mail.xml");
		MailMail mm = (MailMail) context.getBean("mailMail");
		mm.sendMail("selit@gmail.com",usuario.getEmail(),"","Para activar su cuenta acceda a la siguiente direccion: http://selit.naval.cat/verify?random=" + saltStr);

		// Se contesta a la peticion con un mensaje de exito.
		response.setStatus(201);
		return "Nuevo usuario creado";
	}

	
	/* sort page y size ?? */
	@GetMapping(path="")
	public @ResponseBody List<UsuarioLoc> obtenerUsuarios(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam (name = "$sort", required = false) 
			String sort, @RequestParam(name = "email", required = false) String email) throws IOException {
		//Obtengo que usuario es el que realiza la petición
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		//Compruebo si el token es valido
		if(TokenCheck.checkAccess(token,u)) {
			List<Usuario> myUserList = new ArrayList<Usuario>();
			if(email != null) {
				if(email.equals(user)){
					myUserList.add(usuarios.buscarPorEmail(user));
				}
				else {
					myUserList.add(usuarios.buscarPorEmailCommon(user));
				}
				
			}
			else {
				if(u.getTipo().equals("administrador")) { 
					// Se devuelve con la lista de usuarios en la base de datos.
					myUserList = usuarios.findAll();
				}
				else {
					// Se devuelve con la lista de usuarios en la base de datos.
					// Solo se devuelven todos los atributos del propio usuario.					
					myUserList = usuarios.findAllCommon();
					
					 //Búsqueda del usuario para reemplazarlo con toda la información
					int i = 0;
					for(Usuario us : myUserList) {
						if(user.equals(us.getEmail())){
							break;
						}
						i++;
					}
					
					//Reemplazo del usuario
					myUserList.remove(i);
					myUserList.add(i,usuarios.buscarPorEmail(user));

				}			
							
			}	
			
			List<UsuarioLoc> userValidList = new ArrayList<UsuarioLoc>();
			for(Usuario userAux : myUserList) {
				Location loc = new Location(userAux.getPosX(), userAux.getPosY());
				
				UsuarioLoc rUser = new UsuarioLoc(userAux.getIdUsuario(),userAux.getGender(),userAux.getBirth_date(),
												loc,userAux.getRating(),userAux.getStatus(),userAux.getPassword(),userAux.getEmail(),
												userAux.getLast_name(),userAux.getFirst_name(),userAux.getTipo());
				userValidList.add(rUser);
			}
			
			

			
			return userValidList;
		}
		else {
			String error = "The user credentials doesn´t exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
		
		
	}
	
	@GetMapping(path="/{user_id}")
	public @ResponseBody UsuarioLoc obtenerUsuario(@PathVariable String user_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Obtengo que usuario es el que realiza la petición
				String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
				String user = Jwts.parser()
						.setSigningKey(SUPER_SECRET_KEY)
						.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
						.getBody()
						.getSubject();
				
				Usuario u = new Usuario();
				u = usuarios.buscarPorEmail(user);
				
				//Compruebo si el token es valido
				if(TokenCheck.checkAccess(token,u)) {
					Usuario u2 = new Usuario();
					u2 = usuarios.buscarPorId(user_id);
					
					Optional<Usuario> userOptional;
					if(u.getTipo().equals("administrador") || u.getEmail().equals(u2.getEmail())) {
						// Se devuelve el usuario con el id indicado en la ruta.
						userOptional = Optional.of(u2);
						if(!userOptional.isPresent()) {
							String error = "The user " + user_id.toString() + " does not exist.";
							response.sendError(404, error);
						}
						
					}
					else {
						userOptional =usuarios.findUserCommon(user_id);
						if(!userOptional.isPresent()) {
							String error = "The user " + user_id.toString() + " does not exist.";
							response.sendError(404, error);
						}
					}
					Usuario aux = userOptional.get();
					Location loc = new Location(aux.getPosX(), aux.getPosY());
					
					UsuarioLoc rUser = new UsuarioLoc(aux.getIdUsuario(),aux.getGender(),aux.getBirth_date(),
													loc,aux.getRating(),aux.getStatus(),aux.getPassword(),aux.getEmail(),
													aux.getLast_name(),aux.getFirst_name(),aux.getTipo());
					return rUser;
				}
				else {
					String error = "The user credentials does not exist or are not correct.";
					response.sendError(401, error);
					return null;
				}
	}
	
	@PutMapping(path="/{user_id}")
	public @ResponseBody String actualizarUsuario(@PathVariable String user_id, 
						HttpServletRequest request, @RequestBody Usuario usuario, 
						HttpServletResponse response) throws IOException {
		//Obtengo que usuario es el que realiza la petición
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		//Compruebo si el token es valido
		if(TokenCheck.checkAccess(token,u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getEmail().equals(u2.getEmail())) {
					usuarios.actualizarUsuario(usuario.getEmail(), 
							usuario.getFirst_name(), usuario.getLast_name(), 
							usuario.getGender(), usuario.getBirth_date(), usuario.getPosX(), 
							usuario.getPosY(), user_id);
				}
				else {
					String error = "You are not an administrator or the user is not you.";
					response.sendError(402, error);
					return null;
				}
			}
			else {
				String error = "The user can´t be found.";
				response.sendError(404, error);
				return null;
			}
			
		}
		else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
		// Se actualiza el usuario.
		return "OK";
	}
	
	@DeleteMapping(path="/{user_id}")
	public @ResponseBody String eliminarUsuario(@PathVariable String user_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		if(TokenCheck.checkAccess(token,u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getEmail().equals(u2.getEmail())) {
					// Se elimina el usuario
					usuarios.deleteById(Long.parseLong(user_id));
				}
				else {
					String error = "You are not an administrator or the user is not you.";
					response.sendError(402, error);
					return null;
				}
			}
			else {
				String error = "The user can´t be found.";
				response.sendError(404, error);
				return null;
			}
			
		}
		else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}			
		
		// Se devuelve mensaje de confirmacion.
		return "OK";	
	}
	
	@PostMapping(path="/{user_id}/change_password")
	public @ResponseBody String changePass(@PathVariable String user_id, 
						@RequestParam (name = "old", required = false) String oldPass, @RequestParam(name = "new") String newPass,
						HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		if(TokenCheck.checkAccess(token,u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getEmail().equals(u2.getEmail())) {
					if(oldPass!=null) {
						String pass = usuarios.searchPassword(user_id);
						
						if(bCryptPasswordEncoder.matches(oldPass, pass)) {
							usuarios.changePassword(bCryptPasswordEncoder.encode(newPass), user_id);
						}
						else {
							String error = "The passwords doesn´t match.";
							response.sendError(412, error);
							return null;
						}
					}
					else {
						usuarios.changePassword(bCryptPasswordEncoder.encode(newPass), user_id);
					}
				}
				else {
					String error = "You are not an administrator or the user is not you.";
					response.sendError(402, error);
					return null;
				}
			}
			else {
				String error = "The user can´t be found.";
				response.sendError(404, error);
				return null;
			}
			
		}
		else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}	
		return "OK";
	}
	
	
}
