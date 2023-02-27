package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByReference(String reference);
}
