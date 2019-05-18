package selit.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a los usuarios guardados en la base de datos
 */
@Entity 
@Table(name="usuario", schema="selit")
public class Usuario {
	
	/** Identificador del usuario */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_usuario")
    private Long idUsuario;
    
    /** Genero del usuario */
    @Column(name="sexo", columnDefinition="ENUM('hombre', 'mujer')")
    private String gender;
    
    /** Fecha de nacimiento del usuario */
    @Column(name="nacimiento", columnDefinition="DATE")
    private String birth_date;
   
    /** Latitud de la ubicacion del usuario */
	@Column(name="posX")
    private float posX;
    
    /** Longitud de la ubicacion del usuario */
    @Column(name="posY")
    private float posY;
    
    /** Calificacion del usuario */
	@Column(name="calificacion", columnDefinition="FLOAT")
    private float rating;
    
	/** Estado de la cuenta (bloqueada o activa) del usuario */
    @Column(name="estado_cuenta", columnDefinition="ENUM('bloqueada', 'activa')" )
    private String status;
    
    /** Contrasenya cifrada del usuario */
    @Column(name="contrasena")
    private String password;
    
    /** Correo electronico del usuario */
    @Column(name="email")
    private String email;
  
    /** Apellidos del usuario */
    @Column(name="apellidos")
    private String last_name;
    
    /** Nombre del usuario */
    @Column(name="nombre")
    private String first_name;
    
    /** Tipo (administrador o usuario normal) del usuario */
    @Column(name="tipo", columnDefinition="ENUM('administrador', 'usuario')")
    private String tipo;
    
    /** Identificador de la imagen del usuario */
    @Column(name="id_imagen")
    private Long idImagen;
    
    /** 
     * Constructor por defecto del usuario 
     */
    public Usuario() {
    	
    }
    
    /**
     * Constructor del usuario
     * @param posX Latitud de la ubicacion del usuario
     * @param posY Longitud de la ubicacion del usuario
     * @param password Contrasenya cifrada del usuario
     * @param email Correo electronico del usuario
     * @param last_name Apellidos del usuario
     * @param first_name Nombre del usuario
     * @param status Estado (bloqueada o activada) de la cuenta del usuario
     * @param tipo Tipo de usuario (administrador o usuario normal)
     * @param rating Calificacion del usuario
     */
    public Usuario(float posX, float posY, String password, String email, String last_name, String first_name, String status, String tipo, float rating) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.password = password;
		this.email = email;
		this.last_name = last_name;
		this.first_name = first_name;
		this.status = status;
		this.tipo = tipo;
		this.rating = rating;
	}

    /**
     * Constructor del usuario
     * @param idUsuario Identificador del usuario
     * @param birth_date Fecha de nacimiento del usuario
     * @param rating Calificacion del usuario
     * @param last_name Apellidos del usuario
     * @param first_name Nombre del usuario
     * @param email Correo electronico del usuario
     * @param posX Latitud de la ubicacion del usuario
     * @param posY Longitud de la ubicacion del usuario
     * @param idImagen Identificador de la imagen 
     */
    public Usuario(Long idUsuario, String birth_date, float rating, String last_name, String first_name, String email, float posX, float posY, Long idImagen) {
    	this.idUsuario = idUsuario;
    	this.birth_date = birth_date;
    	this.rating = rating;
    	this.last_name = last_name;
    	this.first_name = first_name;
    	this.email = email;
    	this.posX = posX;
    	this.posY = posY;
    	this.idImagen = idImagen;
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
	 * Devuelve la latitud de la ubicacion del usuario
	 * @return Latitud de la ubicacion del usuario
	 */
	public float getPosX() {
		return posX;
	}

	/**
	 * Cambia la latitud de la ubicacion del usuario a posX
	 * @param posX Nueva latitud de la ubicacion del usuario
	 */
	public void setPosX(float posX) {
		this.posX = posX;
	}
	
	/**
	 * Devuelve la longitud de la ubicacion del usuario
	 * @return Longitud de la ubicacion del usuario
	 */
	public float getPosY() {
		return posY;
	}

	/**
	 * Cambia la longitud de la ubicacion del usuario a posY
	 * @param posY Nueva longitud de la ubicacion del usuario
	 */
	public void setPosY(float posY) {
		this.posY = posY;
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
	 * Devuelve el identificador de la imagen del usuario
	 * @return Identificador de la imagen del usuario
	 */
	public Long getIdImagen() {
		return idImagen;
	}

	/**
	 * Cambia el identificador de la imagen del usuario a idImagen
	 * @param idImagen Nuevo identificador de la imagen del usuario
	 */
	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
	}
	
}

