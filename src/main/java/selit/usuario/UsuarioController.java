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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioRepository;
import selit.valoracion.Valoracion;
import selit.valoracion.ValoracionAux;
import selit.valoracion.ValoracionAux2;
import selit.valoracion.ValoracionesRepository;
import selit.verificacion.Verificacion;
import selit.verificacion.VerificacionRepository;
import selit.wishes.WishA;
import selit.wishes.WishAId;
import selit.wishes.WishS;
import selit.wishes.WishSId;
import selit.wishes.WishesARepository;
import selit.wishes.WishesSRepository;
import selit.Location.Location;
import selit.auctions.Subasta;
import selit.auctions.SubastaAux2;
import selit.auctions.SubastaRepository;
import selit.bid.Bid;
import selit.bid.BidAux2;
import selit.bid.BidRepository;
import selit.mail.MailMail;
import selit.media.Media;
import selit.picture.Picture;
import selit.picture.PictureRepository;
import selit.producto.Anuncio;
import selit.producto.AnuncioAux2;
import selit.producto.AnuncioRepository;
import selit.report.ReportRepository;
import selit.security.TokenCheck;
import io.jsonwebtoken.Jwts;

/**
 * Controlador de las operaciones relacionadas con usuarios
 */
@RestController   
@RequestMapping(path="/users") 
public class UsuarioController {
	
	/** Repositorio de usuarios */
	@Autowired
	public static UsuarioRepository usuarios;	
	
	/** Repositorio de anuncios */
	@Autowired public 
	AnuncioRepository anuncios;	
	
	/** Repositorio de subastas */
	@Autowired public 
	SubastaRepository subastas;	
	
	/** Repositorio de verificaciones */
	@Autowired public 
	VerificacionRepository verificaciones;	
	
	/** Repositorio de imagenes */
	@Autowired public 
	PictureRepository pictures;	
	
	/** Repositorio de anuncios deseados */
	@Autowired public 
	WishesARepository wishesA;	
	
	/** Repositorio de subastas deseadas */
	@Autowired public 
	WishesSRepository wishesS;	
	
	/** Repositorio de pujas */
	@Autowired public
	BidRepository pujas;
	
	/** Repositorio de valoraciones */
	@Autowired public
	ValoracionesRepository valoraciones;
	
	/** Reposiciones de informes */
	@Autowired public
	ReportRepository informes;
	
	/** Cifrador de contrasenyas */
	public static BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Constructor del controlador de operaciones relacionadas con usuarios.
	 * @param usuarios Repositorio de usuarios inicial.
	 * @param bCryptPasswordEncoder Cifrador de contrasenyas.
	 */
	public UsuarioController(UsuarioRepository usuarios, BCryptPasswordEncoder bCryptPasswordEncoder) {
		UsuarioController.usuarios = usuarios;
		UsuarioController.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	/**
	 * Devuelve el nombre del atributo contenido en parametro por el que 
	 * ordenador o null si no coincide con ninguno.
	 * @param parametro Nombre del parametro por el que ordenar.
	 * @return Nombre del atributo por el que ordenar o null si no coincide.
	 */
	private String elegirAtributo(String parametro) {
		if (parametro.equals("id")) {
			return "id_usuario";
		} else if (parametro.equals("email")) {
			return "email";
		} else if (parametro.equals("status")) {
			return "estado_cuenta";
		} else if (parametro.equals("first_name")) {
			return "nombre";
		} else if (parametro.equals("last_name")) {
			return "apellidos";
		} else if (parametro.equals("gender")) {
			return "sexo";
		} else if (parametro.equals("birth_date")) {
			return "nacimiento";
		} else if (parametro.equals("rating")) {
			return "calificacion";
		} else {
			return null;
		}
	}

	
	// TODO: excepciones?
	/**
	 * Anyade un nuevo usuario a la base de datos.
	 * @param usuario Usuario a anyadir.
	 * @param response Respuesta http: 201 si se a creado con exito o 409 si
	 * ya existe el correo electronico del usuario a crear.
	 * @return "Nuevo usuario creado".
	 * @throws IOException
	 */
	@PostMapping(path="")
	public @ResponseBody String anyadirUsuario (@RequestBody UsuarioAux usuario, HttpServletResponse response) throws IOException {
		
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
		mm.sendMail("accounts@selit.naval.cat",usuario.getEmail(),"Activación de la cuenta","Para activar su cuenta acceda a la siguiente direccion: https://selit.naval.cat/verify?random=" + saltStr);
		((ClassPathXmlApplicationContext) context).close();
		
		// Se contesta a la peticion con un mensaje de exito.
		response.setStatus(201);
		return "Nuevo usuario creado";
	}

	/**
	 * Devuelve una lista de los usuarios contenidos en la base de datos.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 412 si algun parametro de la peticion es
	 * incorrecta o 401 si el token es incorrecto.
	 * @param sort Atributo por el que ordenar la lista.
	 * @param email Correo electronico del usuario a buscar.
	 * @param page Pagina de la lista.
	 * @param size Tamanyo de la pagina.
	 * @return Lista de usuarios contenidos en la base de datos.
	 * @throws IOException
	 */
	@GetMapping(path="")
	public @ResponseBody List<UsuarioAux> obtenerUsuarios(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam (name = "$sort", required = false) 
			String sort, @RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "$page", required = false) String page, 
			@RequestParam(name = "$size", required = false) String size) throws IOException {
		
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
			
			// Si se quiere obtener solo un usuario mediante correo.
			if(email != null) {
				if(email.equals(user)){
					myUserList.add(usuarios.buscarPorEmail(user));
				}
				else {
					myUserList.add(usuarios.buscarPorEmailCommon(user));
				}
				
			// Si se quiere ordenar el resultado (ademas de devolver una pagina concreta).
			} else if (sort != null) {
				// Si el campo sort no esta vacio, se comprueba que lo que se ha pasado como parametro
				// es un valor correcto.
				String campos[] = sort.split("\\s");
				if ( ( elegirAtributo(campos[0]) != null) &&  
					 ( campos[1].equals("DESC") || campos[1].equals("ASC") ) ) {
					
					campos[0] = elegirAtributo(campos[0]);
					
					// Dependiendo de si se pide ordenar de forma ascendente o descendente, el usuario es
					// un usuario o un administrador, y si ademas de ordenar, se quiere devolver una pagina
					// concreta, se realizan unas funciones u otras.
					if (campos[1].equals("DESC")) {
						
						if (page != null && size != null) {
							if ( (Integer.parseInt(page) >= 0) && (Integer.parseInt(size) > 0) ) {
								if (u.getTipo().equals("administrador")) {
									myUserList = usuarios.buscarUsuariosPagina(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(campos[0]).descending()));
								} else {
									myUserList = usuarios.buscarUsuariosPaginaCommon(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(campos[0]).descending()));
								}
							} else {
								response.sendError(412, "Valores incorrectos en el parametro $page o $size");
							}

						} else {
							
							if (u.getTipo().equals("administrador")) {
								myUserList = usuarios.buscarUsuariosOrdenados(Sort.by(campos[0]).descending());
							} else {
								myUserList = usuarios.buscarUsuariosOrdenadosCommon(Sort.by(campos[0]).descending());
							}
						}
					} else {
						
						if (page != null && size != null) {
							if ( (Integer.parseInt(page) >= 0) && (Integer.parseInt(size) > 0) ) {
								if (u.getTipo().equals("administrador")) {
									myUserList = usuarios.buscarUsuariosPagina(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(campos[0]).ascending()));
	
								} else {
									myUserList = usuarios.buscarUsuariosPaginaCommon(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(campos[0]).ascending()));
								}
							} else {
								response.sendError(412, "Valores incorrectos en el parametro $page o $size");
							}
							
						} else {
							
							if (u.getTipo().equals("administrador")) {
								myUserList = usuarios.buscarUsuariosOrdenados(Sort.by(campos[0]).ascending());
							} else {
								myUserList = usuarios.buscarUsuariosOrdenadosCommon(Sort.by(campos[0]).ascending());
							}
						}
					}
					
				} else {
					response.sendError(412, "Valores incorrectos en el parametro $sort");
				}
				
			// Si se quiere devolver una pagina en concreta, sin ordenar.
			} else if (page != null && size != null) {
				if (u.getTipo().equals("administrador")) {
					myUserList = usuarios.buscarUsuariosPagina(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size)));
				} else {
					myUserList = usuarios.buscarUsuariosPaginaCommon(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size)));
				}
			}
			
			// Si se quiere obtener la lista de usuarios sin modificar.
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
			
			List<UsuarioAux> userValidList = new ArrayList<UsuarioAux>();
			for(Usuario userAux : myUserList) {
				Location loc = new Location(userAux.getPosX(), userAux.getPosY());
												 				
				UsuarioAux rUser = new UsuarioAux(userAux.getIdUsuario(),userAux.getGender(),userAux.getBirth_date(),
												loc,userAux.getRating(),userAux.getStatus(),null,userAux.getEmail(),
												userAux.getLast_name(),userAux.getFirst_name(),userAux.getTipo(),new Picture(userAux.getIdImagen()));
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

	/**
	 * Devuelve el usuario con identificador user_id
	 * @param user_id Identificador del usuario
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 401 si el token no es correcto o 404 si 
	 * no existe un usuario con el identificador user_id
	 * @return Usuario con identificador user_id
	 * @throws IOException
	 */
	@GetMapping(path="/{user_id}")
	public @ResponseBody UsuarioAux obtenerUsuario(@PathVariable String user_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
					
					UsuarioAux rUser = new UsuarioAux(aux.getIdUsuario(),aux.getGender(),aux.getBirth_date(),
													loc,aux.getRating(),aux.getStatus(),null,aux.getEmail(),
													aux.getLast_name(),aux.getFirst_name(),aux.getTipo(),new Picture(aux.getIdImagen()));
					return rUser;
				}
				else {
					String error = "The user credentials does not exist or are not correct.";
					response.sendError(401, error);
					return null;
				}
	}
	
	/**
	 * Actualiza la informacion del usuario con identificador user_id en la base
	 * de datos.
	 * @param user_id Identificador del usuario.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param usuario Usuario actualizado.
	 * @param response Respuesta http: 500 si la imagen no se puede guardar, 
	 * 402 si el usuario que realiza la peticion no coincide con el que se 
	 * quiere actualizar o no es el administrador, 404 si no existe un usuario 
	 * identificado con user_id o 401 si el token es incorrecto.
	 * @return "OK" si se ha podido actualizar o null en caso contrario.
	 * @throws IOException
	 */
	@PutMapping(path="/{user_id}")
	public @ResponseBody String actualizarUsuario(@PathVariable String user_id, 
						HttpServletRequest request, @RequestBody UsuarioAux usuario, 
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
					//Guardo la imagen
					Picture p =  new Picture();
					
					//Si me pasan una imagen compruebo que no sea vacia
					if(usuario.getPicture().getBase64() != null) {
						Picture pic = new Picture(usuario.getPicture().getMime(),usuario.getPicture().getCharset(),usuario.getPicture().getBase64());
						try {
							p = pictures.save(pic);
						}
						catch(Exception e){
							String error = "The image can´t be saved.";
							response.sendError(500, error);
							return null;
						}
						
						
						//Actualizo el usuario
						usuarios.actualizarUsuario(usuario.getEmail(), 
								usuario.getFirst_name(), usuario.getLast_name(), 
								usuario.getGender(), usuario.getBirth_date(), usuario.getLocation().getLat(), 
								usuario.getLocation().getLng(), user_id,p.getIdImagen());
						
						Long idIm = u2.getIdImagen();
						//Borro la antigua imagen de perfil
						if(idIm != null) {
							pictures.deleteById(idIm);
						}
					}
					else if(usuario.getPicture().getIdImagen() == null) {
						//Actualizo el usuario
						usuarios.actualizarUsuario(usuario.getEmail(), 
								usuario.getFirst_name(), usuario.getLast_name(), 
								usuario.getGender(), usuario.getBirth_date(), usuario.getLocation().getLat(), 
								usuario.getLocation().getLng(), user_id,null);
						
						Long idIm = u2.getIdImagen();
						//Borro la antigua imagen de perfil

						if(idIm != null) {
							pictures.deleteById(idIm);
						}
					}
					else {
						//Actualizo el usuario
						usuarios.actualizarUsuario(usuario.getEmail(), 
								usuario.getFirst_name(), usuario.getLast_name(), 
								usuario.getGender(), usuario.getBirth_date(), usuario.getLocation().getLat(), 
								usuario.getLocation().getLng(), user_id,u2.getIdImagen());
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
		// Se actualiza el usuario.
		return "OK";
	}
	
	/**
	 * Elimina el usuario con identificador user_id de la base de datos.
	 * @param user_id Identificador del usuario.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 404 si no existe el usuario identificado 
	 * con user_id, 402 si no coincide el usuario que realiza la peticion con el
	 * que se quiere eliminar o 401 si el token es incorrecto.
	 * @return "OK" si se ha podido eliminar o null en caso contrario.
	 * @throws IOException
	 */
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
					Long idIm = u2.getIdImagen();					
					if(idIm != null) {
						usuarios.setImagenNull(user_id);
						pictures.deleteById(idIm);
					}
					wishesA.deleteByUsuario(Long.parseLong(user_id));
					wishesS.deleteByUsuario(Long.parseLong(user_id));
					List<Anuncio> la = anuncios.findByUsuarioIdUsuario(Long.parseLong(user_id));
					for (Anuncio a: la) {
						pictures.deleteByProducto(a.getId_producto());
					}
					List<Subasta> ls = subastas.findByUsuarioIdUsuario(Long.parseLong(user_id));
					for (Subasta s: ls) {
						pictures.deleteBySubasta(s.getIdSubasta());
					}
					anuncios.deleteByUsuario(Long.parseLong(user_id));
					subastas.deleteByUsuario(Long.parseLong(user_id));
					informes.deleteByUsuario(Long.parseLong(user_id));
					valoraciones.deleteByUsuario(Long.parseLong(user_id));
					pujas.deleteByUsuario(Long.parseLong(user_id));

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
	
	/**
	 * Cambia la contrasenya del usuario con identificador user_id.
	 * @param user_id Identificador del usuario.
	 * @param oldPass Contrasenya antigua del usuario.
	 * @param newPass Contrasenya nueva del usuario.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 412 si no coinciden las contrasenyas, 
	 * 402 si el usuario que realiza la peticion no es el que se quiere cambiar
	 * la contrasenya o no es el administrador, 404 si no existe el usuario con
	 * identificador user_id o 401 si el token es incorrecto.
	 * @return "OK" si se ha podido cambiar la contrasenya o null en caso
	 * contrario.
	 * @throws IOException
	 */
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
	
	/**
	 * Devuelve el usuario que envia la peticion.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 401 si el token es correcto.
	 * @return Usuario que envia la peticion.
	 * @throws IOException
	 */
	@GetMapping(path="/me")
	public @ResponseBody UsuarioAux obtenerUsuarioActual(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);		
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Location loc = new Location(u.getPosX(), u.getPosY());
			
			UsuarioAux rUser = new UsuarioAux(u.getIdUsuario(),u.getGender(),u.getBirth_date(),
											loc,u.getRating(),u.getStatus(),null,u.getEmail(),
											u.getLast_name(),u.getFirst_name(),u.getTipo(),new Picture(u.getIdImagen()));
			//Devuelvo la información del usuario que me ha realizado la petición
			return rUser;
		} 
		else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
	}
	
	/**
	 * Anyade el producto identificado con product_id a la lista de productos
	 * deseados del usuario identificado por user_id.
	 * @param user_id Identificador del usuario.
	 * @param product_id Identificador del producto.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 412 si el producto ya esta en su lista,
	 * 404 si no existe el producto identificado con product_id o el usuario con
	 * user_id, 402 si el usuario que envia la peticion no coincide con el 
	 * identificado con user_id o no es administrador, o 401 si el token es
	 * incorrecto.
	 * @return "OK" si se ha podido anyadir correctamente o null en caso
	 * contrario.
	 * @throws IOException
	 */
	@PutMapping(path="/{user_id}/wishes_products/{product_id}")
	public @ResponseBody String anyadirDeseadoProduct(@PathVariable String user_id,@PathVariable String product_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);	
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getIdUsuario().equals(u2.getIdUsuario())) {
					Optional<Anuncio> anun = anuncios.findById(Long.parseLong(product_id));
					if(anun.isPresent()) {
						WishAId wId = new WishAId(Long.parseLong(user_id),Long.parseLong(product_id));
						Optional<WishA> waux = wishesA.findById(wId);
						if(waux.isEmpty()) {
							//Si el anuncio no existe en la lista de deseados incremento el numero de favoritos de la subasta y la guardo en la lista de deseados
							Anuncio a = anun.get();
							Long numFav = a.getNfav();
							numFav++;
							a.setNfav(numFav);
							anuncios.actualizarNFav(Long.parseLong(product_id),numFav);
							WishA w = new WishA(wId);
							wishesA.save(w);	
						}
						else {
							String error = "The product is already on the wish list.";
							response.sendError(412, error);
							return null;
						}
											
					} 
					else {
						String error = "The product can´t be found.";
						response.sendError(404, error);
						return null;
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
	
	/**
	 * Devuelve la lista de productos deseados del usuario identificado con
	 * user_id.
	 * @param user_id Identificador del usuario.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 404 si no existe el usuario identificado
	 * por user_id, 402 si el usuario que envia la peticion no coincide con el
	 * identificado por user_id o 401 si el token es incorrecto.
	 * @param lat Latidud maxima del producto deseado.
	 * @param lng Longitud maxima del producto deseado.
	 * @return Lista de productos deseados del usuario identificado con user_id.
	 * @throws IOException
	 */
	@GetMapping(path="/{user_id}/wishes_products")
	public @ResponseBody List<AnuncioAux2> getDeseadosProduct(@PathVariable String user_id, HttpServletRequest request, HttpServletResponse response,
			@RequestParam (name = "lat") String lat,@RequestParam (name = "lng") String lng) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);	
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getIdUsuario().equals(u2.getIdUsuario())) {
					//Se devuelve la lista de deseados
					List<WishA> listWa = wishesA.buscarPorIdUsuario(Long.parseLong(user_id));
					List<AnuncioAux2> listWaId = new ArrayList<AnuncioAux2>();
					for (WishA wAux : listWa) {
						String id = wAux.getWishAId().getIdProducto().toString();
						//Obtengo los id de las imagenes
						List<Media> idList = new ArrayList<Media>();
						
						List<BigInteger> idListBI = pictures.findIdImages(id.toString());
						for(BigInteger idB : idListBI){
							Media med = new Media(idB.longValue());
							idList.add(med);
						}	
						
						//Compruebo si esta en la lista de deseados
						WishA wAux2 = wishesA.buscarInWishList(u.getIdUsuario().toString(),id);		
						boolean in = false;
						if(wAux2 != null) {
							in = true;
						}
						
						Anuncio aaux = anuncios.buscarPorId(id);
						
						Location loc = new Location(aaux.getPosX(),aaux.getPosY());
						Usuario userFind = usuarios.buscarPorId(aaux.getId_owner().toString());
						userFind = usuarios.buscarPorEmailCommon(userFind.getEmail());
						Location loc2 = new Location(userFind.getPosX(),userFind.getPosY());
						
						Location loc3 = null;
						Usuario buyer = null;
						UsuarioAux buyer2 = null;
						if (aaux.getId_buyer() != null) {
							buyer = usuarios.buscarPorId(aaux.getId_buyer().toString());
							loc3 = new Location(buyer.getPosX(), buyer.getPosY());
							buyer2 = new UsuarioAux(buyer.getIdUsuario(),buyer.getGender(),buyer.getBirth_date(),
									loc3,buyer.getRating(),buyer.getStatus(),null,buyer.getEmail(),
									buyer.getLast_name(),buyer.getFirst_name(),buyer.getTipo(),new Picture(buyer.getIdImagen()));
						}
						
						//Creo el usuario a devolver
						UsuarioAux rUser = new UsuarioAux(userFind.getIdUsuario(),userFind.getGender(),userFind.getBirth_date(),
								loc2,userFind.getRating(),userFind.getStatus(),null,userFind.getEmail(),
								userFind.getLast_name(),userFind.getFirst_name(),userFind.getTipo(),new Picture(userFind.getIdImagen()));
						
						
						AnuncioAux2 rAnuncio;	
						rAnuncio = new AnuncioAux2(aaux.getId_producto(),aaux.getPublicate_date(),aaux.getDescription(),
								aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
								aaux.getNfav(),aaux.getNvis(),aaux.getCategory(),aaux.getStatus(),
								rUser,anuncios.selectDistance(lat, lng, id.toString()),idList,in,buyer2);	
						
						listWaId.add(rAnuncio);
					}
					return listWaId;
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
			
	}
	
	/**
	 * Devuelve la lista de productos deseados del usuario que envia la 
	 * peticion.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 404 si no se encuentra el usuario o 401
	 * si el token es incorrecto.
	 * @param lat Latidud maxima del producto deseado.
	 * @param lng Longitud maxima del producto deseado.
	 * @return Lista de productos deseados del usuario que envia la peticion.
	 * @throws IOException
	 */
	@GetMapping(path="/me/wishes_products")
	public @ResponseBody List<AnuncioAux2> getMyDeseadosProduct(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (name = "lat") String lat,@RequestParam (name = "lng") String lng) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);	
		String user_id = u.getIdUsuario().toString();
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getIdUsuario().equals(u2.getIdUsuario())) {
					//Se devuelve la lista de deseados
					List<WishA> listWa = wishesA.buscarPorIdUsuario(Long.parseLong(user_id));
					List<AnuncioAux2> listWaId = new ArrayList<AnuncioAux2>();
					for (WishA wAux : listWa) {
						String id = wAux.getWishAId().getIdProducto().toString();
						//Obtengo los id de las imagenes
						List<Media> idList = new ArrayList<Media>();
						
						List<BigInteger> idListBI = pictures.findIdImages(id.toString());
						for(BigInteger idB : idListBI){
							Media med = new Media(idB.longValue());
							idList.add(med);
						}	
						
						//Compruebo si esta en la lista de deseados
						WishA wAux2 = wishesA.buscarInWishList(u.getIdUsuario().toString(),id);		
						boolean in = false;
						if(wAux2 != null) {
							in = true;
						}
						
						Anuncio aaux = anuncios.buscarPorId(id);
						
						Location loc = new Location(aaux.getPosX(),aaux.getPosY());
						Usuario userFind = usuarios.buscarPorId(aaux.getId_owner().toString());
						userFind = usuarios.buscarPorEmailCommon(userFind.getEmail());
						Location loc2 = new Location(userFind.getPosX(),userFind.getPosY());
						
						Location loc3 = null;
						Usuario buyer = null;
						UsuarioAux buyer2 = null;
						if (aaux.getId_buyer() != null) {
							buyer = usuarios.buscarPorId(aaux.getId_buyer().toString());
							loc3 = new Location(buyer.getPosX(), buyer.getPosY());
							buyer2 = new UsuarioAux(buyer.getIdUsuario(),buyer.getGender(),buyer.getBirth_date(),
									loc3,buyer.getRating(),buyer.getStatus(),null,buyer.getEmail(),
									buyer.getLast_name(),buyer.getFirst_name(),buyer.getTipo(),new Picture(buyer.getIdImagen()));
						}
						
						//Creo el usuario a devolver
						UsuarioAux rUser = new UsuarioAux(u2.getIdUsuario(),u2.getGender(),u2.getBirth_date(),
								loc2,u2.getRating(),u2.getStatus(),null,u2.getEmail(),
								u2.getLast_name(),u2.getFirst_name(),u2.getTipo(),new Picture(u2.getIdImagen()));
						
						
						AnuncioAux2 rAnuncio;	
						rAnuncio = new AnuncioAux2(aaux.getId_producto(),aaux.getPublicate_date(),aaux.getDescription(),
								aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
								aaux.getNfav(),aaux.getNvis(),aaux.getCategory(),aaux.getStatus(),
								rUser,anuncios.selectDistance(lat, lng, id.toString()),idList,in,buyer2);	
						
						listWaId.add(rAnuncio);
					}
					return listWaId;
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
			
	}
	
	/**
	 * Elimina el producto identificado con product_id de la lista de productos
	 * deseados del usuario identificado con user_id.
	 * @param user_id Identificador del usuario.
	 * @param product_id Identificador del producto.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 404 si el usuario identificado con
	 * user_id o producto identificado con product_id no existe, 402 si el
	 * usuario que envia la peticion no coincide con el identificado con
	 * user_id o no es el administrador, o 401 si el token es incorrecto.
	 * @return "OK" si se ha podido eliminar correctamente o null en caso
	 * contrario.
	 * @throws IOException
	 */
	@DeleteMapping(path="/{user_id}/wishes_products/{product_id}")
	public @ResponseBody String deleteDeseadosProduct(@PathVariable String user_id, @PathVariable String product_id, HttpServletRequest request , HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getIdUsuario().equals(u2.getIdUsuario())) {
					WishA wish = wishesA.buscarInWishList(user_id,product_id);
					if(wish != null) {		
						//Si existe el anuncio en la lista de deseados, se borra
						wishesA.deleteById(new WishAId(Long.parseLong(user_id),Long.parseLong(product_id)));			
					} 
					else {
						String error = "The product can´t be found.";
						response.sendError(404, error);
						return null;
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
	
	/**
	 * Anyade la subasta identificada con product_id a la lista de subastas
	 * deseadas del usuario identificado por user_id.
	 * @param user_id Identificador del usuario.
	 * @param product_id Identificador de la subasta.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 412 si la subasta ya esta en su lista,
	 * 404 si no existe la subasta identificada con product_id o el usuario con
	 * user_id, 402 si el usuario que envia la peticion no coincide con el 
	 * identificado con user_id o no es administrador, o 401 si el token es
	 * incorrecto.
	 * @return "OK" si se ha podido anyadir correctamente o null en caso
	 * contrario.
	 * @throws IOException
	 */
	@PutMapping(path="/{user_id}/wishes_auctions/{product_id}")
	public @ResponseBody String anyadirDeseadoAuction(@PathVariable String user_id,@PathVariable String product_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);		
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getIdUsuario().equals(u2.getIdUsuario())) {					
					Optional<Subasta> anun = subastas.findById(Long.parseLong(product_id));
					if(anun.isPresent()) {
						WishSId wId = new WishSId(Long.parseLong(user_id),Long.parseLong(product_id));
						Optional<WishS> waux = wishesS.findById(wId);
						if(waux.isEmpty()) {
							//Si la subasta no existe en la lista de deseados incremento el numero de favoritos de la subasta y la guardo en la lista de deseados
							Subasta a = anun.get();
							Long numFav = a.getNfav();
							numFav++;
							a.setNfav(numFav);
							subastas.actualizarNFav(Long.parseLong(product_id),numFav);
							WishS w = new WishS(wId);
							wishesS.save(w);	
						}
						else {
							String error = "The auction is already on the wish list.";
							response.sendError(412, error);
							return null;
						}
											
					} 
					else {
						String error = "The product can´t be found.";
						response.sendError(404, error);
						return null;
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
	
	/**
	 * Devuelve la lista de subastas deseadas del usuario identificado con
	 * user_id.
	 * @param user_id Identificador del usuario.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 404 si no existe el usuario identificado
	 * por user_id, 402 si el usuario que envia la peticion no coincide con el
	 * identificado por user_id o 401 si el token es incorrecto.
	 * @param lat Latidud maxima de la subasta deseada.
	 * @param lng Longitud maxima de la subasta deseada.
	 * @return Lista de subastas deseadas del usuario identificado con user_id.
	 * @throws IOException
	 */
	@GetMapping(path="/{user_id}/wishes_auctions")
	public @ResponseBody List<SubastaAux2> getDeseadosAuction(@PathVariable String user_id, HttpServletRequest request, HttpServletResponse response,
			@RequestParam (name = "lat") String lat,@RequestParam (name = "lng") String lng) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);	
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getIdUsuario().equals(u2.getIdUsuario())) {
					//Se devuelve la lista de deseados
					List<WishS> listWs = wishesS.buscarPorIdUsuario(Long.parseLong(user_id));
					List<SubastaAux2> listWsId = new ArrayList<SubastaAux2>();
					for (WishS wAux : listWs) {
						String id = wAux.getWishSId().getIdSubasta().toString();

						//Obtengo los id de las imagenes
						List<Media> idList = new ArrayList<Media>();
						
						List<BigInteger> idListBI = pictures.findIdImagesSub(id.toString());
						for(BigInteger idB : idListBI){
							Media med = new Media(idB.longValue());
							idList.add(med);
						}	
						
						Subasta saux = subastas.buscarPorId(id);
						
						//Compruebo si esta en la lista de deseados
						WishS wAux2 = wishesS.buscarInWishList(u.getIdUsuario().toString(),id);		
						boolean in = false;
						if(wAux2 != null) {
							in = true;
						}
						
						List<Bid> pujas2 =  pujas.findById_subasta(saux.getIdSubasta(), Sort.by("fecha").descending());
						UsuarioAux usuarioSubasta2;
						BidAux2 puja2;
						if (pujas2.isEmpty()) {
							puja2 = null;
						} else {
							Bid puja = pujas2.get(0);
							Usuario usuarioPuja = usuarios.buscarPorId(puja.getClave().getUsuario_id_usuario().toString());
							usuarioPuja.setPassword(null);					
							
							Location locUsuario2 = new Location(usuarioPuja.getPosX(), usuarioPuja.getPosY());
							UsuarioAux usuarioPujaAux = new UsuarioAux(usuarioPuja.getIdUsuario(), usuarioPuja.getGender(), 
									usuarioPuja.getBirth_date(), locUsuario2, usuarioPuja.getRating(), usuarioPuja.getStatus(), 
									null, usuarioPuja.getEmail(), usuarioPuja.getLast_name(), usuarioPuja.getFirst_name(), 
									usuarioPuja.getTipo(), new Picture(usuarioPuja.getIdImagen()));
							
							puja2 = new BidAux2(puja.getPuja(), usuarioPujaAux, puja.getFecha());
						}
						
						Usuario usuarioSubasta = usuarios.buscarPorId(saux.getId_owner().toString());
						Location locUsuario = new Location(usuarioSubasta.getPosX(), usuarioSubasta.getPosY());
						usuarioSubasta2 = new UsuarioAux(usuarioSubasta.getIdUsuario(), usuarioSubasta.getGender(), 
								usuarioSubasta.getBirth_date(), locUsuario, usuarioSubasta.getRating(), usuarioSubasta.getStatus(), 
								null, usuarioSubasta.getEmail(), usuarioSubasta.getLast_name(), usuarioSubasta.getFirst_name(), 
								usuarioSubasta.getTipo(), null);
							
						
						Location loc = new Location(saux.getPosX(),saux.getPosY());
						Usuario userFind = usuarios.buscarPorId(saux.getId_owner().toString());
						userFind = usuarios.buscarPorEmailCommon(userFind.getEmail());
						
						
						SubastaAux2 rAnuncio;	
						rAnuncio = new SubastaAux2(saux.getIdSubasta(), saux.getPublicate_date(), saux.getDescription(), saux.getTitle(), 
								loc, saux.getStartPrice(), saux.getFecha_finalizacion(), saux.getCategory(), 
								usuarioSubasta2, puja2,saux.getNfav(),saux.getNvis(),idList,in,subastas.selectDistance(lat, lng, id),saux.getCurrency(),saux.getStatus());	
						
						listWsId.add(rAnuncio);
					}
					return listWsId;
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
			
	}
	
	/**
	 * Devuelve la lista de subastas deseadas del usuario que envia la 
	 * peticion.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 404 si no se encuentra el usuario o 401
	 * si el token es incorrecto.
	 * @param lat Latidud maxima de la subasta deseada.
	 * @param lng Longitud maxima de la subasta deseada.
	 * @return Lista de subastas deseadas del usuario que envia la peticion.
	 * @throws IOException
	 */
	@GetMapping(path="/me/wishes_auctions")
	public @ResponseBody List<SubastaAux2> getMyDeseadosAuction(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (name = "lat") String lat,@RequestParam (name = "lng") String lng) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);	
		String user_id = u.getIdUsuario().toString();
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getIdUsuario().equals(u2.getIdUsuario())) {
					//Se devuelve la lista de deseados
					List<WishS> listWs = wishesS.buscarPorIdUsuario(Long.parseLong(user_id));
					List<SubastaAux2> listWsId = new ArrayList<SubastaAux2>();
					for (WishS wAux : listWs) {
						String id = wAux.getWishSId().getIdSubasta().toString();

						//Obtengo los id de las imagenes
						List<Media> idList = new ArrayList<Media>();
						
						List<BigInteger> idListBI = pictures.findIdImagesSub(id.toString());
						for(BigInteger idB : idListBI){
							Media med = new Media(idB.longValue());
							idList.add(med);
						}	
						
						Subasta saux = subastas.buscarPorId(id);
						
						//Compruebo si esta en la lista de deseados
						WishS wAux2 = wishesS.buscarInWishList(u.getIdUsuario().toString(),id);		
						boolean in = false;
						if(wAux2 != null) {
							in = true;
						}
						
						List<Bid> pujas2 =  pujas.findById_subasta(saux.getIdSubasta(), Sort.by("fecha").descending());
						UsuarioAux usuarioSubasta2;
						BidAux2 puja2;
						if (pujas2.isEmpty()) {
							usuarioSubasta2 = null;
							puja2 = null;
						} else {
							Bid puja = pujas2.get(0);
							Usuario usuarioPuja = usuarios.buscarPorId(puja.getClave().getUsuario_id_usuario().toString());
							usuarioPuja.setPassword(null);
							Usuario usuarioSubasta = usuarios.buscarPorId(saux.getId_owner().toString());
							Location locUsuario = new Location(usuarioSubasta.getPosX(), usuarioSubasta.getPosY());
							Picture picUsuario2;
							if (usuarioSubasta.getIdImagen() != null) {
								Optional<Picture> picUsuario;
								picUsuario = pictures.findById(usuarioSubasta.getIdImagen());
								if (picUsuario.isEmpty()) {
									picUsuario2 = null;
								} else {
									picUsuario2 = picUsuario.get();
								}
							} else {
								picUsuario2 = null;
							}
							usuarioSubasta2 = new UsuarioAux(usuarioSubasta.getIdUsuario(), usuarioSubasta.getGender(), 
									usuarioSubasta.getBirth_date(), locUsuario, usuarioSubasta.getRating(), usuarioSubasta.getStatus(), 
									null, usuarioSubasta.getEmail(), usuarioSubasta.getLast_name(), usuarioSubasta.getFirst_name(), 
									usuarioSubasta.getTipo(), picUsuario2);
							
							Location locUsuario2 = new Location(usuarioPuja.getPosX(), usuarioPuja.getPosY());
							UsuarioAux usuarioPujaAux = new UsuarioAux(usuarioPuja.getIdUsuario(), usuarioPuja.getGender(), 
									usuarioPuja.getBirth_date(), locUsuario2, usuarioPuja.getRating(), usuarioPuja.getStatus(), 
									null, usuarioPuja.getEmail(), usuarioPuja.getLast_name(), usuarioPuja.getFirst_name(), 
									usuarioPuja.getTipo(), new Picture(usuarioPuja.getIdImagen()));
							
							puja2 = new BidAux2(puja.getPuja(), usuarioPujaAux, puja.getFecha());
						}
							
						
						Location loc = new Location(saux.getPosX(),saux.getPosY());
						Usuario userFind = usuarios.buscarPorId(saux.getId_owner().toString());
						userFind = usuarios.buscarPorEmailCommon(userFind.getEmail());
						
						
						SubastaAux2 rAnuncio;	
						rAnuncio = new SubastaAux2(saux.getIdSubasta(), saux.getPublicate_date(), saux.getDescription(), saux.getTitle(), 
								loc, saux.getStartPrice(), saux.getFecha_finalizacion(), saux.getCategory(), 
								usuarioSubasta2, puja2,saux.getNfav(),saux.getNvis(),idList,in,subastas.selectDistance(lat, lng, id),saux.getCurrency(),saux.getStatus());	
						
						listWsId.add(rAnuncio);
					}
					return listWsId;
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
			
	}
	
	/**
	 * Elimina la subasta identificado con product_id de la lista de productos
	 * deseados del usuario identificado con user_id.
	 * @param user_id Identificador del usuario.
	 * @param product_id Identificador de la subsata.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 404 si el usuario identificado con
	 * user_id o subasta identificada con product_id no existe, 402 si el
	 * usuario que envia la peticion no coincide con el identificado con
	 * user_id o no es el administrador, o 401 si el token es incorrecto.
	 * @return "OK" si se ha podido eliminar correctamente o null en caso
	 * contrario.
	 * @throws IOException
	 */
	@DeleteMapping(path="/{user_id}/wishes_auctions/{product_id}")
	public @ResponseBody String deleteDeseadosAuctions(@PathVariable String user_id, @PathVariable String product_id, HttpServletRequest request , HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				if(u.getTipo().contentEquals("administrador") || u.getIdUsuario().equals(u2.getIdUsuario())) {
					WishS wish = wishesS.buscarInWishList(user_id,product_id);
					if(wish != null) {	
						//Si existe la subasta en la lista de deseados, se borra
						wishesS.deleteById(new WishSId(Long.parseLong(user_id),Long.parseLong(product_id)));			
					} 
					else {
						String error = "The product can´t be found.";
						response.sendError(404, error);
						return null;
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
	
	/**
	 * Devuelve la lista de valoraciones del usuario identificado con user_id.
	 * @param user_id Identificador del usuario.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 404 si no se encuentra al usuario o 401
	 * si el token es incorrecto.
	 * @return Lista de valoraciones del usuario identificado con user_id. 
	 * @throws IOException
	 */
	@GetMapping(path="/{user_id}/reviews")
	public @ResponseBody List<ValoracionAux2> getUserReviews(@PathVariable String user_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				List<Valoracion> vList = valoraciones.buscarPorIdAnunciante(u2.getIdUsuario());
				List<Valoracion> vList2 = valoraciones.buscarPorIdComprador(u2.getIdUsuario());
				vList.addAll(vList2);
				List<ValoracionAux2> vAux = new ArrayList<ValoracionAux2>();
				
				for(Valoracion v : vList) {			
					Location loc3 = null;
					Usuario buyer = null;
					UsuarioAux buyer2 = null;

					buyer = usuarios.buscarPorId(v.getId_comprador().toString());
					loc3 = new Location(buyer.getPosX(), buyer.getPosY());
					buyer2 = new UsuarioAux(buyer.getIdUsuario(),buyer.getGender(),buyer.getBirth_date(),
							loc3,buyer.getRating(),buyer.getStatus(),null,buyer.getEmail(),
							buyer.getLast_name(),buyer.getFirst_name(),buyer.getTipo(),new Picture(buyer.getIdImagen()));
					
					Location loc4 = null;
					Usuario buyer3 = null;
					UsuarioAux buyer4 = null;

					buyer3 = usuarios.buscarPorId(v.getId_anunciante().toString());
					loc4 = new Location(buyer3.getPosX(), buyer3.getPosY());
					buyer4 = new UsuarioAux(buyer3.getIdUsuario(),buyer3.getGender(),buyer3.getBirth_date(),
							loc4,buyer3.getRating(),buyer3.getStatus(),null,buyer3.getEmail(),
							buyer3.getLast_name(),buyer3.getFirst_name(),buyer3.getTipo(),new Picture(buyer3.getIdImagen()));
					
					


					ValoracionAux2 vAuxAdd = new ValoracionAux2(buyer2,buyer4,
							v.getValor(),v.getComentario(),v.getId_subasta(),v.getId_producto());
					vAux.add(vAuxAdd);
				}

				return vAux;
							
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
	}
	
	/**
	 * Anyade la valoracion valoracion al usuario identificado con user_id. 
	 * @param user_id Identificador del usuario.
	 * @param valoracion Valoracion a anyadir.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 412 si la valoracion es incorrecta, 404
	 * si no existe el usuario identificado con user_id o 401 si el token es
	 * incorrecto.
	 * @return "OK" si se ha podido anyadir con exito o null en caso contrario.
	 * @throws IOException
	 */
	@PostMapping(path="/{user_id}/reviews")
	public @ResponseBody String addUserReviews(@PathVariable String user_id, @RequestBody ValoracionAux valoracion,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			
			
			//Se comprueba si existe el usuario
			if((u2!=null && (u.getIdUsuario().equals(valoracion.getId_comprador()) || u.getIdUsuario().equals(valoracion.getId_anunciante()))
					&& !u2.getIdUsuario().equals(u.getIdUsuario())) || (u2!=null && u.getTipo().contentEquals("administrador"))) {

					Valoracion v = new Valoracion(valoracion.getId_comprador(),valoracion.getId_anunciante(),
							valoracion.getValor(),valoracion.getComentario(),valoracion.getId_subasta(),valoracion.getId_producto());
					
					List<Valoracion> vList = valoraciones.buscarPorIdAnunciante(Long.parseLong(user_id));
					List<Valoracion> vList2 = valoraciones.buscarPorIdComprador(Long.parseLong(user_id));
					vList.addAll(vList2);
					
					Boolean guardar = true;
					
					Anuncio a = null;
					Subasta s = null;
					
					if(valoracion.getId_producto() != null) {
						 a = anuncios.buscarPorId(valoracion.getId_producto().toString());
					}
					
					if(valoracion.getId_subasta() != null) {
						s = subastas.buscarPorId(valoracion.getId_subasta().toString());
					}
					
					if(a != null) {
						if(a.getId_owner().equals(u.getIdUsuario())) {
							v.setValorador("vendedor");
						}
						else {
							v.setValorador("comprador");
						}
					}
					else if(s != null) {
						if(s.getId_owner().equals(u.getIdUsuario())) {
							v.setValorador("vendedor");
						}
						else {
							v.setValorador("comprador");
						}
					}
					
					for(Valoracion vAux : vList) {
						if((v.getValorador().contentEquals("vendedor") && vAux.getId_comprador().equals(Long.valueOf(user_id)) 
								&& vAux.getId_anunciante().equals(u.getIdUsuario())) || (v.getValorador().contentEquals("comprador") 
								&& vAux.getId_comprador().equals(u.getIdUsuario()) && vAux.getId_anunciante().equals(Long.valueOf(user_id)))) {
							if(vAux.getId_producto() != null) {						
								if(vAux.getId_producto().equals(valoracion.getId_producto())) {
									guardar = false;
									break;
								}

							}
							if(vAux.getId_subasta() != null) {
								if(vAux.getId_subasta().equals(valoracion.getId_subasta())) {
									guardar = false;
									break;
								}
							}
						}
					}
					
					if(guardar) {
						if(valoracion.getId_producto() != null) {
							 if(a!= null) {
								 if(a.getId_buyer() ==  null) {
									 guardar = false;
								 }
								 else if(a.getStatus().contentEquals("en venta") || (!a.getId_buyer().equals(u.getIdUsuario()) 
										 && (!a.getId_owner().equals(u.getIdUsuario()) && a.getId_buyer() != null))) {
									 guardar = false;
								 }
							 }				
						}

						if(valoracion.getId_subasta() != null) {
							s = subastas.buscarPorId(valoracion.getId_subasta().toString());
							if(s != null) {
								List<Bid> pujas2 =  pujas.findById_subasta(s.getIdSubasta(), Sort.by("fecha").descending());
								if (pujas2.isEmpty()) {									
									guardar = false;
								} else {
									Bid puja = pujas2.get(0);			
									 if(s.getStatus().contentEquals("en venta") || (!puja.getClave().getUsuario_id_usuario().equals(u2.getIdUsuario())
											 && !s.getId_owner().equals(u2.getIdUsuario()))) {
										 guardar = false;
									 }
								}
							}														 
						}
										
						if((a != null || s  != null) && guardar) {			
							int num = vList.size();

							float valoracionAux = valoracion.getValor();
							
							if(valoracionAux >= 0.0 && valoracionAux <= 5.0) {
								float valor = (valoracionAux + u2.getRating()*num)/(num + 1);
								
								valoraciones.save(v);	
								usuarios.updateRating(user_id, valor);
								return "OK";
							}
							else {
								String error = "The value must be between 0.0 and 5.0.";
								response.sendError(412, error);
								return null;
							}
							
						}
						else {
							if(a == null && s  == null) {
								String error = "The product doesn´t exist or is not a product of the user.";
								response.sendError(404, error);
							}
							else {
								String error = "The product has not been sold yet or it has not been sold to you.";
								response.sendError(412, error);
							}
							return null;
						}
						
					}
					else {
						String error = "The review for this product has already been done.";
						response.sendError(412, error);
						return null;
					}
					
			}
			else {
				String error = "The user can´t be found or you are not the user.";
				response.sendError(404, error);
				return null;
			}
		}
		else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}	
	}
	
	/**
	 * Devuelve el numero de calificaciones del usuario identificado con
	 * user_id.
	 * @param user_id Identificador del usuario.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 404 si no existe el usuario identificado
	 * con user_id o 401 si el token es incorrecto.
	 * @return Numero de calificaciones del usuario identificado con user_id si
	 * se ha logrado obtener el valor con exito o null en caso contrario.
	 * @throws IOException
	 */
	@GetMapping(path="/{user_id}/reviews_count")
	public @ResponseBody Integer getUserNumberOfReviews(@PathVariable String user_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		//Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u2!=null) {
				List<Valoracion> vList = valoraciones.buscarPorIdAnunciante(u2.getIdUsuario());
				return vList.size();
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
	}
	
	/**
	 * Envia un correo electronico al usuario que ha enviado la peticion con
	 * toda su informacion.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 401 si el token es correcto.
	 * @return "Correo enviado" si se ha enviado con exito o null en caso
	 * contrario.
	 * @throws IOException
	 */
	@GetMapping(path="/request")
	public @ResponseBody String correoUsuarioActual(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		// Se comprueba si el token es válido
		if (TokenCheck.checkAccess(token, u)) {
			Location loc = new Location(u.getPosX(), u.getPosY());
			
			UsuarioAux rUser = new UsuarioAux(u.getIdUsuario(),u.getGender(),u.getBirth_date(),
											loc,u.getRating(),u.getStatus(),null,u.getEmail(),
											u.getLast_name(),u.getFirst_name(),u.getTipo(),new Picture(u.getIdImagen()));
			
			ObjectMapper mapperObj = new ObjectMapper();
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("Spring-Mail.xml");
			MailMail mm = (MailMail) context.getBean("mailMail");
			List<Anuncio> anuns = anuncios.findByUsuarioIdUsuario(rUser.getIdUsuario());
			String as = "";
			List<Picture> imagenes = new LinkedList<Picture>();
			List<Picture> imagenesAux = new LinkedList<Picture>();
			for (Anuncio a: anuns) {
				imagenesAux = pictures.findByAnuncio(a.getId_producto());
				imagenes.addAll(imagenesAux);
				as += mapperObj.writeValueAsString(a);
			}
			List<Subasta> subs = subastas.findByUsuarioIdUsuario(rUser.getIdUsuario());
			String ss = "";
			for (Subasta s: subs) {
				imagenesAux = pictures.findBySubasta(s.getIdSubasta());
				imagenes.addAll(imagenesAux);
				ss += mapperObj.writeValueAsString(s);
			}
			
			List<Bid> bds = pujas.findByIdUsuario(rUser.getIdUsuario());
			String bs = "";
			for (Bid b: bds) {
				bs += mapperObj.writeValueAsString(b);
			}
			List<Valoracion> vls = valoraciones.buscarPorIdUsuario(rUser.getIdUsuario(), rUser.getIdUsuario());
			String vs = "";
			for (Valoracion v: vls) {
				vs += mapperObj.writeValueAsString(v);
			}
			
			List<WishA> wa = wishesA.buscarPorIdUsuario(rUser.getIdUsuario());
			String wa2 = "";
			for (WishA wa3: wa) {
				wa2 += mapperObj.writeValueAsString(wa3);
			}
			
			List<WishS> ws = wishesS.buscarPorIdUsuario(rUser.getIdUsuario());
			String ws2 = "";
			for (WishS ws3: ws) {
				ws2 += mapperObj.writeValueAsString(ws3);
			}
			String is = "";
			for (Picture i: imagenes) {
				is += mapperObj.writeValueAsString(i);
			}
			
			mm.sendMail2("privacy@selit.naval.cat",rUser.getEmail(),"Informacion personal",mapperObj.writeValueAsString(rUser),as,ss, bs, vs, wa2, ws2, is);
			((ClassPathXmlApplicationContext) context).close();
			
			return "Correo enviado";
			
		} 
		else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
	}
	
	/**
	 * Reestablece la contrasenya al usuario que tiene como correo electronico
	 * email y le envia al usuario su nueva contrasenya.
	 * @param email Correo electronico del usuario.
	 * @param response Respuesta http: 404 si no existe el correo electronico.
	 * @return "OK" si existe el correo electronico existe en la base de datos o
	 * null.
	 * @throws IOException
	 */
	@GetMapping(path="/forgot")
	public @ResponseBody String recuperarContrasenya(HttpServletResponse response, @RequestParam String email) throws IOException {
		Usuario u = usuarios.buscarPorEmail(email);
		if (u != null) {
			//Generar RANDOM
			String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 18) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }		
	        String saltStr = salt.toString();
			ApplicationContext context = 
		             new ClassPathXmlApplicationContext("Spring-Mail.xml");
			MailMail mm = (MailMail) context.getBean("mailMail");
			mm.sendMail("accounts@selit.naval.cat",email,"Recuperación de la cuenta","Nueva contrasenya: " + saltStr);
			((ClassPathXmlApplicationContext) context).close();
			usuarios.changePassword(bCryptPasswordEncoder.encode(saltStr), u.getIdUsuario().toString());
			return "OK";
		} else {
			response.sendError(404, "No hay una cuenta con el correo: "+email);
			return null;
		}
	}
	
}
