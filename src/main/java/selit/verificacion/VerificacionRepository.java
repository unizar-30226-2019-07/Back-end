package selit.verificacion;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VerificacionRepository extends JpaRepository<Verificacion, Long> {
	
	@Query("select idUsuario from Verificacion where random=:random")
	public Long buscarPorRandom(@Param("random") String random);
	
	@Modifying
    @Transactional
    @Query("delete from Verificacion where random=:random")
    public void deleteRandom(@Param("random") String random);
}
