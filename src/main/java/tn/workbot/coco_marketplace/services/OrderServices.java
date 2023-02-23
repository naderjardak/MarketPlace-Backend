package tn.workbot.coco_marketplace.services;

import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.controllers.OrderStatsController;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.PaymentType;
import tn.workbot.coco_marketplace.entities.enmus.StatusOrderType;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.repositories.ProductQuantityRepository;
import tn.workbot.coco_marketplace.repositories.ShippingRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;
import tn.workbot.coco_marketplace.services.interfaces.ProductQuantityInterface;

import javax.validation.constraints.Null;
import java.util.Date;
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
    @Autowired
    UserrRepository userrRepository;

            @Override
            public List<Order> getAllOrders() {
                return orderRepository.findAll();
            }

            @Override
            public Order getOrderById(Long id) {
                return orderRepository.findById(id).orElse(null);
            }

            @Override
            public Order createOrder(ProductQuantity productQuantity) {

                //Session Manager Id ne9sa affectation mte3 id user lil order
                User user=userrRepository.findById(1L).get();
                Order newOrder = new Order();
                newOrder.setBuyer(user);
                newOrder.setStatus(StatusOrderType.BASKET);
                newOrder.getProductQuantities().add(productQuantity);
                newOrder.setCreationDate(new Date(System.currentTimeMillis()));
                float sum =productQuantity.getProduct().getProductPrice()*productQuantity.getQuantity();
                newOrder.setSum(sum);
                return null;

            }

            @Override
            public Boolean AddProductToOrder(ProductQuantity productQuantity) {
                 Order order= orderRepository.BasketExistance(1L);
                  if(order==null) {
                      createOrder(productQuantity);
                      return true;
                  }
                  order.getProductQuantities().add(productQuantity);
                  order.setSum(order.getSum()+(productQuantity.getQuantity()*productQuantity.getProduct().getProductPrice()));
                  orderRepository.save(order);
                    return true;
            }

            public Order AffectShippingAdressToOrder(Shipping shipping)
            {
                Order order= orderRepository.BasketExistance(1L);
                order.setShipping(shipping);
                return orderRepository.save(order);
            }

            public Boolean endCommandProsess(PaymentType paymentType,Boolean cardPaiment)
            {
                Order order= orderRepository.BasketExistance(1L);
                if (paymentType == PaymentType.CASH_ON_DELIVERY)
                {
                    order.setStatus(StatusOrderType.WAITING_FOR_PAYMENT);
                    order.setPayment(PaymentType.CASH_ON_DELIVERY);
                }
                else if(paymentType == PaymentType.BANK_CARD && cardPaiment)
                {
                    order.setStatus(StatusOrderType.ACCEPTED_PAYMENT);
                    order.setPayment(PaymentType.BANK_CARD);
                }
                else
                {
                    order.setStatus(StatusOrderType.REFUSED_PAYMENT);
                    orderRepository.save(order);
                    return false;
                }
                orderRepository.save(order);
                return true;
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
            if(o.getStatus().equals(StatusOrderType.BASKET ))
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



