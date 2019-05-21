package selit.valoracion;

import selit.usuario.UsuarioAux;

/** 
 * Representa a las valoraciones devueltas por la API 
 */
public class ValoracionAux {

	/** Identificador de la valoracion */	
    private Long id_comprador;

	/** Identificador del usuario anunciante */
    private Long id_anunciante;
	
    /** Valor de la valoracion */
	private float valor;

	/** Comentario de la valoracion */
	private String comentario;

	/** Identificador de la subasta de la valoracion */
	private Long id_subasta;
	
	/** Identificador del anuncio de la valoracion */
	private Long id_producto;
	
	/** Usuario comprador */
	private UsuarioAux buyer;
	
	/**
	 * Constructor por defecto.
	 */
	public ValoracionAux() {
		
	}

	/**
	 * Constructor.
	 * @param buyer Usuario comprador.
	 * @param id_anunciante Identificador del usuario anunciante.
	 * @param valor Valor de la valoracion.
	 * @param comentario Comentario de la valoracion.
	 * @param id_subasta Identificador de la subasta de la valoracion.
	 * @param id_producto Identificador del anuncio de la valoracion.
	 */
	public ValoracionAux(UsuarioAux buyer, Long id_anunciante, float valor, String comentario,
			Long id_subasta, Long id_producto) {
		super();
		this.buyer = buyer;
		this.id_anunciante = id_anunciante;
		this.valor = valor;
		this.comentario = comentario;
		this.id_subasta = id_subasta;
		this.id_producto = id_producto;
	}

	/**
	 * Devuelve el identificador de la valoracion.
	 * @return Identificador de la valoracion.
	 */
	public Long getId_comprador() {
		return id_comprador;
	}

	/**
	 * Cambia el identificador de la valoracion a id_valoracion.
	 * @param id_valoracion Nuevo identificador de la valoracion.
	 */
	public void setId_comprador(Long id_comprador) {
		this.id_comprador = id_comprador;
	}

	/**
	 * Devuelve el identificador del usuario comprador.
	 * @return Identificador del usuario comprador.
	 */
	public Long getId_anunciante() {
		return id_anunciante;
	}

	/**
	 * Cambia el identificador del usuario comprador a id_comprador.
	 * @param id_comprador Nuevo identificador del usuario comprador.
	 */
	public void setId_anunciante(Long id_anunciante) {
		this.id_anunciante = id_anunciante;
	}

	/**
	 * Devuelve el valor de la valoracion.
	 * @return Valor de la valoracion.
	 */
	public float getValor() {
		return valor;
	}

	/**
	 * Cambia el valor de la valoracion a valor.
	 * @param valor Nuevo valor de la valoracion.
	 */
	public void setValor(float valor) {
		this.valor = valor;
	}

	/**
	 * Devuelve el comentario de la valoracion.
	 * @return Comentario de la valoracion.
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * Cambia el comentario de la valoracion a comentario.
	 * @param comentario Nuevo comentario de la valoracion.
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * Devuelve el identificador de la subasta de la valoracion.
	 * @return Identificador de la subasta de la valoracion.
	 */
	public Long getId_subasta() {
		return id_subasta;
	}

	/**
	 * Cambia el identificador de la subasta de la valoracion a id_subasta.
	 * @param id_subasta Nuevo identificador de la subasta de la valoracion.
	 */
	public void setId_subasta(Long id_subasta) {
		this.id_subasta = id_subasta;
	}

	/**
	 * Devuelve el identificador del anuncio de la valoracion.
	 * @return Identificador del anuncio de la valoracion.
	 */
	public Long getId_producto() {
		return id_producto;
	}
	
	/**
	 * Cambia el identificador del anuncio de la valoracion a id_producto.
	 * @param id_producto Nuevo identificador del anuncio de la valoracion.
	 */
	public void setId_producto(Long id_producto) {
		this.id_producto = id_producto;
	}

	/**
	 * Devuelve el usuario comprador.
	 * @return Usuario comprador.
	 */
	public UsuarioAux getBuyer() {
		return buyer;
	}

	/**
	 * Cambia el usuario comprador a buyer.
	 * @param buyer Nuevo usuario comprador.
	 */
	public void setBuyer(UsuarioAux buyer) {
		this.buyer = buyer;
	}
	
}
