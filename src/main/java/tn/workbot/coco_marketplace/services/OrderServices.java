package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.repositories.ProductQuantityRepository;
import tn.workbot.coco_marketplace.repositories.ShippingRepository;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;
import tn.workbot.coco_marketplace.services.interfaces.ProductQuantityInterface;

import java.util.List;

@Service
@Slf4j
public class OrderServices implements OrderInterface {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShippingRepository shippingRepository;
    @Autowired
    ShippingServices shippingServices;
    @Autowired
    ProductQuantityRepository productQuantityRepository;

            @Override
            public List<Order> getAllOrders() {
                return orderRepository.findAll();
            }

            @Override
            public Order getOrderById(Long id) {
                return orderRepository.findById(id).orElse(null);
            }

            @Override
            public Order createOrder(Long shippingId,Order order) {
                for (ProductQuantity pq:order.getProductQuantities())
                {
                pq.setOrder(order);
                }
                //Session Manager Id ne9sa affectation mte3 id user lil order
                productQuantityRepository.saveAllAndFlush(order.getProductQuantities());
                Shipping shipping = shippingServices.getShippingById(shippingId);
                order.setShipping(shipping);
                return orderRepository.save(order);
            }

            @Override
            public Boolean updateOrder(Long shippingId, Order order) {
                if (orderRepository.findById(order.getId()).isPresent()) {
                    for (ProductQuantity pq:order.getProductQuantities())
                    {
                        pq.setOrder(order);
                    }
                    productQuantityRepository.saveAllAndFlush(order.getProductQuantities());
                    Shipping shipping = shippingRepository.findById(shippingId).get();
                    order.setShipping(shipping);
                    orderRepository.save(order);
                    return true;
                }
                return false;
            }

            @Override
            public Boolean deleteOrder(Long id) {
                orderRepository.deleteById(id);
                return true;
            }
    }



