package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderInterface orderInterface;

    @GetMapping("Get All Orders")
    List<Order> getAllOrders(){return orderInterface.getAllOrders();}

    @PostMapping("Create Order")
    Order createOrder(@RequestBody Order order){return orderInterface.createOrder(order);}

    @PutMapping("Update Order")
    Boolean updateOrder(@RequestParam Long id, @RequestBody Order order){return orderInterface.updateOrder(id,order);}

    @GetMapping("Get Order By Id")
    Order getOrderById(@RequestParam Long id){return orderInterface.getOrderById(id);}

    @PutMapping("Delete Order")
    Boolean deleteOrder(@RequestParam Long id){return orderInterface.deleteOrder(id);}
}
