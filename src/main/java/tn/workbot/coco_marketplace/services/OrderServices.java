package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.PaymentType;
import tn.workbot.coco_marketplace.entities.enmus.StatusOrderType;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.repositories.ProductQuantityRepository;
import tn.workbot.coco_marketplace.repositories.ShippingRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

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
            public void createOrder(ProductQuantity productQuantity) {

                //Session Manager Id ne9sa affectation mte3 id user lil order
                User user=userrRepository.findById(1L).get();
                Order newOrder = new Order();
                newOrder.setBuyer(user);
                newOrder.setStatus(StatusOrderType.BASKET);
                if (newOrder.getProductQuantities() == null) {
                    newOrder.setProductQuantities(new ArrayList<>());
                }
                newOrder.getProductQuantities().add(productQuantity);
                newOrder.setCreationDate(new Date(System.currentTimeMillis()));
                float sum =productQuantity.getProduct().getProductPrice()*productQuantity.getQuantity();
                newOrder.setSum(sum);
                productQuantity.setOrder(newOrder);
                orderRepository.save(newOrder);
                productQuantityRepository.save(productQuantity);
            }

            @Override
            public Boolean AddProductToOrder(ProductQuantity productQuantity) {
                //Session Id (change 1L)
                 Order order= orderRepository.BasketExistance(1L);
                  if(order==null) {
                      createOrder(productQuantity);
                      return true;
                  }
                  order.setSum(order.getSum()+(productQuantity.getQuantity()*productQuantity.getProduct().getProductPrice()));
                  orderRepository.save(order);
                    return true;
            }


            @Override
            public ProductQuantity UpdateQuantiyOfProduct(Long refProuct,int quantity)
            {
                if(quantity==0)
                {
                    return DeleteProductFromOrder(refProuct);
                }
                //Session Id (change 1L)
                Order order= orderRepository.BasketExistance(1L);
                ProductQuantity productQuantity = productQuantityRepository.findByProductReferenceAndOrderId(refProuct,order.getId());
                productQuantity.setQuantity(quantity);
                productQuantityRepository.save(productQuantity);
                order= orderRepository.BasketExistance(1L);
                order.setSum(SummOrder());
                return productQuantity;
            }

            @Override
            public ProductQuantity DeleteProductFromOrder(Long refProduct) {
                //Session Id (change 1L)
                Order order= orderRepository.BasketExistance(1L);
                ProductQuantity productQuantity=productQuantityRepository.findByProductReferenceAndOrderId(refProduct,order.getId());

                if(productQuantity!=null) {
                    order.getProductQuantities().remove(productQuantity);
                    productQuantityRepository.delete(productQuantity);
                }
                order.setSum(SummOrder());

                if(order.getProductQuantities().size()==0)
                {
                    orderRepository.deleteById(order.getId());
                    return productQuantity;
                }
                orderRepository.save(order);
                return productQuantity;
            }

            @Override
            public Order AffectShippingAdressToOrder(Shipping shipping)
            {
                Order order= orderRepository.BasketExistance(1L);
                order.setShipping(shipping);
                return orderRepository.save(order);
            }

            @Override
            public Boolean endCommandProsess(PaymentType paymentType,Boolean cardPaiment)
            {

                Order order= orderRepository.BasketExistance(1L);

                if(order.getShipping()==null)
                    return false;

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

            public float SummOrder()
            {
                Order order= orderRepository.BasketExistance(1L);
                float sum=0;
                for(ProductQuantity pq:order.getProductQuantities())
                {
                    sum+= pq.getQuantity()*pq.getProduct().getProductPrice();
                }
                return sum;
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

            @Override
            public List<String> GovernoratTopShipped()
            {
                return orderRepository.RankGouvernoratByNbOrders();

            }

}



