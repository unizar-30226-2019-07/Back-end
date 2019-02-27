package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Definicion de un dato de tipo Usuario (nombre, contrasenya, correo)
 */
@Entity
public class Usuario {
	
    @Id // Autogeneracion de IDs
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String nombre;
    
    private String contrasenya;

    private String correo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getContrasenya() {
		return contrasenya;
	}
	
	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
    
}

