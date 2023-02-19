package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.services.interfaces.ProductInterface;

import java.util.List;

@Service
public class ProductService implements ProductInterface {

    @Autowired
    ProductRepository productRepository;


    @Override
    public Product create(Product p) {
        return productRepository.save(p);
    }


    @Override
    public Product update(Product p) {
        return productRepository.save(p);
    }


    @Override
    public List<Product> retrieveAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        if(productRepository.findById(id).isPresent()){
                return productRepository.findById(id).get();}

        return new Product();
    }

    @Override
    public void delete(Product p) {
         productRepository.delete(p);
    }
}
