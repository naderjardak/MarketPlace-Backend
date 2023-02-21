package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.User;

import java.util.List;
import java.util.Map;

public interface OrderInterface {

    // Get all orders from the database
    List<Order> getAllOrders();

    // Create a new order in the database
    Order createOrder(Long shippingId,Order order);

    // Update an existing order in the database by ID
    Boolean updateOrder(Long shippingId, Order order);

    // Get an order from the database by ID
    Order getOrderById(Long id);

    // Delete an order from the database by ID
    Boolean deleteOrder(Long id);

    //Stat for basket
    Map<String, Integer> statsByStatusType();

    //Ranking Users
    List<String> statsByStatusTypeOrdred();


}
