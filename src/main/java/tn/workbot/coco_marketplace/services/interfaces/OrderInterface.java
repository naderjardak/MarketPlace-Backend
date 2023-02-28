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

    // Get all orders from the database
    List<Order> getAllOrders();

    // Create a new order in the database
    void createOrder(ProductQuantity productQuantity);

    // Update an existing order in the database by ID
    Boolean AddProductToOrder(ProductQuantity productQuantity);

    // Delete Product from Order List
    ProductQuantity DeleteProductFromOrder(Long refProduct);

    // Update Quantity Of product in basket
    ProductQuantity UpdateQuantiyOfProduct(Long refProuct,int quantity);

    //add Shipping to order
    Order AffectShippingAdressToOrder(Shipping shipping);


    //Ending command Paiment Prosess
    Boolean endCommandProsess(PaymentType paymentType, Boolean cardPaiment) throws MessagingException;

    // Get an order from the database by ID
    Order getOrderById(Long id);

    // Delete an order from the database by ID
    Boolean deleteOrder(Long id);

    //Stat for basket
    Map<String, Integer> statsByStatusType();

    //Ranking Users
    List<String> statsByStatusTypeOrdred();

    //Governorate top Shipped
    List<Map<String,Integer>> GovernoratTopShipped();

    //Sum of order Amount
    public float SummOrder();

    //StripePayement
    CustemerModel StripePayementService(CustemerModel data) throws StripeException, MessagingException;

    //Delete order Created for more than 10 Days when status is Basket
    void deleteOrderAfterDateExmiration();

    //Buyer Product Page and research
    public List<Product> research(float maxPrix , float minPrix , String nameProduct, String categorie, String mark, ProductFiltre productFiltre);

    //validate Cash On delivery Command
    public String validateCommand(String token) throws MessagingException;

    //Token generator
    public String generateToken(User user);

    //uncode Token
    public String parseToken(String token);

    public String generateRandomNumber(int n);
    }
