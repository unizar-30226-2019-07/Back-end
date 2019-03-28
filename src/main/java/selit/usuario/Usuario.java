package selit.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="usuario", schema="selit")
public class Usuario {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_usuario")
    private Long idUsuario;
    
	@Column(name="username")
    private String username;
    
    @Column(name="sexo", columnDefinition="ENUM('hombre', 'mujer')")
    private String gender;
    
    @Column(name="nacimiento", columnDefinition="DATE")
    private String birth_date;
    
    @Column(name="posX")
    private float posX;
    
    @Column(name="posY")
    private float posY;
    
	@Column(name="calificacion")
    private long rating;
    
    @Column(name="estado_cuenta", columnDefinition="ENUM('bloqueada', 'activa')" )
    private String status;
    
    @Column(name="contrasena")
    private String password;
    
    @Column(name="email")
    private String email;
  
    @Column(name="apellidos")
    private String last_name;
    
    @Column(name="nombre")
    private String first_name;
    
    @Column(name="tipo", columnDefinition="ENUM('administrador', 'usuario')")
    private String tipo;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
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

