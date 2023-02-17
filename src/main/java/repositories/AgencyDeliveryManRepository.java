package repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyDeliveryManRepository extends CrudRepository<AgencyDeliveryManRepository,Long> {

}
