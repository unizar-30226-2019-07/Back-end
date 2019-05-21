package selit.verificacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a las verificaciones de los usuarios
 */
@Entity 
@Table(name="verificacion", schema="selit")
public class Verificacion {
	
	/** Identificador del usuario */
	@Id
	@Column(name="id_usuario")
	private Long idUsuario;
	 
	/** Cadena aleatoria asociada al usuario */
	@Column(name="random") 
	private String random;
    
	/**
	 * Devuelve el identificador del usuario.
	 * @return Identificador del usuario.
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}
	
	/**
	 * Cambia el identificador del usuario a idUsuario.
	 * @param idUsuario Nuevo identificador del usuario.
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/** 
	 * Devuelve la cadena aleatoria asociada al usuario.
	 * @return Cadena aleatoria asociada al usuario.
	 */
	public String getRandom() {
		return random;
	}
	
	/**
	 * Cambia la cadena aleatoria asociada al usuario a random.
	 * @param random Nueva cadena aleatoria asociada al usuario.
	 */
	public void setRandom(String random) {
		this.random = random;
	}
 
}
