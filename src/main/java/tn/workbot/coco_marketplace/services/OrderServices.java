package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;

import java.util.List;

@Service
@Slf4j
public class OrderServices implements OrderInterface {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShippingServices shippingServices;

            @Override
            public List<Order> getAllOrders() {
                return orderRepository.findAll();
            }

            @Override
            public Order getOrderById(Long id) {
                return orderRepository.findById(id).orElse(null);
            }

            @Override
            public Order createOrder(Order order) {
                Shipping shipping = shippingServices.getShippingById(order.getShipping().getId());
                order.setShipping(shipping);
                return orderRepository.save(order);
            }

            @Override
            public Boolean updateOrder(Long id, Order order) {
                Order existingOrder = orderRepository.findById(id).orElse(null);

                if (existingOrder != null) {
                    existingOrder.setRef(order.getRef());
                    existingOrder.setSum(order.getSum());
                    existingOrder.setOrderCode(order.getOrderCode());
                    existingOrder.setPayment(order.getPayment());
                    existingOrder.setStatus(order.getStatus());
                    existingOrder.setCreationDate(order.getCreationDate());
                    Shipping shipping = shippingServices.getShippingById(order.getShipping().getId());
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



