package main.java.hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Definicion de un dato de tipo Usuario (nombre, contrasenya, correo)
 */
@Entity
public class Media {
	
    @Id // Autogeneracion de IDs
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String type;
    
    private String mime;

    private String href;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
    
}

