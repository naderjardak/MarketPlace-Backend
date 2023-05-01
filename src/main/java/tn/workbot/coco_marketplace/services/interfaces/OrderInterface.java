package tn.workbot.coco_marketplace.services.interfaces;

import com.stripe.exception.StripeException;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.Model.CustemerModel;
import tn.workbot.coco_marketplace.entities.enmus.PaymentType;
import tn.workbot.coco_marketplace.entities.enmus.ProductFiltre;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

public interface OrderInterface {

    public Order addOrder(Order order);

    Order GetBasketOrder();
    List<ProductQuantity> productOfBasket();
    // Get all orders from the database
    List<Order> getAllOrders();

    // Update an existing order in the database by ID
    Boolean AddProductToOrder(ProductQuantity productQuantity,String voucher);

    // Delete Product from Order List
    ProductQuantity DeleteProductFromOrder(String refProduct);

    // Update Quantity Of product in basket
    ProductQuantity UpdateQuantiyOfProduct(String refProuct,int quantity);

    //add Shipping to order
    Order AffectShippingAdressToOrder(Long idshipping);


    //Ending command Paiment Prosess
    Boolean endCommandProsess(PaymentType paymentType, Boolean cardPaiment) throws MessagingException;

    // Get an order from the database by ID
    Order getOrderById(Long id);

    // Delete an order from the database by ID
    Boolean deleteOrder(Long id);

    //Stat for basket
    Map<String, Integer> statsByStatusType();

    //Ranking Users
    List<Map<String,Integer>> statsByStatusTypeOrdred();

    public List<String> PDFstatsByStatusTypeOrdred();

    //Governorate top Shipped
    List<Map<String,Integer>> GovernoratTopShipped();

    //Sum of order Amount
    float SummOrder();

    //StripePayement
    CustemerModel StripePayementService(CustemerModel data) throws StripeException, MessagingException;

    //Delete order Created for more than 10 Days when status is Basket
    void deleteOrderAfterDateExmiration();

    //Buyer Product Page and research
    List<Product> researchProduct(float maxPrix , float minPrix , String nameProduct, String categorie, String mark, ProductFiltre productFiltre);

    //validate Cash On delivery Command
    String validateCommand(String token) throws MessagingException;

    //Token generator
    String generateToken(User user);

    //uncode Token
    String parseToken(String token);

    String generateRandomNumber(int n);

    Order deleteBasket();

    List<Order> getAllOrdersByUserId();

    boolean sessionReteurn();

    public List<Order> getBestOrdersUser();

    }
