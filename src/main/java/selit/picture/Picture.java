package selit.picture;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa las imagenes en la base de datos.
 */
@Entity 
@Table(name="imagen", schema="selit")
public class Picture {

	/** Identificador de la imagen */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_imagen")
    private Long idImagen;
    
    /** Nombre de la imagen */
    @Column(name="nombre")
    private String mime;
    
    /** Tipo de la imagen */
    @Column(name="tipo")
    private String charset;
    
    /** Tamanyo de la imagen */
    @Column(name="tamano")
    private Integer tamanyo;
    
    /** Contenido de la imagen */
    @Column(name="contenido")
    private byte[] base64;
    
    /** Identificador del anuncio que tiene la imagen */
    @Column(name="id_producto")
    private Long idProducto;
    
    /** Identificador de la subasta que tiene la imagen */
    @Column(name="id_subasta")
    private Long idSubasta;
    
    /**
     * Constructor por defecto.
     */
    public Picture() {
    	
    }

    /**
     * Constructor.
     * @param idImagen Identificador de la imagen.
     * @param mime Nombre de la imagen.
     * @param charset Tipo de la imagen.
     * @param tamanyo Tamanyo de la imagen.
     * @param base64 Contenido de la imagen.
     * @param idProducto Identificador del anuncio que tiene la imagen.
     */
	public Picture(Long idImagen, String mime, String charset, Integer tamanyo, byte[] base64, Long idProducto) {
		super();
		this.idImagen = idImagen;
		this.mime = mime;
		this.charset = charset;
		this.tamanyo = tamanyo;
		this.base64 = base64;
		this.idProducto = idProducto;
	}

	/**
	 * Constructor.
	 * @param idImagen Identificador de la imagen.
	 */
	public Picture(Long idImagen) {
		this.idImagen = idImagen;
	}
    
	/**
	 * Constructor.
	 * @param mime Contenido de la imagen.
	 * @param charset Tipo de la imagen.
	 * @param base64 Contenido de la imagen.
	 */
	public Picture(String mime, String charset, byte[] base64) {
		super();
		this.mime = mime;
		this.charset = charset;
		this.base64 = base64;
	}

	/**
	 * Devuelve el nombre de la imagen.
	 * @return Nombre de la imagen.
	 */
	public String getMime() {
		return mime;
	}

	/**
	 * Cambia el nombre de la imagen a mime.
	 * @param mime Nuevo nombre de la imagen.
	 */
	public void setMime(String mime) {
		this.mime = mime;
	}

	/**
	 * Devuelve el tipo de la imagen.
	 * @return Tipo de la imagen.
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * Cambia el tipo de la imagen a charset.
	 * @param charset Nuevo tipo de la imagen.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * Devuelve el tamanyo de la imagen.
	 * @return Tamanyo de la imagen.
	 */
	public Integer getTamanyo() {
		return tamanyo;
	}

	/**
	 * Cambia el tamanyo de la imagen a tamanyo.
	 * @param tamanyo Nuevo tamanyo de la imagen.
	 */
	public void setTamanyo(Integer tamanyo) {
		this.tamanyo = tamanyo;
	}

	/**
	 * Devuelve el contenido de la imagen. 
	 * @return Contenido de la imagen.
	 */
	public byte[] getBase64() {
		return base64;
	}

	/**
	 * Cambia el contenido de la imagen a base64.
	 * @param base64 Nuevo contenido de la imagen a base64.
	 */
	public void setBase64(byte[] base64) {
		this.base64 = base64;
	}
	
	/**
	 * Devuelve el identificador de la imagen.
	 * @return Identificador de la imagen.
	 */
	public Long getIdImagen() {
		return idImagen;
	}

	/**
	 * Cambia el identificador de la imagen a idImagen.
	 * @param idImagen Nuevo identificador de la imagen.
	 */
	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
	}
	
	/**
	 * Devuelve el identificador del producto que tiene la imagen.
	 * @return Identificador del producto que tiene la imagen.
	 */
    public Long getIdProducto() {
		return idProducto;
	}

    /**
     * Cambia el identificador del producto que tiene la imagen a idProducto.
     * @param idProducto Nuevo identificador del producto que tiene la imagen.
     */
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	
	/**
	 * Devuelve el identificador de la subasta que tiene la imagen.
	 * @return Identificador de la subasta que tiene la imagen.
	 */
	public Long getIdSubasta() {
		return idSubasta;
	}

    /**
     * Cambia el identificador de la subasta que tiene la imagen a idSubasta.
     * @param idSubasta Nuevo identificador de la subasta que tiene la imagen.
     */
	public void setIdSubasta(Long idSubasta) {
		this.idSubasta = idSubasta;
	}
}
