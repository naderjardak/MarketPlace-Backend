package tn.workbot.coco_marketplace.repositories;

import lombok.NoArgsConstructor;
import tn.workbot.coco_marketplace.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByName(String name);

    ProductCategory findByNameAndCategoryName(String categoryName,String subCategoryName);
}
