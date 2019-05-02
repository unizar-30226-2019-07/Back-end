package selit.bid;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BidRepository extends  JpaRepository<Bid, ClavePrimaria> {
	
	@Query("from Bid where subasta_id_producto=:id_subasta")
	public List<Bid> findById_subasta(@Param("id_subasta") Long id_subasta, Sort sort);
	
}
