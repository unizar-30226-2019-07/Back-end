package selit.valoracion;

public class ValoracionAux {

    private Long id_comprador;

    private Long id_anunciante;

	private float valor;

	private String comentario;

	private Long id_subasta;
	
	private Long id_producto;
	
	public ValoracionAux() {
		
	}

	
	public ValoracionAux(Long id_comprador, Long id_anunciante, float valor, String comentario,
			Long id_subasta, Long id_producto) {
		super();
		this.id_comprador = id_comprador;
		this.id_anunciante = id_anunciante;
		this.valor = valor;
		this.comentario = comentario;
		this.id_subasta = id_subasta;
		this.id_producto = id_producto;
	}

	public Long getId_comprador() {
		return id_comprador;
	}

	public void setId_comprador(Long id_comprador) {
		this.id_comprador = id_comprador;
	}

	public Long getId_anunciante() {
		return id_anunciante;
	}

	public void setId_anunciante(Long id_anunciante) {
		this.id_anunciante = id_anunciante;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Long getId_subasta() {
		return id_subasta;
	}

	public void setId_subasta(Long id_subasta) {
		this.id_subasta = id_subasta;
	}

	public Long getId_producto() {
		return id_producto;
	}

	public void setId_producto(Long id_producto) {
		this.id_producto = id_producto;
	}

	
	
}
