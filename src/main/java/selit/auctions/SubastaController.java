package selit.auctions;

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
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioAux;
import selit.usuario.UsuarioController;
import selit.usuario.UsuarioRepository;
import selit.verificacion.VerificacionRepository;
import selit.wishes.WishS;
import selit.wishes.WishesSRepository;
import selit.Location.Location;
import selit.picture.Picture;
import selit.picture.PictureRepository;
import selit.security.TokenCheck;
import selit.bid.Bid;
import selit.bid.BidAux;
import selit.bid.BidAux2;
import selit.bid.BidRepository;
import selit.bid.ClavePrimaria;
import selit.media.Media;
import io.jsonwebtoken.Jwts;

/** 
 * Clase controladora de las operaciones relacionadas con subastas 
 */
@RestController   
@RequestMapping(path="/auctions"
		+ "") 
public class SubastaController {
	
	/** Repositorio de usuarios */
	@Autowired
	public 
	UsuarioRepository usuarios;
	
	/** Repositorio de subastas */
	@Autowired
	private SubastaRepository subastas;	
	
	/** Repositorio de imagenes */
	@Autowired public 
	PictureRepository pictures;
	
	/** Repositorio de verificaciones */
	@Autowired public 
	VerificacionRepository verificaciones;	
	
	/** Repositorio de pujas */
	@Autowired public
	BidRepository pujas;
	
	/** Repositorio de subastas deseadas */
	@Autowired public
	WishesSRepository wishesS;

	/** Cifrador de contrasenyas */
	public static BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Constructor.
	 * @param usuarios Repositorio de usuarios.
	 * @param bCryptPasswordEncoder Cifrador de constrasenyas.
	 */
	public SubastaController(UsuarioRepository usuarios, BCryptPasswordEncoder bCryptPasswordEncoder) {
		UsuarioController.usuarios = usuarios;
		UsuarioController.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	/**
	 * Devuelve la interseccion entre dos listas list1 y list2 de tipo T.
	 * @param <T> Tipo de la lista
	 * @param list1 Lista 1
	 * @param list2 Lista 2
	 * @return Interseccion entre las dos listas list1 y list2.
	 */
	private <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
	
	/**
	 * Devuelve el nombre del atributo contenido en parametro por el que 
	 * ordenador o null si no coincide con ninguno.
	 * @param parametro Nombre del parametro por el que ordenar.
	 * @return Nombre del atributo por el que ordenar o null si no coincide.
	 */
	private String elegirAtributo(String parametro) {
		if (parametro.equals("id")) {
			return "id_subasta";
		} else if (parametro.equals("title")) {
			return "titulo";
		} else if (parametro.equals("owner")) {
			return "usuario_id_usuario";
		} else if (parametro.equals("published")) {
			return "fecha_publicacion";
		} else if (parametro.equals("distance")) {
			return "distancia";
		} else if (parametro.equals("category")) {
			return "nombre_categoria";
		} else if (parametro.equals("status")) {
			return "estado";
		} else if (parametro.equals("price")) {
			return "precio_salida";
		} else if (parametro.equals("currency")) {
			return "moneda";
		} else {
			return null;
		}
	}
	
	/**
	 * Devuelve la pagina page de tamanyo size de la lista list1 de tipo T.
	 * @param <T> Tipo de la lista.
	 * @param list1 Lista que paginar.
	 * @param page Pagina de la lista.
	 * @param size Tamanyo de la pagina.
	 * @return Pagina page de tamanyo size de la lista list1 de tipo T.
	 */
	private <T> List<T> paginar(List<T> list1, Integer page, Integer size) {
        
		List<T> list = new ArrayList<T>();

		Integer i=1, pagina = 0;
		
        for (T t: list1) {
        	if (pagina == page) {
        		list.add(t);
        	}
        	if (i == size) {
        		pagina++;
        		i=1;
        	} else {
        		i++;
        	}
        }

        return list;
    }
	
	/**
	 * Anyade una nueva subasta a la base de datos.
	 * @param subastaAux Subasta a anyadir.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 201 si se a creado con exito, 402 si el
	 * usuario que envia la peticion no coincide con el identificado en la
	 * subasta subastaAux, 500 si no se han podido guardar las imagenes, 401 si
	 * el token es incorrecto o 412 si la fecha de creacion de la subasta es
	 * posterior a la fecha de finalizacion.
	 * @return "Nueva subastas creada" si se ha podido insertar con exito o null
	 * en caso contrario.
	 * @throws IOException
	 */
	@PostMapping(path="")
	public @ResponseBody String anyadirSubasta(@RequestBody SubastaAux subastaAux, HttpServletRequest request, HttpServletResponse response) throws IOException { 

		// Se obtiene el correo del usuario que ha anyadido la subasta para 
		// encontrar su identificador y meterlo en la tabla de subasta como
		// el creador.
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		//Se comrprueba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
			if(subastaAux.getOwner_id().equals(u.getIdUsuario())) {
			
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
				LocalDateTime now = LocalDateTime.now();  
				if (LocalDate.parse(subastaAux.getEndDate(), dtf).isAfter(now.toLocalDate())) {	
					Float lat = (float) Math.round(subastaAux.getLocation().getLat()*1000)/1000f;
					Float lng = (float) Math.round(subastaAux.getLocation().getLng()*1000)/1000f;
					Float startP = subastaAux.getStartPrice()*100/100f;
					Subasta subasta = new Subasta(dtf.format(now).toString(),subastaAux.getDescription(),subastaAux.getTitle(), subastaAux.getEndDate(), startP,u.getIdUsuario(),subastaAux.getCategory(),
							lat,lng,"en venta",subastaAux.getCurrency(),Long.valueOf(0),Long.valueOf(0)); 
					
					// Se guarda la subasta.
					subasta = subastas.save(subasta);
					
					List<Picture> lp = subastaAux.getMedia();
					Long idSubasta = subasta.getIdSubasta();
					
					
					for(Picture pic : lp){
						pic.setIdSubasta(idSubasta);
						try {
							pictures.save(pic);
						}
						catch(Exception e){
							subastas.deleteById(idSubasta);
							String error = "The image can´t be saved.";
							response.sendError(500, error);
							return null;
						}
						
					}
					
					// Se contesta a la peticion con un mensaje de exito.
					response.setStatus(201);
					return "Nueva subasta creada";
				} else {
					response.sendError(412, "La fecha de finalizacion es anterior a la de creacion ");
					return null;
				}
			}
			else {
				String error = "The user doesn't have enough permissions.";
				response.sendError(402, error);
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
	 * Elimina la subasta con identificador auction_id de la base de datos.
	 * @param auction_id Identificador de la subasta.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 404 si no existe la subasta identificado 
	 * con auction_id, 402 si no coincide el usuario que realiza la peticion con 
	 * el propietario de la subasta que se quiere eliminar o 401 si el token es 
	 * incorrecto.
	 * @return "Subasta eliminada" si se ha podido eliminar o null en caso 
	 * contrario.
	 * @throws IOException
	 */
	@DeleteMapping(path="/{auction_id}")
	public @ResponseBody String eliminarSubasta(@PathVariable String auction_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		// Se compreba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
	
			// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
			Optional<Subasta> subasta = subastas.findById(Long.parseLong(auction_id));
			if ( !subasta.isPresent() ) {
				
				// Se devuelve error 404.
				response.sendError(404, "La subasta con id "+auction_id+" no existe");
				
				return null;
				
			} else {
				
				// Se comprueba que el usuario que realiza la peticion de eliminar es un administrador
				// o es el propietario del producto.
				Subasta subasta2 = subasta.get();
				if (u.getTipo().equals("administrador") || subasta2.getId_owner().equals(u.getIdUsuario())) {
					
					List<BigInteger> listPic = pictures.findIdImagesSub(auction_id);
					for(BigInteger idP : listPic) {
						pictures.deleteById(idP.longValue());
					}
					// Se elimina el producto.
					subastas.deleteById(Long.parseLong(auction_id));
					
					// Se devuelve mensaje de confirmacion.
					return "Anuncio eliminado";
					
				} else {
					
					// No es el administrador o el propietario del producto, se devuelve un error.
					String error = "You are not an administrator or the user is not you.";
					response.sendError(402, error);
					return null;
				}
			}
			
		} else {
			
			// El token es incorrecto.
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
		
	}
	
	/**
	 * Devuelve la subasta con identificador auction_id
	 * @param auction_id Identificador de la subasta
	 * @param lat Latitud de la ubicacion del que envia la peticion.
	 * @param lng Longitud de la ubicacion del que envia la peticion.
	 * @param tokenBool True si se envia el token.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 401 si el token no es correcto o 404 si 
	 * no existe una subasta identificada con auction_id.
	 * @return Subasta con identificador auction_id.
	 * @throws IOException
	 */
	@GetMapping(path="/{auction_id}")
	public @ResponseBody SubastaAux2 obtenerSubasta(@PathVariable String auction_id, @RequestParam (name = "lat", required = false) String lat,
			@RequestParam (name = "lng", required = false) String lng,@RequestParam (name = "token", required = false) String tokenBool, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
		Optional<Subasta> subasta = subastas.findById(Long.parseLong(auction_id));
		if ( !subasta.isPresent() ) {
			
			// Se devuelve error 404.
			response.sendError(404, "La subasta con id "+auction_id+" no existe");
			
			return null;
			
		} else {
			Subasta saux = subasta.get();
			Location loc = new Location(saux.getPosX(),saux.getPosY());
			Usuario userFind = usuarios.buscarPorId(saux.getId_owner().toString());
			userFind = usuarios.buscarPorEmailCommon(userFind.getEmail());
			Location loc2 = new Location(userFind.getPosX(),userFind.getPosY());
			
			UsuarioAux rUser = new UsuarioAux(userFind.getIdUsuario(),userFind.getGender(),userFind.getBirth_date(),
					loc2,userFind.getRating(),userFind.getStatus(),null,userFind.getEmail(),
					userFind.getLast_name(),userFind.getFirst_name(),userFind.getTipo(),new Picture(userFind.getIdImagen()));
			
			//Obtengo los id de las imagenes
			List<Media> idList = new ArrayList<Media>();
			
			List<BigInteger> idListBI = pictures.findIdImagesSub(auction_id);
			for(BigInteger idB : idListBI){
				Media med = new Media(idB.longValue());
				idList.add(med);
			}	
			
			//Check del token
			boolean in = false;
			if(tokenBool != null) {
				if(tokenBool.equals("yes")) {
					String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
					String user = Jwts.parser()
							.setSigningKey(SUPER_SECRET_KEY)
							.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
							.getBody()
							.getSubject();
					
					Usuario u = new Usuario();
					u = usuarios.buscarPorEmail(user);

					// Se compreba si el token es valido.
					if(TokenCheck.checkAccess(token,u)) {
						//Compruebo si esta en la lista de deseados
						WishS wAux = wishesS.buscarInWishList(u.getIdUsuario().toString(),auction_id);		
						
						if(wAux != null) {
							in = true;
						}
					}
					else {
						
						// El token es incorrecto.
						String error = "The user credentials does not exist or are not correct.";
						response.sendError(401, error);
						return null;
					}
				}
			}			
			
			Long numVis = saux.getNvis();
			numVis++;
			saux.setNvis(numVis);
			subastas.actualizarNVis(Long.parseLong(auction_id),numVis);
			
			SubastaAux2 rSubasta;
			BidAux2 puja2;
			List<Bid> pujas2 = pujas.findById_subasta(Long.parseLong(auction_id), Sort.by(Sort.Direction.DESC, "fecha"));
			Bid puja;
			if (!pujas2.isEmpty()) {
				puja = pujas2.get(0);
				Optional<Usuario> propietario = usuarios.findById(puja.getClave().getUsuario_id_usuario());
				Usuario propietario2 = propietario.get();
				
				Location locUsuario2 = new Location(propietario2.getPosX(), propietario2.getPosY());
				UsuarioAux usuarioPujaAux = new UsuarioAux(propietario2.getIdUsuario(), propietario2.getGender(), 
						propietario2.getBirth_date(), locUsuario2, propietario2.getRating(), propietario2.getStatus(), 
						null, propietario2.getEmail(), propietario2.getLast_name(), propietario2.getFirst_name(), 
						propietario2.getTipo(), new Picture(propietario2.getIdImagen()));
				
				puja2 = new BidAux2(puja.getPuja(), usuarioPujaAux, puja.getFecha());
			} else {
				puja2 = null;
			}
			
			if(lat != null && lng != null) {
				rSubasta = new SubastaAux2(saux.getIdSubasta(),saux.getPublicate_date(),saux.getDescription(),
						saux.getTitle(),loc,saux.getStartPrice(),saux.getFecha_finalizacion(),saux.getCategory(),
						rUser, puja2,saux.getNfav(),saux.getNvis(),idList,in,subastas.selectDistance(lat, lng, auction_id),saux.getCurrency(),saux.getStatus());
			}
			else {
				rSubasta = new SubastaAux2(saux.getIdSubasta(),saux.getPublicate_date(),saux.getDescription(),
						saux.getTitle(),loc,saux.getStartPrice(),saux.getFecha_finalizacion(),saux.getCategory(),
						rUser, puja2,saux.getNfav(),saux.getNvis(),idList,in,saux.getCurrency(),saux.getStatus());
			}

			
			return rSubasta;
			
		}
		
	}
	
	/**
	 * Devuelve la lista de todas las subastas.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 412 si hay algun parametro incorrecto o
	 * 401 si el token enviado es incorrecto.
	 * @param lat Latitud de la ubicacion del usuario que envia la peticion.
	 * @param lng Longitud de la ubicacion del usuario que envia la peticion.
	 * @param distance Distancia maxima de las subastas.
	 * @param category Categoria del producto.
	 * @param search Termino contenido en el titulo de la subasta.
	 * @param priceFrom Precio minimo de la subasta.
	 * @param priceTo Precio maximo de la subasta.
	 * @param publishedFrom Fecha de publicacion minima de la subasta.
	 * @param publishedTo Fecha de publicacion maxima de la subasta.
	 * @param owner Propietario del producto.
	 * @param status Estado (vendido o en venta) de la subasta.
	 * @param size Tamanyo de la pagina.
	 * @param page Pagina de la lista de subastas.
	 * @param sort Forma de ordenar la lista de subastas.
	 * @param tokenBool True si se envia el token.
	 * @return Lista de todas las subastas.
	 * @throws IOException
	 */
	@GetMapping(path="")
	public @ResponseBody List<SubastaAux2> obtenerSubastas(HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestParam (name = "lat") String lat,
			@RequestParam (name = "lng") String lng,
			@RequestParam (name = "distance") String distance,
			@RequestParam (name = "category", required = false) String category,
			@RequestParam (name = "search", required = false) String search,
			@RequestParam (name = "priceFrom", required = false) String priceFrom,
			@RequestParam (name = "priceTo", required = false) String priceTo,
			@RequestParam (name = "publishedFrom", required = false) String publishedFrom,
			@RequestParam (name = "publishedTo", required = false) String publishedTo,
			@RequestParam (name = "owner", required = false) String owner,
			@RequestParam (name = "status", required = false) String status,
			@RequestParam (name = "$size", required = false) String size,
			@RequestParam (name = "$page", required = false) String page,
			@RequestParam (name = "$sort", required = false) String sort,
			@RequestParam (name = "token", required = false) String tokenBool		
			) throws IOException{
		
		List<Subasta> mySubastaListAux = new ArrayList<Subasta>();
		List<Long> mySubastaList = new ArrayList<Long>();
		List<Long> categories = new ArrayList<Long>();
		List<BigInteger> foundAux = new ArrayList<BigInteger>();
		List<Long> found = new ArrayList<Long>();
		List<Long> pFrom = new ArrayList<Long>();
		List<Long> pTo = new ArrayList<Long>();
		List<Long> pubFrom = new ArrayList<Long>();
		List<Long> pubTo = new ArrayList<Long>();
		List<Long> ownerL = new ArrayList<Long>();
		List<Long> statusL = new ArrayList<Long>();
		
		if ( sort != null) {
			String campos[] = sort.split("\\s");
			if ( (campos.length == 2) && ( (campos[1].equals("ASC")) || (campos[1].equals("DESC")) )  && ( elegirAtributo(campos[0]) != null) ) {
				campos[0] = elegirAtributo(campos[0]);
				if ( campos[1].equals("ASC")) {
					mySubastaListAux = subastas.selectSubastaCommonDistance(lat, lng, distance, Sort.by(campos[0]).ascending());
				} else {
					mySubastaListAux = subastas.selectSubastaCommonDistance(lat, lng, distance, Sort.by(campos[0]).descending());
				}
			} else {
				try {
					response.sendError(412, "Valores incorrectos en el parametro $sort");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			mySubastaListAux = subastas.selectSubastaCommonDistance(lat, lng, distance, null);
		}
		for(Subasta id : mySubastaListAux){
			mySubastaList.add(id.getIdSubasta().longValue());
		}
		if(category != null) {
			categories = subastas.selectSubastaCommonCategory(category);
			mySubastaList = intersection(mySubastaList,categories);
		}
		if(search != null) {
			foundAux = subastas.selectSubastaCommonSearch(search);
			for(BigInteger id : foundAux){
				found.add(id.longValue());
			}
			mySubastaList = intersection(mySubastaList,found);				
		}
		if(priceFrom != null) {
			 pFrom = subastas.selectSubastaCommonPriceFrom(Float.parseFloat(priceFrom));
			 mySubastaList = intersection(mySubastaList,pFrom);
		}
		if(priceTo != null) {
			pTo = subastas.selectSubastaCommonPriceTo(Float.parseFloat(priceTo));
			mySubastaList = intersection(mySubastaList,pTo);				 
		}
		if(publishedFrom != null) {
			pubFrom = subastas.selectSubastaCommonPublishedFrom(publishedFrom);
			mySubastaList = intersection(mySubastaList,pubFrom);
		}			
		if(publishedTo != null) {
			pubTo = subastas.selectSubastaCommonPublishedTo(publishedTo);
			mySubastaList = intersection(mySubastaList,pubTo);
		}
		if(owner != null) {
			ownerL = subastas.selectSubastaCommonOwner(Long.parseLong(owner));
			mySubastaList = intersection(mySubastaList,ownerL);
		}
		if(status != null) {
			statusL = subastas.selectSubastaCommonStatus(status);
			mySubastaList = intersection(mySubastaList,statusL);
		}
		List<SubastaAux2> ListaSubastasDevolver = new ArrayList<SubastaAux2>();
		for(Long id : mySubastaList) {
			Optional<Subasta> a = subastas.findSubastaCommon(id);
			Subasta saux;
			saux = a.get();

			Usuario userFind = usuarios.buscarPorId(saux.getId_owner().toString());
			userFind = usuarios.buscarPorEmailCommon(userFind.getEmail());
			Location loc2 = new Location(userFind.getPosX(),userFind.getPosY());
			
			//Obtengo los id de las imagenes
			List<Media> idList = new ArrayList<Media>();
			
			List<BigInteger> idListBI = pictures.findIdImagesSub(id.toString());
			for(BigInteger idB : idListBI){
				Media med = new Media(idB.longValue());
				idList.add(med);
			}	
			
			
			//Check del token
			boolean in = false;
			if(tokenBool != null) {
				if(tokenBool.equals("yes")) {
					String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
					String user = Jwts.parser()
							.setSigningKey(SUPER_SECRET_KEY)
							.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
							.getBody()
							.getSubject();
					
					Usuario u = new Usuario();
					u = usuarios.buscarPorEmail(user);

					// Se compreba si el token es valido.
					if(TokenCheck.checkAccess(token,u)) {
						//Compruebo si esta en la lista de deseados
						WishS wAux = wishesS.buscarInWishList(u.getIdUsuario().toString(),id.toString());		
						
						if(wAux != null) {
							in = true;
						}
					}
					else {
						
						// El token es incorrecto.
						String error = "The user credentials does not exist or are not correct.";
						response.sendError(401, error);
						return null;
					}
				}
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
					usuarioSubasta.getTipo(), new Picture(userFind.getIdImagen()));
			SubastaAux2 subastaDevolver;	
			subastaDevolver = new SubastaAux2(saux.getIdSubasta(), saux.getPublicate_date(), saux.getDescription(), 
					saux.getTitle(), loc2, saux.getStartPrice(), saux.getFecha_finalizacion(), saux.getCategory(), 
					usuarioSubasta2, puja2,saux.getNfav(),saux.getNvis(),idList,in,subastas.selectDistance(lat, lng, id.toString()),saux.getCurrency(),saux.getStatus());	
			
			ListaSubastasDevolver.add(subastaDevolver);
			
		}
		if ( ( size != null) && ( page != null ) ) {
			ListaSubastasDevolver = paginar(ListaSubastasDevolver, Integer.parseInt(page), Integer.parseInt(size));
		}
		return ListaSubastasDevolver;		
		
	}
	
	/**
	 * Actualiza la informacion de la subasta con identificador auction_id en la 
	 * base de datos.
	 * @param auction_id Identificador de la subasta.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param subasta Subasta actualizada.
	 * @param response Respuesta http: 500 si la imagen no se puede guardar, 
	 * 402 si el usuario que realiza la peticion no coincide con el propietario
	 * de la subasta que se quiere actualizar o no es el administrador, 404 si 
	 * no existe el anuncio identificado con auction_id, 401 si el token es 
	 * incorrecto o 409 si ya se ha vendido el producto.
	 * @return "Subasta actualizada" si se ha podido actualizar o null en caso 
	 * contrario.
	 * @throws IOException
	 */
	@PutMapping(path="/{auction_id}")
	public @ResponseBody String actualizarSubasta(@PathVariable String auction_id, HttpServletRequest request,@RequestBody SubastaAux subasta, HttpServletResponse response) throws IOException { 

		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		//Se comrprueba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
			
			// Se busca la subasta con el id pasado en la ruta, si no existe se devuelve un error.
			Optional<Subasta> subasta2 = subastas.findById(Long.parseLong(auction_id));
			if ( !subasta2.isPresent() ) {
				
				// Se devuelve error 404.
				response.sendError(404, "La subasta con id "+auction_id+" no existe");
				
				return null;
				
			} else {
				// Se comprueba que el usuario que realiza la peticion de actualizar es un administrador
				// o es el propietario del producto.
				Subasta subasta3 = subasta2.get();
				
				if(subasta3.getStatus().equals("en venta")) {
					if (u.getTipo().equals("administrador") || subasta3.getId_owner().equals(u.getIdUsuario())) {
						
						List<BigInteger> listIds = pictures.findIdImagesSub(auction_id);
						List<Long> auxIds = new ArrayList<Long>();
						List<Long> realIds = new ArrayList<Long>();
						
						for(BigInteger id: listIds) {
							auxIds.add(id.longValue());
						}
						
												
						List<Picture> picL = subasta.getMedia();
						for(Picture pi : picL) {
							Long idIm = pi.getIdImagen();
							
							if(idIm == null) {
								pi.setIdSubasta(Long.parseLong(auction_id));
								pictures.save(pi);
							}
							else {
								realIds.add(idIm);
							}
						}
						
						for(Long idAux : auxIds) {
							if(!realIds.contains(idAux)) {
								pictures.deleteById(idAux);
							}
						}
						
						// Se actualiza el producto.
						subastas.actualizarSubasta(subasta3.getPublicate_date(),subasta.getDescription(),subasta.getTitle(), 
								subasta.getLocation().getLat(),subasta.getLocation().getLng(),subasta.getStartPrice(),subasta.getCurrency(),
								subasta.getEndDate(), u.getIdUsuario(),subasta.getCategory(),auction_id);
						
						// Se devuelve mensaje de confirmacion.
						return "Subasta actualizada";
						
					} else {
						
						// No es el administrador o el propietario del producto, se devuelve un error.
						String error = "You can't update this product.";
						response.sendError(402, error);
						return null;
					}
				}
				else {
					String error = "You can´t update this product, it has already been sold.";
					response.sendError(409, error);
					return null;
				}
				
				
			}
			
		} else {
			
			// El token es incorrecto.
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
	}
	
	/**
	 * Anyade una nueva puja a la subasta identificada por auction_id.
	 * @param auction_id Identificador de la subasta.
	 * @param puja Nueva puja.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 409 si la puja es incorrecta, 404 si la
	 * subasta identificada con auction_id no existe, 402 si el usuario que
	 * envia la peticion no es el propietario de la subasta o 401 si el token
	 * es incorrecto, 412 si el precio es menor a 0.
	 * @return "Guardada la puja correctamente" si se ha guardado con exito o
	 * null si no se ha podido guardar correctamente.
	 * @throws IOException
	 */
	@PostMapping(path="/{auction_id}/bid")
	public @ResponseBody String anyadirPuja(@PathVariable Long auction_id, @RequestBody BidAux puja, HttpServletRequest request, HttpServletResponse response) throws IOException { 

		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		String user = Jwts.parser()
				.setSigningKey(SUPER_SECRET_KEY)
				.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail(user);
		
		//Se comrprueba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
			if (u.getIdUsuario().equals(puja.bidder_id)) {
				
				Optional<Subasta> subastaOp = subastas.findById(auction_id);
				if (subastaOp.isPresent()) {
					
					Subasta subasta = subastaOp.get();
					List<Bid> pujas2 = pujas.findById_subasta(subasta.getIdSubasta(), Sort.by(Sort.Direction.DESC, "fecha"));
					Bid puja2;
					if (pujas2.isEmpty() ) {
						puja2 = null;
					} else {
						puja2 = pujas2.get(0);
					}
					Bid puja3 = new Bid();
					if (puja2 == null) {
						puja3.setClave(new ClavePrimaria(puja.getBidder_id(), auction_id, subasta.getId_owner()));
						puja3.setPuja(puja.getAmount());
						puja2 = new Bid();
					} else {
						puja3.setClave(new ClavePrimaria(puja.getBidder_id(), puja2.getClave().getSubasta_id_producto(), puja2.getClave().getSubasta_id_usuario()));
					}
					if(puja.getAmount() >= subasta.getStartPrice()) {
						if(puja.getAmount() >= 0 && puja.getAmount() <= 1000000) {
							float pricePuja = Math.round(puja.getAmount()*100)/100f;
							if (puja2 == null || puja2.getPuja() < pricePuja) {
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
								LocalDateTime now = LocalDateTime.now(); 
								if (LocalDate.parse(subasta.getFecha_finalizacion(), dtf).isAfter(now.toLocalDate())) {									
									puja3.setPuja(pricePuja);
									puja3.setFecha(dtf.format(now));
									pujas.save(puja3);
									response.setStatus(201);
									return "Guardada la puja correctamente";
								} else {
									response.sendError(409, "La subasta termino " + subasta.getFecha_finalizacion());
									return null;
								}
							} else {
								response.sendError(409, "No se ha superado el precio actual de " + puja2.getPuja());
								return null;
							} 
						}
						else {
							String error = "The price should be 0 or higher or lesser than 1000000.";
							response.sendError(412, error);
							return null;
						}
					}
					else {
						String error = "Bid´s price should be higher than the start price.";
						response.sendError(412, error);
						return null;
					}
					
				} else {
					response.sendError(404, "No existe la subasta con id " + auction_id);
					return null;
				}
			} else {
				response.sendError(402, "No tienes permisos para realizar la puja");
				return null;
			}
					
		} else {
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
	}
	
	/**
	 * Finaliza la subasta identificado con auction_id.
	 * @param auction_id  Identificador de la subasta.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que ha enviado la peticion.
	 * @param response Respuesta http: 412 si no existe la subasta identficada
	 * con auction_id.
	 * @return Devuelve la ultima puja de la subasta identificada por 
	 * auction_id.
	 * @throws IOException
	 * @throws ParseException
	 */
	@PutMapping(path="/{auction_id}/sell")
	public @ResponseBody BidAux2 finSubasta(@PathVariable Long auction_id, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException { 
		Optional<Subasta> a = subastas.findSubastaCommon(auction_id);
		Subasta saux;
		if(a.isPresent()) {
			saux = a.get();
			
			Boolean devolver = false;
			
			if(!saux.getStatus().contentEquals("vendido")) {
				Calendar c = new GregorianCalendar();
				String dia = Integer.toString(c.get(Calendar.DATE));
				String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
				String annio = Integer.toString(c.get(Calendar.YEAR));
				String actualDate = annio + "-" + mes + "-" + dia;
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Date d1 = sdf.parse(actualDate);
				Date d2 = sdf.parse(saux.getFecha_finalizacion());
				
				if(d2.before(d1)) {
					subastas.actualizarStatus("vendido",saux.getIdSubasta());
					devolver = true;
				}
			}
			else {
				devolver = true;
			}
			
			if(devolver) {
				List<Bid> pujas2 =  pujas.findById_subasta(saux.getIdSubasta(), Sort.by("fecha").descending());
				BidAux2 puja2 = new BidAux2();
				if (!pujas2.isEmpty()) {
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
				
				return puja2;
			}
			else{
				return null;
			}
		}
		else {
			String error = "The auction doesn´t exist.";
			response.sendError(412, error);
			return null;
		}
	}
	
}
