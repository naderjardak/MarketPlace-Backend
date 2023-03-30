package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.workbot.coco_marketplace.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Long> {
    @Query("select distinct (o.shipping) from Order o where o.buyer.id=:iduser")
    List<Shipping> findShippingListByUserId(@Param("iduser") Long iduser);

    List<Shipping> findShippingByBuyerId(Long idb);
}
