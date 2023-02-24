package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.workbot.coco_marketplace.entities.Pickup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;

import java.util.List;

@Repository
public interface PickupRepository extends CrudRepository<Pickup,Long> {
    List<Pickup> findByGovernorate(String governorat);
    @Query("select s from User u , Store s where u.id=s.seller.id and u.id=:v1")
    List<Store> storesofuser(@Param("v1") Long id);
}
