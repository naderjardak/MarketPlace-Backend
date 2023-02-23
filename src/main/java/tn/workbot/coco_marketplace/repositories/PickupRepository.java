package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.workbot.coco_marketplace.entities.Pickup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickupRepository extends CrudRepository<Pickup,Long> {
    List<Pickup> findByGovernorate(String governorat);

}
