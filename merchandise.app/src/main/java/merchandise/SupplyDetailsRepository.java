package merchandise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyDetailsRepository extends CrudRepository<SupplyDetails,Integer>{
}
