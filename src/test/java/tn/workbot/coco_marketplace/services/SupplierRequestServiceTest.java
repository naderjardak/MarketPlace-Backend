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
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.entities.SupplierRequest;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.SupplierRequestStatus;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.repositories.StoreRepository;
import tn.workbot.coco_marketplace.repositories.SupplierRequestRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;

import javax.mail.MessagingException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class SupplierRequestServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierRequestService supplierRequestService;
    @Autowired
    private SupplierRequestRepository supplierRequestRepository;
    @Autowired
    private UserrRepository userrRepository;
    @Autowired
    private StoreRepository storeRepository;
    @MockBean
    SessionService sessionService;
    @AfterEach
    public void afterEachTest() {
        supplierRequestRepository.deleteAll();
        productRepository.deleteAll();
        storeRepository.deleteAll();
        userrRepository.deleteAll();
        //productCategoryRepository.deleteAll();


    }

    @Test
    public void shouldAcceptRequestAutomatically() throws MessagingException {


        User user = new User();
        user.setFirstName("test");
        user.setBrandName("test-supplier");
        user.setEmail("test1@gmail.com");
        userrRepository.save(user);

        Store store = new Store();
        store.setName("STORE TEST");
        store.setSeller(user);

        storeRepository.save(store);


        Product product = new Product();
        product.setName("test");
        product.setUnityPriceFromSupplier(0.5f);
        product.setDeliveryQuantity(100);
        product.setAutomaticRequestAcceptance(true);
        product.setStore(store);
        productRepository.save(product);
        Date currentDate = new Date(); // date actuelle
        int daysToAdd = 1; // nombre de jours à ajouter
        // Ajouter des jours en millisecondes
        long newTimeInMillis = currentDate.getDate() + 1;

        SupplierRequest supplierRequest = new SupplierRequest();

        supplierRequest.setQuantity(100);
        supplierRequest.setUnityPrice(0.4f);
        supplierRequest.setDeliveryDate(new Date(newTimeInMillis));
        when(sessionService.getUserBySession()).thenReturn(user);

        SupplierRequest supplierRequestSaved = supplierRequestService.create(supplierRequest, product.getId());

        assertNotNull(supplierRequestSaved);
        assertNotNull(supplierRequestSaved.getId());
        assertEquals(SupplierRequestStatus.ACCEPTED, supplierRequestSaved.getRequestStatus());
    }

    @Test
    public void shouldNotAcceptRequestAutomatically() throws MessagingException {


        User user = new User();
        user.setFirstName("test");
        user.setBrandName("test-supplier");
        user.setEmail("test1@gmail.com");
        userrRepository.save(user);

        Store store = new Store();
        store.setName("STORE TEST");
        store.setSeller(user);

        storeRepository.save(store);


        Product product = new Product();
        product.setName("test");
        product.setUnityPriceFromSupplier(0.5f);
        product.setDeliveryQuantity(100);
        product.setAutomaticRequestAcceptance(true);
        product.setStore(store);
        productRepository.save(product);
        Date currentDate = new Date(); // date actuelle
        int daysToAdd = 1; // nombre de jours à ajouter
        // Ajouter des jours en millisecondes
        long newTimeInMillis = currentDate.getDate() + 1;

        SupplierRequest supplierRequest = new SupplierRequest();

        supplierRequest.setQuantity(100);
        supplierRequest.setUnityPrice(0.6f);
        supplierRequest.setDeliveryDate(new Date(newTimeInMillis));
        when(sessionService.getUserBySession()).thenReturn(user);

        SupplierRequest supplierRequestSaved = supplierRequestService.create(supplierRequest, product.getId());

        assertNotNull(supplierRequestSaved);
        assertNotNull(supplierRequestSaved.getId());
        assertNotNull(supplierRequestSaved.getProduct());
        assertEquals(SupplierRequestStatus.WAITING_FOR_VALIDATION, supplierRequestSaved.getRequestStatus());
    }
}