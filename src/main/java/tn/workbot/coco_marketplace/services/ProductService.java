package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.Api.OrderMailSenderService;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.ProductCategory;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.ProductStatus;
import tn.workbot.coco_marketplace.entities.enmus.RoleType;
import tn.workbot.coco_marketplace.repositories.*;
import tn.workbot.coco_marketplace.services.interfaces.ProductInterface;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class ProductService implements ProductInterface {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    PromotionCodeRepository promotionCodeRepository;

    @Autowired
    OrderMailSenderService mailSenderService;

    @Autowired
    UserrRepository userrRepository;


    @Override
    public Product create(Product p) {
        User user = userrRepository.findById(1L).get();

        if (p.getProductWeight() <= 1) {
            p.setDeliveryPrice(6);
        } else if (p.getProductWeight() > 1) {
            p.setDeliveryPrice(6 + (p.getProductWeight() - 1) * 2.5f);

        }
        p.setProductPriceBeforeDiscount(p.getProductPrice());

        Random random = new Random();
        int nbRand = random.nextInt(99999);
        p.setReference(("REF-" + p.getProductCategory().getName().substring(0, 2).toUpperCase() + p.getName().substring(0, 2).toUpperCase() + nbRand));

        p.setProductStatus(ProductStatus.WAITING_FOR_VALIDATION);
        p.setCreationDate(new Timestamp(System.currentTimeMillis()));

        //a verifier en premier en cas de probleme!!!!
        p.setStore(storeRepository.findStoreByNameAndAndSeller(p.getStore().getName(), user));

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
        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        }

        return new Product();
    }

    @Override
    public void delete(Product p) {
        productRepository.delete(p);
    }

    @Override
    public Product createAndAssignToStore(Product p, Long idStore) {
        Store store = storeService.getById(idStore);
        p.setStore(store);
        return this.create(p);

    }

    @Override
    public Product createAndAssignCategoryAndSubCategory(Product p, String categoryName, String subCatName) {

        ProductCategory category = productCategoryRepository.findByName(categoryName);
        ProductCategory subCategory = productCategoryRepository.findByNameAndCategoryName(subCatName, categoryName);

        if (category == null && subCategory == null) {
            ProductCategory category1 = new ProductCategory();
            category1.setName(categoryName);
            subCategory = new ProductCategory();
            subCategory.setName(subCatName);
            subCategory.setCategory(category1);
            productCategoryRepository.save(subCategory);

        }
        if (category != null && subCategory == null) {
            subCategory = new ProductCategory();
            subCategory.setName(subCatName);
            subCategory.setCategory(category);
            productCategoryRepository.save(subCategory);


        }
        p.setProductCategory(subCategory);


        return this.create(p);
    }


    @Scheduled(cron = "0 0 8 * * *")
    void productsOutOfStock() {
        List<User> userList = userrRepository.findUserByRoleType(RoleType.ADMINISTRTOR);
        for (User user : userList) {
            StringBuilder s = new StringBuilder("Products Out Of Stock");
            int i = 0;
            for (Store store : user.getStores()) {
                s.append("\n ").append("STORE : ").append(store.getName());
                for (Product p : store.getProducts()) {
                    if (p.getQuantity() == 0) {
                        s.append("`\n").append("   ").append(p.getReference()).append(" : ").append(p.getName());
                        i++;

                    }
                }
            }
            if (i > 0) {
                //mailSenderService.sendEmail(user.getEmail(),"Products Out Of Stock",s.toString());
                log.info(s.toString());
            } else {
                log.info("no products out of stock");
            }
        }
    }
}










