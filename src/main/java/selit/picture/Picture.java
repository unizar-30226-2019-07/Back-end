package selit.picture;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="imagen", schema="selit")
public class Picture {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_imagen")
    private Long idImagen;
    
    @Column(name="nombre")
    private String name;
    
    @Column(name="tipo")
    private String tipo;
    
    @Column(name="tamano")
    private Integer tamanyo;
    
    @Column(name="contenido")
    private byte[] content;

	public Long getIdImagen() {
		return idImagen;
	}

	public Picture(String name, String tipo, Integer tamanyo, byte[] content) {
		super();
		this.name = name;
		this.tipo = tipo;
		this.tamanyo = tamanyo;
		this.content = content;
	}

	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getTamanyo() {
		return tamanyo;
	}

	public void setTamanyo(Integer tamanyo) {
		this.tamanyo = tamanyo;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
