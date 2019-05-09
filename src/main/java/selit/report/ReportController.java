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
import selit.security.TokenCheck;
import selit.usuario.Usuario;
import selit.usuario.UsuarioRepository;

@RestController   
@RequestMapping(path="/reports") 
public class ReportController {

	@Autowired
	public UsuarioRepository usuarios;	
	
	@Autowired public
	ReportRepository informes;
	
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
	
	@GetMapping(path="")
	public @ResponseBody List<Report> getReports(HttpServletRequest request, HttpServletResponse response,@RequestParam (name = "status", required = false) String status) throws IOException {
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
				List<Report> reportListAux = new ArrayList<Report> ();
				
				if(status != null) {
					for(Report r :  reportList) {
						if(r.getEstado_informe().contentEquals(status)) {
							reportListAux.add(r);
						}
					}
					return reportListAux;
				}
				else {
					return reportList;
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
			Usuario u2 = new Usuario();
			u2 = usuarios.buscarPorId(user_id);
			//Se comprueba si existe el usuario
			if(u.getTipo().contentEquals("administrador")) {
				Report r = informes.buscarPorId(report.getId_evaluado(),report.getId_informador());
				if(r != null) {
					ReportId rId = new ReportId(report.getId_evaluado(),report.getId_informador());
					r = new Report(rId,report.getDescripcion(),report.getFecha_realizacion(),report.getEstado_informe(),report.getAsunto());
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
}
