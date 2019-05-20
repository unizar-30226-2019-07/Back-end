package selit.usuario;

import selit.Location.Location;
import selit.picture.Picture;

/**
 * Representa a los usuarios devueltos por la API
 */
public class UsuarioAux {
	
	/** Identificador del usuario */
    private Long idUsuario;
    
    /** Genero del usuario */
    private String gender;
    
    /** Fecha de nacimiento del usuario */
    private String birth_date;
    
    /** Ubicacion del usuario */
    private Location location;
    
    /** Calificacion del usuario */
    private float rating;
    
    /** Estado (activada o bloqueada) de la cuenta del usuario */
    private String status;
    
    /** Contrasenya cifrada del usuario */
    private String password;
    
    /** Correo electronico del usuario */
    private String email;
  
    /** Apellidos del usuario */
    private String last_name;
    
    /** Nombre del usuario */
    private String first_name;
    
    /** Tipo (administrador o usuario normal) del usuario */
    private String tipo;
    
    /** Imagen del usuario */
    private Picture picture;
    
    /** 
     * Constructor por defecto del usuario
     */
    public UsuarioAux() {
    	
    }
    
    /**
     * Constructor del usuario
     * @param idUsuario Identificador del usuario
     * @param gender Genero del usuario
     * @param birth_date Fecha de nacimiento del usuario
     * @param location Ubicacion del usuario
     * @param rating Calificacion del usuario
     * @param status Estado (bloqueada o activada) de la cuenta del usuario
     * @param password Conrasenya cifrada del usuario
     * @param email Correo electronico del usuario
     * @param last_name Apellidos del usuario
     * @param first_name Nombre del usuario
     * @param tipo Tipo (administrador o normal) del usuario
     * @param picture Imagen del usuario
     */
	public UsuarioAux(Long idUsuario, String gender, String birth_date, Location location, float rating, String status,
			String password, String email, String last_name, String first_name, String tipo, Picture picture) {
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
		this.picture = picture;
	}

    /**
     * Devuelve el identificador del usuario
     * @return Identificador del usuario
     */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Cambia el identificador del usuario a idUsuario
	 * @param idUsuario Nuevo identificador del usuario
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 * Devuelve el genero del usuario
	 * @return Genero del usuario
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Cambia el genero del usuario a gender
	 * @param gender Nuevo genero del usuario
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Devuelve la fecha de nacimiento del usuario
	 * @return Fecha de nacimiento del usuario
	 */
	public String getBirth_date() {
		return birth_date;
	}

	/**
	 * Cambia la fecha de nacimiento del usuario a birth_date
	 * @param birth_date Nueva fecha de nacimiento del usuario
	 */
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	/**
	 * Devuelve la ubicacion del usuario
	 * @return Ubicacion del usuario
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Cambia la ubicacion del usuario a location
	 * @param location Nueva ubicacion del usuario
	 */
	public void seLocation(Location location) {
		this.location =location;
	}

	/**
	 * Devuelve la calificacion del usuario
	 * @return Calificacion del usuario
	 */
	public float getRating() {
		return rating;
	}

	/** 
	 * Cambia la calificacion del usuario a rating
	 * @param rating Nueva calificacion del usuario
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}

	/**
	 * Devuelve el estado (activada o bloqueada) de la cuenta del usuario
	 * @return Estado (activada o bloqueada) de la cuenta del usuario
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Cambia el estado (activada o bloqueada) de la cuenta del usuario a status
	 * @param status Nuevo estado (activada o bloqueada) de la cuenta del
	 * usuario
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Devuelve la contrasenya cifrada del usuario
	 * @return Contrasenya cifrada del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Cambia la contrasenya cifrada del usuario a password
	 * @param password Nueva contrasenya cifrada del usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Devuelve el correo electronico del usuario
	 * @return Correo electronico del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Cambia el correo electronico del usuario a email
	 * @param email Nuevo correo electronico del usuario
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Devuelve los apellidos del usuario
	 * @return Apellidos del usuario
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * Cambia los apellidos del usuario a last_name
	 * @param last_name Nuevos apellidos del usuario
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * Devuelve el nombre del usuario
	 * @return Nombre del usuario
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * Cambia el nombre del usuario a first_name
	 * @param first_name Nuevo nombre del usuario
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * Devuelve el tipo (normal o administrador) del usuario
	 * @return Tipo (normal o administrador) del usuario
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Cambia el tipo (normal o administrador) del usuario a tipo
	 * @param tipo Nuevo tipo (normal o administrador) del usuario
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	
	/**
	 * Devuelve la imagen del usuario
	 * @return Imagen del usuario
	 */
	public Picture getPicture() {
		return picture;
	}

	/**
	 * Cambia la imagen del usuario a picture
	 * @param picture Nueva imagen del usuario
	 */
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	
}

