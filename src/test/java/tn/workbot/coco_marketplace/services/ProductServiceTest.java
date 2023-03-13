package tn.workbot.coco_marketplace.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tn.workbot.coco_marketplace.Api.OrderMailSenderService;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.ProductStatus;
import tn.workbot.coco_marketplace.entities.enmus.RoleType;
import tn.workbot.coco_marketplace.entities.enmus.SupplierRequestStatus;
import tn.workbot.coco_marketplace.repositories.*;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {


    @Autowired
    RoleRepository roleRepository;
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

    @Autowired
    private SupplierRequestRepository supplierRequestRepository;


    //    @MockBean
//    private UserrRepository userrRepositoryMock;
    @MockBean
    private OrderMailSenderService mailSenderService;

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
        productCategoryRepository.deleteAll();


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
        assertNotNull(savedProduct.getStore());
        assertNotNull(savedProduct.getProductCategory());
        assertNotNull(savedProduct.getReference());
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
    void shouldNotCreateWithoutCategory() throws Exception {


        Product product = new Product();
        product.setQuantity(10);
        product.setName("test");
        product.setProductPrice(25);
        product.setDescription("test");
        product.setProductWeight(2);
        product.setProductStatus(ProductStatus.WAITING_FOR_VALIDATION);

        User user = new User();
        user.setFirstName("test");
        userrRepository.save(user);

        Store store = new Store();
        store.setName("STORE TEST");
        store.setSeller(user);

        storeRepository.save(store);

        Exception thrown = assertThrows(Exception.class, () -> productService.create(product, "STORE TEST"));

        assertEquals("Missing Category", thrown.getMessage());


    }


    @Test
    void shouldCreateProductAndCategoryAndSubcategory() throws Exception {

        Product product = new Product();
        product.setQuantity(10);
        product.setName("test");
        product.setProductPrice(25);
        product.setDescription("test");
        product.setProductWeight(2);
        product.setProductStatus(ProductStatus.WAITING_FOR_VALIDATION);

        User user = new User();
        user.setFirstName("test");
        userrRepository.save(user);

        Store store = new Store();
        store.setName("STORE TEST");
        store.setSeller(user);

        storeRepository.save(store);

        Product savedProduct = productService.createAndAssignCategoryAndSubCategory(product, "CatTest", "subCatTest", "STORE TEST");


        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertNotNull(savedProduct.getStore());
        assertNotNull(productCategoryRepository.findByName("CatTest"));
        assertNotNull(productCategoryRepository.findByName("subCatTest"));
        assertNotNull(savedProduct.getProductCategory());
        assertNotNull(savedProduct.getProductCategory().getCategory());
        assertNotNull(savedProduct.getReference());
        assertEquals(product, savedProduct);
    }

    @Test
    void shouldCreateProductAndSubcategory() throws Exception {

        ProductCategory category1 = new ProductCategory();
        category1.setName("CatTest");

        productCategoryRepository.save(category1);

        Product product = new Product();
        product.setQuantity(10);
        product.setName("test");
        product.setProductPrice(25);
        product.setDescription("test");
        product.setProductWeight(2);
        product.setProductStatus(ProductStatus.WAITING_FOR_VALIDATION);

        User user = new User();
        user.setFirstName("test");
        userrRepository.save(user);

        Store store = new Store();
        store.setName("STORE TEST");
        store.setSeller(user);

        storeRepository.save(store);

        Product savedProduct = productService.createAndAssignCategoryAndSubCategory(product, "CatTest", "subCatTest", "STORE TEST");


        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertNotNull(savedProduct.getStore());
        assertNotNull(productCategoryRepository.findByName("CatTest"));
        assertNotNull(productCategoryRepository.findByName("subCatTest"));
        assertNotNull(savedProduct.getProductCategory());
        assertNotNull(savedProduct.getProductCategory().getCategory());
        assertNotNull(savedProduct.getReference());
        assertEquals(product, savedProduct);
    }


    @Test
    public void testProductsOutOfStock() {
        //Create a list of users
        List<User> userList = new ArrayList<>();

        //create roke
        Role role = new Role();
        role.setType(RoleType.SELLER);
        roleRepository.save(role);

        // Create a user with a store and a product out of stock
        User user1 = new User();
        user1.setEmail("user1@test.com");
        user1.setRole(role);
        userrRepository.save(user1);
        Store store1 = new Store();
        store1.setName("Store 1");
        store1.setSeller(user1);

        storeRepository.save(store1);
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setReference("ABC123");
        product1.setQuantity(0);
        product1.setStore(store1);
        Set<Product> productList1 = new HashSet<>();
        productList1.add(product1);
        store1.setProducts(productList1);
        Set<Store> storeList1 = new HashSet<>();
        storeList1.add(store1);
        user1.setStores(storeList1);
        userList.add(user1);

        productRepository.save(product1);
        storeRepository.save(store1);


        // Create a user with a store and a product in stock
        User user2 = new User();
        user2.setEmail("user2@test.com");
        user2.setRole(role);
        userrRepository.save(user2);

        Store store2 = new Store();
        store2.setName("Store 2");
        store2.setSeller(user2);
        storeRepository.save(store2);
        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setReference("DEF456");
        product2.setQuantity(1);
        product2.setStore(store2);
        Set<Product> productList2 = new HashSet<>();
        productList2.add(product2);
        store2.setProducts(productList2);
        Set<Store> storeList2 = new HashSet<>();
        storeList2.add(store2);
        user2.setStores(storeList2);
        userList.add(user2);


        productRepository.save(product2);
        storeRepository.save(store2);
        //MailSenderService mailSenderService = mock(MailSenderService.class);

        // Call the productsOutOfStock method
        productService.productsOutOfStock();

        // Verify that sendEmail was called once with the correct arguments for user1
        verify(mailSenderService, times(1)).sendEmail(eq("user1@test.com"), eq("Products Out Of Stock"), eq("Products Out Of Stock\nSTORE : Store 1\n   ABC123 : Product 1"));


        // Verify that sendEmail was not called for user2
        verify(mailSenderService, never()).sendEmail(eq("user2@test.com"), anyString(), anyString());
    }

    @Test
    public void shoufldReturnPDF() {

        String suppStatus = "DELIVERED";
        Product product = new Product();

        product.setName("Test product");
        productRepository.save(product);

        SupplierRequest supplierRequest1 = new SupplierRequest();
        supplierRequest1.setReference("REF001");
        supplierRequest1.setQuantity(10);
        supplierRequest1.setUnityPrice(100.0F);
        supplierRequest1.setDeliveryDate(new Date());
        supplierRequest1.setRequestStatus(SupplierRequestStatus.DELIVERED);
        supplierRequestRepository.save(supplierRequest1);
        SupplierRequest supplierRequest2 = new SupplierRequest();
        supplierRequest2.setReference("REF002");
        supplierRequest2.setQuantity(20);
        supplierRequest2.setUnityPrice(200.0F);
        supplierRequest2.setDeliveryDate(new Date());
        supplierRequest2.setRequestStatus(SupplierRequestStatus.ACCEPTED);
        supplierRequestRepository.save(supplierRequest2);

        // Act
        ByteArrayInputStream inputStream = productService.allSupplierRequestsOnProduct(product.getId(), suppStatus);

        // Assert
        assertNotNull(inputStream);
    }
}

