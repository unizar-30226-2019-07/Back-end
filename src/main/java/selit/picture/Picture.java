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
    private String mime;
    
    @Column(name="tipo")
    private String charset;
    
    @Column(name="tamano")
    private Integer tamanyo;
    
    @Column(name="contenido")
    private byte[] base64;
    
    @Column(name="id_producto")
    private Long idProducto;
    
    @Column(name="id_subasta")
    private Long idSubasta;
    
    public Picture() {
    	
    }



	public Picture(Long idImagen, String mime, String charset, Integer tamanyo, byte[] base64, Long idProducto) {
		super();
		this.idImagen = idImagen;
		this.mime = mime;
		this.charset = charset;
		this.tamanyo = tamanyo;
		this.base64 = base64;
		this.idProducto = idProducto;
	}


	public Picture(Long idImagen) {
		this.idImagen = idImagen;
	}
    
	public Picture(String mime, String charset, byte[] base64) {
		super();
		this.mime = mime;
		this.charset = charset;
		this.base64 = base64;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Integer getTamanyo() {
		return tamanyo;
	}

	public void setTamanyo(Integer tamanyo) {
		this.tamanyo = tamanyo;
	}

	public byte[] getBase64() {
		return base64;
	}

	public void setBase64(byte[] base64) {
		this.base64 = base64;
	}
	
	public Long getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
	}
	
    public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	
	public Long getIdSubasta() {
		return idSubasta;
	}


	public void setIdSubasta(Long idSubasta) {
		this.idSubasta = idSubasta;
	}
}
