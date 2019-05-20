package selit.media;

/**
 * Clase que representa los ficheros multimedia.
 */
public class Media {
	
	/** Identificador de la imagen */
	private Long idImagen;

	/** 
	 * Devuelve el identificador de la imagen.
	 * @return Identificador de la imagen.
	 */
	public Long getIdImagen() {
		return idImagen;
	}

	/** 
	 * Cambia el identificador de la iamgen a idImagen.
	 * @param idImagen Nuevo identificador de la imagen.
	 */
	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
	}

	/**
	 * Constructor.
	 * @param idImagen Identificador de la imagen.
	 */
	public Media(Long idImagen) {
		this.idImagen = idImagen;
	}
}
