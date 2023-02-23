package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderInterface orderInterface;

    @GetMapping("GetAllOrders")
    List<Order> getAllOrders(){return orderInterface.getAllOrders();}

    @PostMapping("CreateOrder")
    Order createOrder(@RequestBody ProductQuantity productQuantity){return orderInterface.createOrder(productQuantity);}

    @PutMapping("UpdateOrder")
    Boolean AddProductToOrder(@RequestBody ProductQuantity productQuantity){return orderInterface.AddProductToOrder(productQuantity);}

    @GetMapping("GetOrderById")
    Order getOrderById(@RequestParam Long id){return orderInterface.getOrderById(id);}

    @DeleteMapping ("DeleteOrder")
    Boolean deleteOrder(@RequestParam Long id){return orderInterface.deleteOrder(id);}
}
