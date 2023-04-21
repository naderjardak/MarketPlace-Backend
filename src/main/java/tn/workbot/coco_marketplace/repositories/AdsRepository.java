package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Ads;
import tn.workbot.coco_marketplace.entities.User;

@Repository
public interface AdsRepository extends CrudRepository<Ads,Long> {
    @Query("select distinct u from User u , Store s , Product p where s.seller.id=u.id and s.id=p.store.id and p.id=:v1")
    public User getSellerOfProduct(@Param("v1") Long idProduct);
}
