package merchandise.repositories;

import merchandise.entities.StockDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDetailsRepository extends CrudRepository<StockDetails,Integer>{
}
