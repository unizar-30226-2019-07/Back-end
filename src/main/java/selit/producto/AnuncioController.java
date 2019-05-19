package selit.producto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static selit.security.Constants.HEADER_AUTHORIZACION_KEY;
import static selit.security.Constants.SUPER_SECRET_KEY;
import static selit.security.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.Float;
import java.math.BigInteger;
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
import selit.usuario.UsuarioRepository;
import selit.wishes.WishA;
import selit.wishes.WishesARepository;
import selit.Location.Location;
import selit.media.Media;
import selit.picture.Picture;
import selit.picture.PictureRepository;
import selit.producto.AnuncioRepository;
import selit.security.TokenCheck;

import io.jsonwebtoken.Jwts;

/**
 * Controlador de las operaciones relacionadas con productos
 */
@RestController
@RequestMapping(path="/products")
public class AnuncioController {

	/** Repositorio de productos */
	@Autowired
	public
	AnuncioRepository anuncios;
	
	/** Repositorio de usuarios */
	@Autowired
	public 
	UsuarioRepository usuarios;

	/** Repositorio de imagenes */
	@Autowired public 
	PictureRepository pictures;	
	
	/** TODO: ??? */
	@Autowired public 
	WishesARepository wishesA;	
	
	/**
	 * Constructor del controlador.
	 * @param productos Repositorio de productos.
	 */
	public AnuncioController(AnuncioRepository productos) {
		anuncios = productos;
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
			return "id_producto";
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
			return "precio";
		} else if (parametro.equals("currency")) {
			return "moneda";
		} else if (parametro.equals("views")) {
			return "nvisitas";
		} else if (parametro.equals("likes")) {
			return "nfavoritos";
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
	 * Anyade un nuevo producto a la base de datos.
	 * @param anuncio Anuncio a anyadir.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 201 si se a creado con exito, 402 si el
	 * usuario que envia la peticion no coincide con el identificado en el
	 * anuncio anuncio, 500 si no se han podido guardar las imagenes o 401 si
	 * el token es incorrecto.
	 * @return "Nuevo producto creado" si se ha podido insertar con exito o null
	 * en caso contrario.
	 * @throws IOException
	 */
	@PostMapping(path="")
	public @ResponseBody String anyadirAnuncio (@RequestBody AnuncioAux anuncio, HttpServletRequest request, HttpServletResponse response) throws IOException { 

		// Se obtiene el correo del usuario que ha anyadido el producto para 
		// encontrar su identificador y meterlo en la tabla de anuncios como
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
			if(anuncio.getOwner_id().equals(u.getIdUsuario())) {
				// Se definen los valores por defecto de las columnas obligatorias.
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
				LocalDateTime now = LocalDateTime.now();  

				Anuncio anun = new Anuncio(dtf.format(now).toString(),anuncio.getDescription(),anuncio.getTitle(),
								anuncio.getLocation().getLat(),anuncio.getLocation().getLng(),anuncio.getPrice(),
								anuncio.getCurrency(),Long.valueOf(0),Long.valueOf(0),u.getIdUsuario(),anuncio.getCategory(),"en venta"); 
				// Se guarda el anuncio.
				Anuncio an = anuncios.save(anun);
				
				List<Picture> lp = anuncio.getMedia();
				Long idProducto = an.getId_producto();
				
				
				for(Picture pic : lp){
					pic.setIdProducto(idProducto);
					try {
						pictures.save(pic);
					}
					catch(Exception e){
						anuncios.deleteById(idProducto);
						String error = "The image can´t be saved.";
						response.sendError(500, error);
						return null;
					}
					
				}
				
				// Se contesta a la peticion con un mensaje de exito.
				response.setStatus(201);
				return "Nuevo producto creado";
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
	 * Elimina el anuncio con identificador product_id de la base de datos.
	 * @param product_id Identificador del anuncio.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 404 si no existe el anuncio identificado 
	 * con product_id, 402 si no coincide el usuario que realiza la peticion con el
	 * propietario del anuncio que se quiere eliminar o 401 si el token es 
	 * incorrecto.
	 * @return "Anuncio eliminado" si se ha podido eliminar o null en caso 
	 * contrario.
	 * @throws IOException
	 */
	@DeleteMapping(path="/{product_id}")
	public @ResponseBody String eliminarAnuncio(@PathVariable String product_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
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
			Optional<Anuncio> anuncio = anuncios.findById(Long.parseLong(product_id));
			if ( !anuncio.isPresent() ) {
				
				// Se devuelve error 404.
				response.sendError(404, "El producto con id "+product_id+" no existe");
				
				return null;
				
			} else {
				
				// Se comprueba que el usuario que realiza la peticion de eliminar es un administrador
				// o es el propietario del producto.
				Anuncio anuncio2 = anuncio.get();
				if (u.getTipo().equals("administrador") || anuncio2.getId_owner() == u.getIdUsuario()) {
					List<BigInteger> listPic = pictures.findIdImages(product_id);
					for(BigInteger idP : listPic) {
						pictures.deleteById(idP.longValue());
					}
					// Se elimina el producto.
					anuncios.deleteById(Long.parseLong(product_id));
					
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
	 * Devuelve el anuncio con identificador product_id
	 * @param product_id Identificador del producto
	 * @param lat Latitud de la ubicacion del que envia la peticion.
	 * @param lng Longitud de la ubicacion del que envia la peticion.
	 * @param tokenBool True si se envia el token.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param response Respuesta http: 401 si el token no es correcto o 404 si 
	 * no existe un producto identificado con product_id.
	 * @return Anuncio con identificador product_id.
	 * @throws IOException
	 */
	@GetMapping(path="/{product_id}")
	public @ResponseBody AnuncioAux2 obtenerAnuncio(@PathVariable String product_id, @RequestParam (name = "lat", required = false) String lat,
			@RequestParam (name = "lng", required = false) String lng, @RequestParam (name = "token", required = false) String tokenBool,
			HttpServletRequest request, HttpServletResponse response) throws IOException {			
			// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
			Optional<Anuncio> anuncio = anuncios.findById(Long.parseLong(product_id));
			if ( !anuncio.isPresent() ) {			
				// Se devuelve error 404.
				response.sendError(404, "El producto con id "+product_id+" no existe");
				
				return null;
				
			}
			else {
				Anuncio aaux = anuncio.get();
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
				
			
				UsuarioAux rUser = new UsuarioAux(userFind.getIdUsuario(),userFind.getGender(),userFind.getBirth_date(),
						loc2,userFind.getRating(),userFind.getStatus(),null,userFind.getEmail(),
						userFind.getLast_name(),userFind.getFirst_name(),userFind.getTipo(),new Picture(userFind.getIdImagen()));
				
				AnuncioAux2 rAnuncio;	
				//Obtengo los id de las imagenes
				List<Media> idList = new ArrayList<Media>();
				
				List<BigInteger> idListBI = pictures.findIdImages(product_id);
				for(BigInteger idB : idListBI){
					Media med = new Media(idB.longValue());
					idList.add(med);
				}	
				
				boolean in = false;
				if(tokenBool!=null) {
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
							WishA wAux = wishesA.buscarInWishList(u.getIdUsuario().toString(),product_id);		
							
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
				
				Long numVis = aaux.getNvis();
				numVis++;
				aaux.setNvis(numVis);
				anuncios.actualizarNVis(Long.parseLong(product_id),numVis);
				
				
				if(lat != null && lng != null) {
					rAnuncio = new AnuncioAux2(aaux.getId_producto(),aaux.getPublicate_date(),aaux.getDescription(),
							aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
							aaux.getNfav(),aaux.getNvis(),aaux.getCategory(),aaux.getStatus(),
							rUser,anuncios.selectDistance(lat, lng, product_id),idList,in, buyer2);
				}
				else {
					rAnuncio = new AnuncioAux2(aaux.getId_producto(),aaux.getPublicate_date(),aaux.getDescription(),
							aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
							aaux.getNfav(),aaux.getNvis(),aaux.getCategory(),aaux.getStatus(),
							rUser,idList,in, buyer2);
				}
	
				
				return rAnuncio;
			}					 
	}
	
	/**
	 * Actualiza la informacion del anuncio con identificador product_id en la 
	 * base de datos.
	 * @param product_id Identificador del producto.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario.
	 * @param anuncio Anuncio actualizado.
	 * @param response Respuesta http: 500 si la imagen no se puede guardar, 
	 * 402 si el usuario que realiza la peticion no coincide con el propietario
	 * del anuncio que se quiere actualizar o no es el administrador, 404 si no 
	 * existe el anuncio identificado con user_id, 401 si el token es 
	 * incorrecto o 409 si ya se ha vendido el producto.
	 * @return "Anuncio actualizado" si se ha podido actualizar o null en caso 
	 * contrario.
	 * @throws IOException
	 */
	@PutMapping(path="/{product_id}")
	public @ResponseBody String actualizarAnuncio(@PathVariable String product_id, 
						HttpServletRequest request, @RequestBody AnuncioAux anuncio, 
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
		
		// Se compreba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
			// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
			Optional<Anuncio> anuncio2 = anuncios.findById(Long.parseLong(product_id));
			if ( !anuncio2.isPresent() ) {
				
				// Se devuelve error 404.
				response.sendError(404, "El producto con id "+product_id+" no existe");
				
				return null;
				
			} else {
				// Se comprueba que el usuario que realiza la peticion de actualizar es un administrador
				// o es el propietario del producto.
				Anuncio anuncio3 = anuncio2.get();
				
				if(anuncio3.getStatus().equals("en venta")) {
					if (u.getTipo().equals("administrador") || anuncio3.getId_owner() == u.getIdUsuario()) {
						List<BigInteger> listIds = pictures.findIdImages(product_id);
						List<Long> auxIds = new ArrayList<Long>();
						List<Long> realIds = new ArrayList<Long>();
						
						for(BigInteger id: listIds) {
							auxIds.add(id.longValue());
						}
						
												
						List<Picture> picL = anuncio.getMedia();
						for(Picture pi : picL) {
							Long idIm = pi.getIdImagen();
							
							if(idIm == null) {
								pi.setIdProducto(Long.parseLong(product_id));
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
						anuncios.actualizarAnuncio(anuncio3.getPublicate_date(),anuncio.getDescription(),
								anuncio.getTitle(),anuncio.getLocation().getLat(),anuncio.getLocation().getLng(),
								anuncio.getPrice(),anuncio.getCurrency(),
								anuncio3.getId_owner(),anuncio.getCategory(),product_id,anuncio.getStatus());
						
						// Se devuelve mensaje de confirmacion.
						return "Anuncio actualizado";
						
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
	 * Devuelve la lista de todos los anuncios.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param response Respuesta http: 412 si hay algun parametro incorrecto o
	 * 401 si el token enviado es incorrecto.
	 * @param lat Latitud de la ubicacion del usuario que envia la peticion.
	 * @param lng Longitud de la ubicacion del usuario que envia la peticion.
	 * @param distance Distancia maxima de los productos.
	 * @param category Categoria de los productos.
	 * @param search Termino contenido en el titulo de un anuncio.
	 * @param priceFrom Precio minimo de los productos.
	 * @param priceTo Precio maximo de los productos.
	 * @param publishedFrom Fecha de publicacion minima de los productos.
	 * @param publishedTo Fecha de publicacion maxima de los productos.
	 * @param owner Propietario de los productos.
	 * @param status Estado de los productos.
	 * @param sort Forma de ordenar la lista de productos.
	 * @param page Pagina de la lista de productos.
	 * @param size Tamanyo de la pagina.
	 * @param tokenBool True si se envia el token.
	 * @return Lista de todos los anuncios.
	 * @throws IOException
	 */
	@GetMapping(path="")
	public @ResponseBody List<AnuncioAux2> obtenerAnuncios(HttpServletRequest request, 
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
			@RequestParam (name = "$sort", required = false) String sort,
			@RequestParam (name = "$page", required = false) String page,
			@RequestParam (name = "$size", required = false) String size,
			@RequestParam (name = "token", required = false) String tokenBool
			) throws IOException {
		//Obtengo que usuario es el que realiza la petición
		
			List<Anuncio> myAnuncioListAux = new ArrayList<Anuncio>();
			List<Long> myAnuncioList = new ArrayList<Long>();
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
						myAnuncioListAux = anuncios.selectAnuncioCommonDistance(lat, lng, distance, Sort.by(campos[0]).ascending());
					} else {
						myAnuncioListAux = anuncios.selectAnuncioCommonDistance(lat, lng, distance, Sort.by(campos[0]).descending());
					}
				} else {
					response.sendError(412, "Valores incorrectos en el parametro $sort");
				}
			} else {
				myAnuncioListAux = anuncios.selectAnuncioCommonDistance(lat, lng, distance, null);
			}
			for(Anuncio id : myAnuncioListAux){
				myAnuncioList.add(id.getId_producto().longValue());
			}
			if(category != null) {
				categories = anuncios.selectAnuncioCommonCategory(category);
				myAnuncioList = intersection(myAnuncioList,categories);
			}
			if(search != null) {
				foundAux = anuncios.selectAnuncioCommonSearch(search);
				for(BigInteger id : foundAux){
					found.add(id.longValue());
				}
				myAnuncioList = intersection(myAnuncioList,found);				
			}
			if(priceFrom != null) {
				 pFrom = anuncios.selectAnuncioCommonPriceFrom(Float.parseFloat(priceFrom));
				 myAnuncioList = intersection(myAnuncioList,pFrom);
			}
			if(priceTo != null) {
				pTo = anuncios.selectAnuncioCommonPriceTo(Float.parseFloat(priceTo));
				myAnuncioList = intersection(myAnuncioList,pTo);				 
			}
			if(publishedFrom != null) {
				pubFrom = anuncios.selectAnuncioCommonPublishedFrom(publishedFrom);
				myAnuncioList = intersection(myAnuncioList,pubFrom);
			}			
			if(publishedTo != null) {
				pubTo = anuncios.selectAnuncioCommonPublishedTo(publishedTo);
				myAnuncioList = intersection(myAnuncioList,pubTo);
			}
			if(owner != null) {
				ownerL = anuncios.selectAnuncioCommonOwner(Long.parseLong(owner));
				myAnuncioList = intersection(myAnuncioList,ownerL);
			}
			if(status != null) {
				statusL = anuncios.selectAnuncioCommonStatus(status);
				myAnuncioList = intersection(myAnuncioList,statusL);
			}
			List<AnuncioAux2> rListAn = new ArrayList<AnuncioAux2>();
			for(Long id : myAnuncioList) {
				Optional<Anuncio> a = anuncios.findAnuncioCommon(id.toString());
				Anuncio aaux = a.get();
				
				Location loc = new Location(aaux.getPosX(),aaux.getPosY());
				Usuario userFind = usuarios.buscarPorId(aaux.getId_owner().toString());
				userFind = usuarios.buscarPorEmailCommon(userFind.getEmail());
				Usuario buyer = null;
				Location loc3 = null;
				if (aaux.getId_buyer() != null) {
					buyer = usuarios.buscarPorId(aaux.getId_buyer().toString());
					loc3 = new Location(buyer.getPosX(), buyer.getPosY());
				}
				Location loc2 = new Location(userFind.getPosX(),userFind.getPosY());
				
				//Obtengo los id de las imagenes
				List<Media> idList = new ArrayList<Media>();
				
				List<BigInteger> idListBI = pictures.findIdImages(id.toString());
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
							WishA wAux = wishesA.buscarInWishList(u.getIdUsuario().toString(),id.toString());		
							
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
				
				
				//Creo el usuario a devolver
				UsuarioAux rUser = new UsuarioAux(userFind.getIdUsuario(),userFind.getGender(),userFind.getBirth_date(),
						loc2,userFind.getRating(),userFind.getStatus(),null,userFind.getEmail(),
						userFind.getLast_name(),userFind.getFirst_name(),userFind.getTipo(),new Picture(userFind.getIdImagen()));
				UsuarioAux buyer2;
				if (buyer == null) {
					buyer2 = null;
				} else {
					buyer2 = new UsuarioAux(buyer.getIdUsuario(), buyer.getGender(), buyer.getBirth_date(),
							loc3, buyer.getRating(), buyer.getStatus(), null, buyer.getEmail(),
							buyer.getLast_name(), buyer.getFirst_name(), buyer.getTipo(), new Picture(buyer.getIdImagen()));
				}
				AnuncioAux2 rAnuncio;	
				
				rAnuncio = new AnuncioAux2(aaux.getId_producto(),aaux.getPublicate_date(),aaux.getDescription(),
						aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
						aaux.getNfav(),aaux.getNvis(),aaux.getCategory(),aaux.getStatus(),
						rUser,anuncios.selectDistance(lat, lng, id.toString()),idList,in, buyer2);	
				
				rListAn.add(rAnuncio);
				
			}
			if ( ( size != null) && ( page != null ) ) {
				rListAn = paginar(rListAn, Integer.parseInt(page), Integer.parseInt(size));
			}
			return rListAn;		
	}
	
	/**
	 * Actualiza el estado del producto identificado con product_id a vendido y
	 * actualiza el comprador del mismo producto.
	 * @param product_id Identificador del producto.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que envia la peticion.
	 * @param anuncio Anuncio que contiene el identificador del comprador del
	 * producto.
	 * @param response Respuesta http: 404 si el producto identificado con
	 * product_id no existe, 402 si el usuario que envia la peticion no coincide
	 * con el propietario del producto que se quiere actualizar o 409 si ya se
	 * ha vendido el producto.
	 * @return "Anuncio actualizado" si se ha actualizado con exito o null en
	 * caso contrario.
	 * @throws IOException
	 */
	@PutMapping(path="/{product_id}/sell")
	public @ResponseBody String actualizarVendido(@PathVariable String product_id, 
						HttpServletRequest request, @RequestBody AnuncioAux anuncio, 
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
		
		// Se compreba si el token es valido.
		if(TokenCheck.checkAccess(token,u)) {
			// Se busca el producto con el id pasado en la ruta, si no existe se devuelve un error.
			Optional<Anuncio> anuncio2 = anuncios.findById(Long.parseLong(product_id));
			if ( !anuncio2.isPresent() ) {
								
				// Se devuelve error 404.
				response.sendError(404, "El producto con id "+product_id+" no existe");
				
				return null;
				
			} else {
				
				// Se comrpueba que existe el usuario de buyer_id.
				Optional<Usuario> usuario_comprador = usuarios.findById(anuncio.getBuyer_id());
				
				// Se comprueba que el usuario que realiza la peticion de actualizar es un administrador
				// o es el propietario del producto.
				Anuncio anuncio3 = anuncio2.get();
				if (usuario_comprador.isEmpty()) {
					response.sendError(404, "El comprador con id "+anuncio.getBuyer_id()+" no existe");
					return null;
				} else {
					if(anuncio3.getStatus().equals("en venta")) {
						if (u.getTipo().equals("administrador") || anuncio3.getId_owner() == u.getIdUsuario()) {													
							// Se actualiza el producto.
							anuncios.actualizarVendido(anuncio.getBuyer_id(),Long.parseLong(product_id));
							
							// Se devuelve mensaje de confirmacion.
							return "Anuncio actualizado";
							
						} else {
							
							// No es el administrador o el propietario del producto, se devuelve un error.
							String error = "You can't update this product.";
							response.sendError(402, error);
							return null;
						}
					} else {
					String error = "You can´t update this product, it has already been sold.";
					response.sendError(409, error);
					return null;
					}
				}
				
			}
			
		} else {
			
			// El token es incorrecto.
			String error = "The user credentials does not exist or are not correct.";
			response.sendError(401, error);
			return null;
		}
	}	
	
}
