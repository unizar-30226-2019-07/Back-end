package selit.verificacion;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio de verificaciones en la base de datos.
 */
public interface VerificacionRepository extends JpaRepository<Verificacion, Long> {
	
	/**
	 * Devuelve el identificador del usuario cuya cadena aleatoria es random.
	 * @param random Cadena aleatoria asociada al usuario a buscar.
	 * @return Identificador del usuario cuya cadena aleatoria es random.
	 */
	@Query("select idUsuario from Verificacion where random=:random")
	public Long buscarPorRandom(@Param("random") String random);
	
	/**
	 * Elimina la verificacion pendiente donde el valor aleatorio es random.
	 * @param random Valor aleatorio asociado a un usuario.
	 */
	@Modifying
    @Transactional
    @Query("delete from Verificacion where random=:random")
    public void deleteRandom(@Param("random") String random);
}
