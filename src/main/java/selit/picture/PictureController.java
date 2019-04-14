package selit.picture;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

 	
@RestController   
@RequestMapping(path="/pictures") 
public class PictureController {
	
	@Autowired public 
	PictureRepository pictures;	
	
	@GetMapping(path="/{picture_id}")
	public void obtenerImagen(@PathVariable String picture_id, @RequestHeader HttpHeaders headers,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Optional<Picture> p = pictures.findById(Long.parseLong(picture_id));
		if(p.isPresent()) {
			Picture pic = p.get();
			InputStream is = new ByteArrayInputStream(pic.getBase64());
			if(pic.getMime().equals("image/jpeg")) {
				response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			}
			else {
				response.setContentType(MediaType.IMAGE_PNG_VALUE);
			}
		    IOUtils.copy(is, response.getOutputStream());		
		}

	}
}
