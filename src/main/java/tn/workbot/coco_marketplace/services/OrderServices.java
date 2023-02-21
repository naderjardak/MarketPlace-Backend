package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.StatusOrderType;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.repositories.ProductQuantityRepository;
import tn.workbot.coco_marketplace.repositories.ShippingRepository;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;
import tn.workbot.coco_marketplace.services.interfaces.ProductQuantityInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    productQuantityRepository.saveAll(order.getProductQuantities());
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

    @Override
    public Map<String, Integer> statsByStatusType() {
        List<Order> orderList=orderRepository.findAll();
        Map<String, Integer> stats = new HashMap<>();
        int BasketCount=0;
        int WAITING_FOR_PAYMENTCount=0;
        int ACCEPTED_PAYMENTCount=0;
        int REFUSED_PAYMENTCount=0;

        stats.put("AllCount",orderList.size());
        for(Order o:orderList)
        {
            if(o.getStatus().equals(StatusOrderType.BASKET))
            {
                BasketCount+=1;
            }
            else if(o.getStatus().equals(StatusOrderType.WAITING_FOR_PAYMENT))
            {
                WAITING_FOR_PAYMENTCount+=1;
            }
            else if(o.getStatus().equals(StatusOrderType.ACCEPTED_PAYMENT))
            {
                ACCEPTED_PAYMENTCount+=1;
            }
            else
            {
                REFUSED_PAYMENTCount+=1;
            }
        }
        stats.put(StatusOrderType.BASKET.toString(),BasketCount);
        stats.put(StatusOrderType.WAITING_FOR_PAYMENT.toString(),WAITING_FOR_PAYMENTCount);
        stats.put(StatusOrderType.ACCEPTED_PAYMENT.toString(),ACCEPTED_PAYMENTCount);
        stats.put(StatusOrderType.REFUSED_PAYMENT.toString(),REFUSED_PAYMENTCount);

        return stats;
    }

    @Override
    public List<String> statsByStatusTypeOrdred() {
        return orderRepository.RankUsersByOrdersAcceptedPayement();
    }
}



