package selit.report;

import static selit.security.Constants.HEADER_AUTHORIZACION_KEY;
import static selit.security.Constants.SUPER_SECRET_KEY;
import static selit.security.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import selit.Location.Location;
import selit.picture.Picture;
import selit.security.TokenCheck;
import selit.usuario.Usuario;
import selit.usuario.UsuarioAux;
import selit.usuario.UsuarioRepository;

/**
 * Controlador de las operaciones relacionadas con informes
 */
@RestController   
@RequestMapping(path="/reports") 
public class ReportController {

	/** Repositorio de usuarios */
	@Autowired
	public UsuarioRepository usuarios;	
	
	/** Repositorio de informes */
	@Autowired public
	ReportRepository informes;
	
	/**
	 * Anyade un nuevo informe a la base de datos.
	 * @param user_id Identificador del usuario al que se realiza el informe.
	 * @param report Informe a anyadir.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que ha enviado la peticion.
	 * @param response Respuesta http: 412 si ya se ha realizado el informe para
	 * el producto correspondiente, 404 si no existe el usuario o el usuario que
	 * ha enviado la peticion es el evaluado o 401 si el token es incorrecto.
	 * @return "OK" si se ha realizado correctamente o null en caso contrario.
	 * @throws IOException
	 */
	@PostMapping(path="/{user_id}/report")
	public @ResponseBody String addUserReport(@PathVariable String user_id, @RequestBody ReportAux report,HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			if((u2!=null && !u2.getIdUsuario().equals(u.getIdUsuario())) || (u2!=null && u.getTipo().contentEquals("administrador"))) {
				Report r = informes.buscarPorId(report.getId_evaluado(),report.getId_informador());
				if(r == null) {
					ReportId rId = new ReportId(report.getId_evaluado(),report.getId_informador());
					r = new Report(rId,report.getDescripcion(),report.getFecha_realizacion(),"pendiente",report.getAsunto());
					informes.save(r);
				}
				else {
					String error = "The report for this product has already been done.";
					response.sendError(412, error);
					return null;
				}
			}
			else {
				String error = "The user can´t be found or you are the user.";
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
	 * Devuelve una lista con todos los informes.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que ha enviado la peticion.
	 * @param response Resputa http: 404 si el usuario que realiza la peticion
	 * no es un administrador o 401 si el token es incorrecto.
	 * @param status Especifica el estado (pendiente o resuelto) de los informes
	 * a resolver.
	 * @return Lista con todos los informes.
	 * @throws IOException
	 */
	@GetMapping(path="")
	public @ResponseBody List<ReportAux2> getReports(HttpServletRequest request, HttpServletResponse response,@RequestParam (name = "status", required = false) String status) throws IOException {
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
			//Se comprueba que sea administrador
			if(u.getTipo().contentEquals("administrador")) {
				List<Report> reportList = informes.findAll();
				List<ReportAux2> reportListAux = new ArrayList<ReportAux2> ();
				List<ReportAux2> reportListAux2 = new ArrayList<ReportAux2> ();
				
				if(status != null) {
					for(Report r :  reportList) {
						if(r.getEstado_informe().contentEquals(status)) {
							Usuario u1 = usuarios.buscarPorId(r.getReportId().getId_evaluado().toString());
							Usuario u2 = usuarios.buscarPorId(r.getReportId().getId_informador().toString());
							
							
							Location loc = new Location(u1.getPosX(),u1.getPosY());				
							UsuarioAux ua1 = new UsuarioAux(u1.getIdUsuario(),u1.getGender(),u1.getBirth_date(),
									loc,u1.getRating(),u1.getStatus(),null,u1.getEmail(),
									u1.getLast_name(),u1.getFirst_name(),u1.getTipo(),new Picture(u1.getIdImagen()));
							
							Location loc2 = new Location(u2.getPosX(),u2.getPosY());				
							UsuarioAux ua2 = new UsuarioAux(u2.getIdUsuario(),u2.getGender(),u2.getBirth_date(),
									loc2,u2.getRating(),u2.getStatus(),null,u2.getEmail(),
									u2.getLast_name(),u2.getFirst_name(),u2.getTipo(),new Picture(u2.getIdImagen()));
							
							ReportAux2 r2 = new ReportAux2(ua1,ua2,r.getDescripcion(),r.getFecha_realizacion(),r.getEstado_informe(),r.getAsunto());
							reportListAux.add(r2);
						}
					}
					return reportListAux;
				}
				else {
					for(Report r :  reportList) {
						Usuario u1 = usuarios.buscarPorId(r.getReportId().getId_evaluado().toString());
						Usuario u2 = usuarios.buscarPorId(r.getReportId().getId_informador().toString());
						
						
						Location loc = new Location(u1.getPosX(),u1.getPosY());				
						UsuarioAux ua1 = new UsuarioAux(u1.getIdUsuario(),u1.getGender(),u1.getBirth_date(),
								loc,u1.getRating(),u1.getStatus(),null,u1.getEmail(),
								u1.getLast_name(),u1.getFirst_name(),u1.getTipo(),new Picture(u1.getIdImagen()));
						
						Location loc2 = new Location(u2.getPosX(),u2.getPosY());				
						UsuarioAux ua2 = new UsuarioAux(u2.getIdUsuario(),u2.getGender(),u2.getBirth_date(),
								loc2,u2.getRating(),u2.getStatus(),null,u2.getEmail(),
								u2.getLast_name(),u2.getFirst_name(),u2.getTipo(),new Picture(u2.getIdImagen()));
						
						ReportAux2 r2 = new ReportAux2(ua1,ua2,r.getDescripcion(),r.getFecha_realizacion(),r.getEstado_informe(),r.getAsunto());
						reportListAux2.add(r2);				
					}
					
					return reportListAux2;
				}
				
			}
			else {
				String error = "You aren´t an administrator.";
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
	 * Actualiza el informe del usuario identificado con user_id.
	 * @param user_id Identificador del usuario cuyo informe se quiere
	 * actualizar.
	 * @param report Informe actualizado.
	 * @param request Peticion http: contiene el token con el correo electronico
	 * del usuario que ha enviado la peticion.
	 * @param response Respuesta http: 412 si no se ha realizado el informe,
	 * 404 si el usuario que realiza la peticion no es administrador o 401 si el
	 * token es incorrecto.
	 * @return "OK" si se ha podido actualizar correctamente o null en caso
	 * contrario.
	 * @throws IOException
	 */
	@PutMapping(path="/{user_id}/report")
	public @ResponseBody String actualizarUserReport(@PathVariable String user_id, @RequestBody ReportAux report,HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			//Se comprueba si existe el usuario
			if(u.getTipo().contentEquals("administrador")) {
				Report r = informes.buscarPorId(report.getId_evaluado(),report.getId_informador());
				if(r != null) {
					ReportId rId = new ReportId(report.getId_evaluado(),report.getId_informador());
					r = new Report(rId,report.getDescripcion(),report.getFecha_realizacion(),report.getEstado_informe(),report.getAsunto());
					informes.save(r);
				}
				else {
					String error = "The report for this product has not been done.";
					response.sendError(412, error);
					return null;
				}
			}
			else {
				String error = "The user can´t be found or you are the user.";
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
