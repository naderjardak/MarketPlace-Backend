package tn.workbot.coco_marketplace.repositories;

import lombok.NoArgsConstructor;
import tn.workbot.coco_marketplace.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByName(String name);

    ProductCategory findByNameAndCategoryName(String categoryName,String subCategoryName);

    List<ProductCategory> findProductCategoriesByCategoryIsNull();
    List<ProductCategory> findProductCategoriesByCategoryIsNotNull();

    List<ProductCategory> findProductCategoriesByCategory(ProductCategory productCategory);
}
