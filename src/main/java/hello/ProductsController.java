package hello;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hello.Producto;
import hello.ProductoRepositorio;

@Controller    // Clase controladora
@RequestMapping(path="/products") // URL's empiezan con /products
public class ProductsController {
	
	@Autowired 
	private ProductoRepositorio productos;
	
	@PutMapping(path="/add") // Solo peticiones de tipo PUT
	public @ResponseBody String addNewProduct (@RequestParam String title, @RequestParam String price
			, @RequestParam Double posX, @RequestParam Double posY) {
		
		Producto p = new Producto();
		p.setTitle(title);
		p.setPrice(price);
		p.setPositionX(posX);
		p.setPositionY(posY);
		productos.save(p);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Producto> getAllProducto() {
		// Devuelve JSON con productos
		return productos.findAll();
	}
	
	@GetMapping(path="/10km")
	public @ResponseBody LinkedList<Producto> getProducto10Km(@RequestParam Double posX, @RequestParam Double posY) {
		// Devuelve JSON con productos que estan en un radio menos a 10KM de la posicion enviada
		Iterable<Producto> todosProds = productos.findAll();
		LinkedList<Producto> prods = new LinkedList<Producto>();
		for (Producto p : todosProds) {
			if ( Point2D.distanceSq(posX, posY, p.getPositionX(), p.getPositionY()) < 10.0 ) {
				prods.add(p);
			}
		}
		return prods;
	}
}
