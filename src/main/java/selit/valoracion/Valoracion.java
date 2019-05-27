package selit.valoracion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Representa a las valoraciones guardadas en la base de datos.
 */
@Entity 
@Table(name="valoracion", schema="selit")
public class Valoracion {

	/** Identificador de la valoracion */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_valoracion")
    private Long id_valoracion;

    /** Identificador del usuario comprador */
	@Column(name="id_comprador")
    private Long id_comprador;

	/** Identificador del usuario anunciante */
	@Column(name="id_anunciante")
    private Long id_anunciante;
	
	/** Valor de la valoracion */
	@Column(name="valor")
	private float valor;
	
	/** Comentario de la valoracion */
	@Column(name="comentario")
	private String comentario;

	/** Identificador de la subasta de la valoracion */
	@Column(name="id_subasta")
	private Long id_subasta;
	
	/** Identificador del anuncio de la valoracion */
	@Column(name="id_producto")
	private Long id_producto;
	
	/** Identificador del anuncio de la valoracion */
	@Column(name="valorador")
	private String valorador;
	
	/**
	 * Constructor por defecto.
	 */
	public Valoracion() {
		
	}

	/**
	 * Constructor.
	 * @param id_comprador Identificador del usuario comprador.
	 * @param id_anunciante Identificador del usuario anunciante.
	 * @param valor Valor de la valoracion.
	 * @param comentario Comentario de la valoracion.
	 * @param id_subasta Identificador de la subasta de la valoracion.
	 * @param id_producto Identificador del anuncio de la valoracion.
	 */
	public Valoracion(Long id_comprador, Long id_anunciante, float valor, String comentario, Long id_subasta,
			Long id_producto) {
		super();
		this.id_comprador = id_comprador;
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
	public Long getId_valoracion() {
		return id_valoracion;
	}

	/**
	 * Cambia el identificador de la valoracion a id_valoracion.
	 * @param id_valoracion Nuevo identificador de la valoracion.
	 */
	public void setId_valoracion(Long id_valoracion) {
		this.id_valoracion = id_valoracion;
	}

	/**
	 * Devuelve el identificador del usuario comprador.
	 * @return Identificador del usuario comprador.
	 */
	public Long getId_comprador() {
		return id_comprador;
	}

	/**
	 * Cambia el identificador del usuario comprador a id_comprador.
	 * @param id_comprador Nuevo identificador del usuario comprador.
	 */
	public void setId_comprador(Long id_comprador) {
		this.id_comprador = id_comprador;
	}

	/**
	 * Devuelve el identificador del usuario anunciante.
	 * @return Identificador del usuario anunciante.
	 */
	public Long getId_anunciante() {
		return id_anunciante;
	}

	/**
	 * Cambia el identificador del anunciante a id_anunciante.
	 * @param id_anunciante Nuevo identificador del anunciante.
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
	 * Devuelve quien ha realizado la valoracion.
	 * @return Comprador o vendedor segun quien ha realizado la valoracón.
	 */
	public String getValorador() {
		return valorador;
	}


	/**
	 * Cambia quien ha realizado la valoracion.
	 * @param valorador Comprador o vendedor segun quien ha realizado la valoracón.
	 */
	public void setValorador(String valorador) {
		this.valorador = valorador;
	}	
	
	
	
}
