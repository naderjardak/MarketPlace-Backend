package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.Pickup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRepository extends CrudRepository<Pickup,Long> {

}
