package selit.producto;

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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import selit.usuario.Usuario;
import selit.usuario.UsuarioRepository;
import selit.producto.ProductoRepository;
import selit.security.TokenCheck;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(path="/products")
public class ProductoController {

	@Autowired
	public
	static ProductoRepository productos;
	public 
	static UsuarioRepository usuarios;	
	
	public ProductoController(ProductoRepository productos) {
		ProductoController.productos = productos;
	}

	@PostMapping(path="")
	public @ResponseBody String anyadirProducto (@RequestBody Producto producto, HttpServletResponse response) {

		// El objeto usuario pasado en el cuerpo de la peticion tiene los
		// atributos email, password y first_name. El resto de los atributos no
		// nulos se deben rellenar.


		producto.setPosX((float) 0.0);
		producto.setPosY((float) 0.0);
		producto.setId_owner((long) 5);
		Usuario u = new Usuario();
		u = usuarios.buscarPorEmail("a.gmail.com");
		producto.setUsuario(u);
		
		// Se guarda al usuario.
		productos.save(producto);

		// Se contesta a la peticion con un mensaje de exito.
		response.setStatus(201);
		return "Nuevo producto creado";
	}

}
