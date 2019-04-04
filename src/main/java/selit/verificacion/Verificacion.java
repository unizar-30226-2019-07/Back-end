package selit.verificacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="verificacion", schema="selit")
public class Verificacion {
     @Id
	 @Column(name="id_usuario")
	    private Long idUsuario;
	 
	 @Column(name="random")
	    private String random;
	 
		public Long getIdUsuario() {
			return idUsuario;
		}

		public void setIdUsuario(Long idUsuario) {
			this.idUsuario = idUsuario;
		}
		
		public String getRandom() {
			return random;
		}

		public void setRandom(String random) {
			this.random = random;
		}
	 
}
