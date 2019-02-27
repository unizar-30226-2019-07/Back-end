package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hello.Media;
import hello.MediaRepositorio;

@Controller    // Clase controladora
@RequestMapping(path="/media") // URL's empiezan con /media
public class MediaController {
	
	@Autowired 
	private MediaRepositorio medias;
	
	@PutMapping(path="/add") // Solo peticiones de tipo PUT
	public @ResponseBody String addNewMedia (@RequestParam String type, @RequestParam String mime
			, @RequestParam String href) {
		
		Media m = new Media();
		m.setType(type);
		m.setMime(mime);
		m.setHref(href);
		medias.save(m);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Media> getAllProducto() {
		// Devuelve JSON con ficheros multimedia
		return medias.findAll();
	}
	
}
