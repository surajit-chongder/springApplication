package merchandise.repositories;

import merchandise.entities.SupplierDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDetailsRepository extends CrudRepository<SupplierDetails,Integer>{
}
