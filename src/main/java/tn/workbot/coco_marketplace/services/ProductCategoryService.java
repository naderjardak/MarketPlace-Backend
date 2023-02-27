package tn.workbot.coco_marketplace.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.ProductCategory;
import tn.workbot.coco_marketplace.repositories.ProductCategoryRepository;
import tn.workbot.coco_marketplace.services.interfaces.ProductCategoryInterface;

import java.util.List;

@Service
public class ProductCategoryService implements ProductCategoryInterface {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory create(ProductCategory p) {
        return productCategoryRepository.save(p);
    }

    @Override
    public ProductCategory update(ProductCategory p) {
        return productCategoryRepository.save(p);
    }

    @Override
    public List<ProductCategory> retrieveAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory getById(Long id) {
        if (productCategoryRepository.findById(id).isPresent())
            return productCategoryRepository.findById(id).get();

        return new ProductCategory();
    }

    @Override
    public void delete(ProductCategory p) {
        productCategoryRepository.delete(p);
    }

    @Override
    public ProductCategory findByName(String name) {
        if (productCategoryRepository.findByName(name) != null)
            return productCategoryRepository.findByName(name);
        return new ProductCategory();
    }


}
