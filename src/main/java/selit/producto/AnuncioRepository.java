package selit.producto;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

	@Query("select new Anuncio(idProducto, publicate_date, description, title, price)  from Anuncio where id_producto=:id_producto")
	public Optional<Anuncio> findAnuncioCommon(@Param("id_producto") String id_producto);
	
	@Query("from Anuncio where id_producto=:id_producto")
	public Anuncio buscarPorId(@Param("id_producto") String id_producto);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Anuncio set fecha_publicacion=:fecha_publicacion, descripcion=:descripcion, titulo=:titulo, "
			+ "ubicacion=:ubicacion, precio=:precio, moneda=:moneda, nfavoritos=:nfavoritos,nvisitas=:nvisitas,"
			+ "usuario_id_usuario=:usuario_id_usuario,nombre_categoria=:nombre_categoria where id_producto=:id_producto")
	public void actualizarAnuncio(@Param("fecha_publicacion") String publicate_date, 
			@Param("descripcion") String description, @Param("titulo") String title,
			@Param("ubicacion") String location, @Param("precio") float price,
			@Param("moneda") String currency, @Param("nfavoritos") int nfav,
			@Param("nvisitas") int nvis, @Param("usuario_id_usuario") Long id_owner,
			@Param("nombre_categoria") String category, @Param("id_producto") String id_producto);
}
