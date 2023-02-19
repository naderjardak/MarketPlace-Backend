package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.repositories.ProductQuantityRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.services.interfaces.ProductQuantityInterface;

import java.util.List;

@Service
@Slf4j
public class ProductQuantityServices implements ProductQuantityInterface {
    @Autowired
    ProductQuantityRepository productQuantityRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Boolean saveProductQuantity(ProductQuantity productQuantity) {
        Product product=productRepository.findById(productQuantity.getProduct().getReference()).get();
        if(product.getQuantity()<productQuantity.getQuantity())
            return false;
        productQuantityRepository.save(productQuantity);
        return true;
    }

    @Override
    public List<ProductQuantity> getAllProductQuantitiesByProduct(Long ProductId) {
        return productQuantityRepository.findAllByProduct(productRepository.findById(ProductId).get());
    }

    @Override
    public ProductQuantity getProductQuantityById(Long id) {
        return productQuantityRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Boolean deleteProductQuantity(Long id) {
        productQuantityRepository.deleteById(id);
        return true;
    }

}
