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
    @Query("select p from Pickup p,Request r where p.id=r.pickup.id and p.id=:v1  and r.requestStatus like'APPROVED'")
    Pickup pickupprettolivred(@Param("v1") Long id);

}
