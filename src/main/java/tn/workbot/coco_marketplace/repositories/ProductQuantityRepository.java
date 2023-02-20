package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity,Long> {

    List<ProductQuantity> findAllByProduct(Product product);
}
