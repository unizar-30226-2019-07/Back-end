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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

@RestController   
@RequestMapping(path="/auctions"
		+ "") 
public class SubastaController {
	
	@Autowired
	public 
	UsuarioRepository usuarios;
	
	@Autowired
	private SubastaRepository subastas;	
	
	@Autowired public 
	PictureRepository pictures;
	
	@Autowired public 
	VerificacionRepository verificaciones;	
	
	@Autowired public
	BidRepository pujas;

	
	public static BCryptPasswordEncoder bCryptPasswordEncoder;

	public SubastaController(UsuarioRepository usuarios, BCryptPasswordEncoder bCryptPasswordEncoder) {
		UsuarioController.usuarios = usuarios;
		UsuarioController.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
				
				Subasta subasta = new Subasta(dtf.format(now).toString(),subastaAux.getDescription(),subastaAux.getTitle(), subastaAux.getEndDate(), subastaAux.getStartPrice(),u.getIdUsuario(),subastaAux.getCategory(),
								subastaAux.getLocation().getLat(),subastaAux.getLocation().getLng(),"en venta",subastaAux.getCurrency(),new Long(0),new Long(0)); 
				
				// Se guarda la subasta.
				subasta = subastas.save(subasta);
				if (subastaAux.getMedia() != null) {
					for (Picture p: subastaAux.getMedia() ) {
						pictures.save(p);
					}
				}
				
				// Se contesta a la peticion con un mensaje de exito.
				response.setStatus(201);
				return "Nueva subasta creada";
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
				if (u.getTipo().equals("administrador") || subasta2.getId_owner() == u.getIdUsuario()) {
					
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
	
	@GetMapping(path="/{auction_id}")
	public @ResponseBody SubastaAux2 obtenerSubasta(@PathVariable String auction_id, @RequestParam (name = "lat", required = false) String lat,
			@RequestParam (name = "lng", required = false) String lng, HttpServletRequest request, HttpServletResponse response) throws IOException {
	
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
			
			SubastaAux2 rSubasta;
			BidAux2 puja2;
			List<Bid> pujas2 = pujas.findById_subasta(Long.parseLong(auction_id), Sort.by(Sort.Direction.DESC, "fecha"));
			Bid puja;
			if (!pujas2.isEmpty()) {
				puja = pujas2.get(0);
				Optional<Usuario> propietario = usuarios.findById(puja.getClave().getUsuario_id_usuario());
				Usuario propietario2 = propietario.get();
				puja2 = new BidAux2(puja.getPuja(), propietario2, puja.getFecha());
			} else {
				puja2 = null;
			}
			rSubasta = new SubastaAux2(saux.getIdSubasta(),saux.getPublicate_date(),saux.getDescription(),
					saux.getTitle(),loc,saux.getStartPrice(),saux.getFecha_finalizacion(),saux.getCategory(),
					rUser, puja2,saux.getNfav(),saux.getNvis(),idList);
			
			return rSubasta;
			
		}
		
	}
	
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
			@RequestParam (name = "?size", required = false) String size,
			@RequestParam (name = "?page", required = false) String page,
			@RequestParam (name = "?sort", required = false) String sort 
			){
		
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
					// TODO Auto-generated catch block
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
			
			List<BigInteger> idListBI = pictures.findIdImages(id.toString());
			for(BigInteger idB : idListBI){
				Media med = new Media(idB.longValue());
				idList.add(med);
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
				puja2 = new BidAux2(puja.getPuja(), usuarioPuja, puja.getFecha());
			}

			SubastaAux2 subastaDevolver;	
			subastaDevolver = new SubastaAux2(saux.getIdSubasta(), saux.getPublicate_date(), saux.getDescription(), 
					saux.getTitle(), loc2, saux.getStartPrice(), saux.getFecha_finalizacion(), saux.getCategory(), 
					usuarioSubasta2, puja2,saux.getNfav(),saux.getNvis(),idList);	
			
			ListaSubastasDevolver.add(subastaDevolver);
			
		}
		if ( ( size != null) && ( page != null ) ) {
			ListaSubastasDevolver = paginar(ListaSubastasDevolver, Integer.parseInt(page), Integer.parseInt(size));
		}
		return ListaSubastasDevolver;		
		
	}
	
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
					if (u.getTipo().equals("administrador") || subasta3.getId_owner() == u.getIdUsuario()) {
						
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
								pi.setIdProducto(Long.parseLong(auction_id));
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
								subasta.getEndDate(), subasta.getOwner_id(),subasta.getCategory(),auction_id);
						
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
			if (u.getIdUsuario() == puja.bidder_id) {
				
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
					if ( puja2 == null || puja2.getPuja() < puja.getAmount()) {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
						LocalDateTime now = LocalDateTime.now(); 
						puja3.setPuja(puja.getAmount());
						puja3.setFecha(dtf.format(now));
						pujas.save(puja3);
						response.setStatus(201);
						return "Guardada la puja correctamente";
					} else {
						response.sendError(409, "No se ha superado el precio actual de " + puja2.getPuja());
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
}
