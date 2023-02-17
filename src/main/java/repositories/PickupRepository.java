package repositories;

import entities.Pickup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRepository extends CrudRepository<Pickup,Long> {

}
