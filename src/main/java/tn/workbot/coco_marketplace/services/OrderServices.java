package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.entities.Shipping;
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
                productQuantityRepository.saveAllAndFlush(order.getProductQuantities());
                Shipping shipping = shippingServices.getShippingById(shippingId);
                order.setShipping(shipping);
                return orderRepository.save(order);
            }

            @Override
            public Boolean updateOrder(Long shippingId, Order order) {
                Order existingOrder = orderRepository.findById(order.getId()).orElse(null);

                if (existingOrder != null) {
                    existingOrder.setRef(order.getRef());
                    existingOrder.setSum(order.getSum());
                    existingOrder.setOrderCode(order.getOrderCode());
                    existingOrder.setPayment(order.getPayment());
                    existingOrder.setStatus(order.getStatus());
                    existingOrder.setCreationDate(order.getCreationDate());
                    Shipping shipping = shippingRepository.findById(shippingId).get();
                    order.setShipping(shipping);
                    orderRepository.save(existingOrder);
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



