package selit.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import selit.Location.Location;

public class UsuarioLoc {
	
    private Long idUsuario;
    
    private String gender;
    
    private String birth_date;
    
    private Location location;
    
    private float rating;
    
    private String status;
    
    private String password;
    
    private String email;
  
    private String last_name;
    
    private String first_name;
    
    private String tipo;
    
    public UsuarioLoc() {
    	
    }
    
	public UsuarioLoc(Long idUsuario, String gender, String birth_date, Location location, float rating, String status,
			String password, String email, String last_name, String first_name, String tipo) {
		super();
		this.idUsuario = idUsuario;
		this.gender = gender;
		this.birth_date = birth_date;
		this.location = location;
		this.rating = rating;
		this.status = status;
		this.password = password;
		this.email = email;
		this.last_name = last_name;
		this.first_name = first_name;
		this.tipo = tipo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public Location getLocation() {
		return location;
	}

	public void seLocation(Location location) {
		this.location =location;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}

