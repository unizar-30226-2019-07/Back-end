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
import selit.usuario.UsuarioLoc;
import selit.usuario.UsuarioRepository;
import selit.Location.Location;
import selit.producto.AnuncioRepository;
import selit.security.TokenCheck;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(path="/products")
public class AnuncioController {

	@Autowired
	public
	AnuncioRepository anuncios;
	
	@Autowired
	public 
	UsuarioRepository usuarios;
	
	public AnuncioController(AnuncioRepository productos) {
		anuncios = productos;
	}
	
	private <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
	
	private String elegirAtributo(String parametro) {
		if (parametro.equals("id")) {
			return "id_producto";
		} else if (parametro.equals("type")) {
			return "???????";
		} else if (parametro.equals("title")) {
			return "titulo";
		} else if (parametro.equals("owner")) {
			return "usuario_id_usuario";
		} else if (parametro.equals("description")) {
			return "descripcion";
		} else if (parametro.equals("published")) {
			return "fecha_publicacion";
		} else if (parametro.equals("location")) {
			return "???????";
		} else if (parametro.equals("distance")) {
			return "??????";
		} else if (parametro.equals("category")) {
			return "nombre_categoria";
		} else if (parametro.equals("status")) {
			return "estado";
		} else if (parametro.equals("media")) {
			return "???????????";
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
								anuncio.getCurrency(),0,0,u.getIdUsuario(),anuncio.getCategory(),"en venta"); 
				// Se guarda el anuncio.
				anuncios.save(anun);
		
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

	@GetMapping(path="/{product_id}")
	public @ResponseBody AnuncioAux2 obtenerAnuncio(@PathVariable String product_id, @RequestParam (name = "lat", required = false) String lat,
			@RequestParam (name = "lng", required = false) String lng, HttpServletRequest request, HttpServletResponse response) throws IOException {			
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
				
				UsuarioLoc rUser = new UsuarioLoc(userFind.getIdUsuario(),userFind.getGender(),userFind.getBirth_date(),
						loc2,userFind.getRating(),userFind.getStatus(),userFind.getPassword(),userFind.getEmail(),
						userFind.getLast_name(),userFind.getFirst_name(),userFind.getTipo());
				
				AnuncioAux2 rAnuncio;	
				
				if(lat != null && lng != null) {
					rAnuncio = new AnuncioAux2(aaux.getId_producto(),aaux.getPublicate_date(),aaux.getDescription(),
							aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
							aaux.getNfav(),aaux.getNvis(),aaux.getCategory(),aaux.getStatus(),
							rUser,anuncios.selectDistance(lat, lng, product_id));
				}
				else {
					rAnuncio = new AnuncioAux2(aaux.getId_producto(),aaux.getPublicate_date(),aaux.getDescription(),
							aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
							aaux.getNfav(),aaux.getNvis(),aaux.getCategory(),aaux.getStatus(),
							rUser);
				}
	
				
				return rAnuncio;
			}					 
	}
	
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
						
						// Se actualiza el producto.
						anuncios.actualizarAnuncio(anuncio3.getPublicate_date(),anuncio.getDescription(),
								anuncio.getTitle(),anuncio.getLocation().getLat(),anuncio.getLocation().getLng(),
								anuncio.getPrice(),anuncio.getCurrency(),anuncio.getNfav(),
								anuncio.getNvis(),anuncio3.getId_owner(),anuncio.getCategory(),product_id,anuncio.getStatus());
						
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
	
	/* sort page y size ?? */
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
			@RequestParam (name = "sort", required = false) String sort,
			@RequestParam (name = "page", required = false) String page,
			@RequestParam (name = "size", required = false) String size
			) throws IOException {
		//Obtengo que usuario es el que realiza la petición
		
			List<BigInteger> myAnuncioListAux = new ArrayList<BigInteger>();
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
			for(BigInteger id : myAnuncioListAux){
				myAnuncioList.add(id.longValue());
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
				Location loc2 = new Location(userFind.getPosX(),userFind.getPosY());
				
				UsuarioLoc rUser = new UsuarioLoc(userFind.getIdUsuario(),userFind.getGender(),userFind.getBirth_date(),
						loc2,userFind.getRating(),userFind.getStatus(),userFind.getPassword(),userFind.getEmail(),
						userFind.getLast_name(),userFind.getFirst_name(),userFind.getTipo());
				
				AnuncioAux2 rAnuncio;	
				rAnuncio = new AnuncioAux2(aaux.getId_producto(),aaux.getPublicate_date(),aaux.getDescription(),
						aaux.getTitle(),loc,aaux.getPrice(),aaux.getCurrency(),
						aaux.getNfav(),aaux.getNvis(),aaux.getCategory(),aaux.getStatus(),
						rUser,anuncios.selectDistance(lat, lng, id.toString()));	
				
				rListAn.add(rAnuncio);
				
			}
			if ( ( size != null) && ( page != null ) ) {
				System.out.println("Hola");
				rListAn = paginar(rListAn, Integer.parseInt(page), Integer.parseInt(size));
			}
			return rListAn;		
	}	
}
