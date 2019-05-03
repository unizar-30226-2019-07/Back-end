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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioRepository;
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
import selit.auctions.SubastaRepository;
import selit.mail.MailMail;
import selit.picture.Picture;
import selit.picture.PictureRepository;
import selit.producto.Anuncio;
import selit.producto.AnuncioRepository;
import selit.security.TokenCheck;
import io.jsonwebtoken.Jwts;

@RestController   
@RequestMapping(path="/users") 
public class UsuarioController {
	
	@Autowired
	public static UsuarioRepository usuarios;	
	
	@Autowired public 
	AnuncioRepository anuncios;	
	
	@Autowired public 
	SubastaRepository subastas;	
	
	@Autowired public 
	VerificacionRepository verificaciones;	
	
	@Autowired public 
	PictureRepository pictures;	
	
	@Autowired public 
	WishesARepository wishesA;	
	
	@Autowired public 
	WishesSRepository wishesS;	
	
	public static BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsuarioController(UsuarioRepository usuarios, BCryptPasswordEncoder bCryptPasswordEncoder) {
		UsuarioController.usuarios = usuarios;
		UsuarioController.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
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
		mm.sendMail("selit@gmail.com",usuario.getEmail(),"","Para activar su cuenta acceda a la siguiente direccion: http://selit.naval.cat/verify?random=" + saltStr);
		((ClassPathXmlApplicationContext) context).close();
		
		// Se contesta a la peticion con un mensaje de exito.
		response.setStatus(201);
		return "Nuevo usuario creado";
	}

	
	/* sort page y size ?? */
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
						p = pictures.save(pic);
						
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
					//Se eliminar el usuario
					usuarios.deleteById(Long.parseLong(user_id));
					
					// Se elimina la imagen
					Long idIm = u.getIdImagen();					
					if(idIm != null) {
						pictures.deleteById(idIm);
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
	
	@GetMapping(path="/{user_id}/wishes_products")
	public @ResponseBody List<WishAId> getDeseadosProduct(@PathVariable String user_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
					List<WishAId> listWaId = new ArrayList<WishAId>();
					for (WishA wAux : listWa) {
						listWaId.add(new WishAId(wAux.getWishAId().getIdUsuario(),wAux.getWishAId().getIdProducto()));
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
					Optional<Anuncio> anun = anuncios.findById(Long.parseLong(product_id));
					if(anun.isPresent()) {		
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
	
	@GetMapping(path="/{user_id}/wishes_auctions")
	public @ResponseBody List<WishSId> getDeseadosAuction(@PathVariable String user_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
					List<WishS> listWa = wishesS.buscarPorIdUsuario(Long.parseLong(user_id));
					List<WishSId> listWaId = new ArrayList<WishSId>();
					for (WishS wAux : listWa) {
						listWaId.add(new WishSId(wAux.getWishSId().getIdUsuario(),wAux.getWishSId().getIdSubasta()));
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
					Optional<Subasta> anun = subastas.findById(Long.parseLong(product_id));
					if(anun.isPresent()) {		
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
	
}
