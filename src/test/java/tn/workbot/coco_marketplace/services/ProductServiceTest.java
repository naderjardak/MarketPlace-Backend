package tn.workbot.coco_marketplace.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.ProductCategory;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.ProductStatus;
import tn.workbot.coco_marketplace.repositories.ProductCategoryRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.repositories.StoreRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {


    @Autowired
    private UserrRepository userrRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    //    @BeforeEach
//    public void initTest() {
//        User user = new User();
//        user.setFirstName("test");
//        userrRepository.save(user);
//
//        Store store = new Store();
//        store.setName("STORE TEST");
//        store.setSeller(user);
//
//        storeRepository.save(store);
//
//    }
//
    @AfterEach
    public void afterEachTest() {
        productRepository.deleteAll();
        storeRepository.deleteAll();
        userrRepository.deleteAll();


    }


    @Test
    void shouldCreate() throws Exception {

        ProductCategory category1 = new ProductCategory();
        ProductCategory subCategory = new ProductCategory();
        category1.setName("test");
        subCategory.setName("test");
        subCategory.setCategory(category1);
        productCategoryRepository.save(subCategory);

        Product product = new Product();
        product.setQuantity(10);
        product.setName("test");
        product.setProductPrice(25);
        product.setDescription("test");
        product.setProductWeight(2);
        product.setProductCategory(subCategory);
        product.setProductStatus(ProductStatus.WAITING_FOR_VALIDATION);

        User user = new User();
        user.setFirstName("test");
        userrRepository.save(user);

        Store store = new Store();
        store.setName("STORE TEST");
        store.setSeller(user);

        storeRepository.save(store);

        Product savedProduct = productService.create(product, "STORE TEST");

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals(product, savedProduct);


    }

    @Test
    void shouldNotCreateWithoutStore() throws Exception {

        ProductCategory category1 = new ProductCategory();
        ProductCategory subCategory = new ProductCategory();
        category1.setName("test");
        subCategory.setName("test");
        subCategory.setCategory(category1);
        productCategoryRepository.save(subCategory);

        Product product = new Product();
        product.setQuantity(10);
        product.setName("test");
        product.setProductPrice(25);
        product.setDescription("test");
        product.setProductWeight(2);
        product.setProductCategory(subCategory);
        product.setProductStatus(ProductStatus.WAITING_FOR_VALIDATION);

        User user = new User();
        user.setFirstName("test");
        userrRepository.save(user);


        Exception thrown = assertThrows(Exception.class, () -> productService.create(product, "STORE TEST"));

        assertEquals("Store not found", thrown.getMessage());

    }


    @Test
    void createAndAssignCategoryAndSubCategory() {

    }

    @Test
    void allSupplierRequestsOnProduct() {
    }

    @Test
    void productsOutOfStock() {
    }
}