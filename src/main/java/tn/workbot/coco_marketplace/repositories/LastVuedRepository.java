package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.workbot.coco_marketplace.entities.LastVued;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.User;

import java.util.List;

public interface LastVuedRepository extends JpaRepository<LastVued,Long> {
    LastVued findByUserAndProductVued(User user, Product product);

    @Query("SELECT p from Product p,LastVued lv where lv.productVued=p and lv.user.id=:id order by lv.nbrVued desc ")
    List<Product> allVuedBynbVued(@Param("id") Long id);

    @Query("SELECT lv from LastVued lv where lv.user.id=:id")
    List<LastVued> allListVuedByUser(@Param("id") Long id);
}
