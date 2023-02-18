package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity,Long> {
}
