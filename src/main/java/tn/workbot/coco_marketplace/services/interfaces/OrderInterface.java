package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.entities.enmus.PaymentType;

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
    Boolean endCommandProsess(PaymentType paymentType, Boolean cardPaiment);

    // Get an order from the database by ID
    Order getOrderById(Long id);

    // Delete an order from the database by ID
    Boolean deleteOrder(Long id);

    //Stat for basket
    Map<String, Integer> statsByStatusType();

    //Ranking Users
    List<String> statsByStatusTypeOrdred();

    //Governorate top Shipped
    List<String> GovernoratTopShipped();



}
