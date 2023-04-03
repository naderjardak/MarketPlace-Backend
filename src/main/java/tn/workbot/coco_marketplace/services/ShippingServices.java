package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.repositories.ShippingRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.ShippingInterface;

import java.util.List;

@Service
@Slf4j
public class ShippingServices implements ShippingInterface {

    @Autowired
    ShippingRepository shippingRepository;
    @Autowired
    UserrRepository userrRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    SessionService sessionService;


    public List<Shipping> getAllShippingsByUserID(Long idU) {
        return shippingRepository.findShippingListByUserId(idU);
    }

    public Shipping getShippingById(Long id) {
        return shippingRepository.findById(id).orElse(null);
    }

    public Shipping createShipping(Shipping shipping) {
        shipping.setBuyer(sessionService.getUserBySession());
        return shippingRepository.save(shipping);
    }

    public Shipping updateShipping(Long id, Shipping shipping) {
        if (shippingRepository.findById(id).isPresent()) {
            shipping.setId(id);
            return shippingRepository.save(shipping);
        }
        return null;
    }

    public Boolean deleteShipping(Long id) {
        Shipping shipping = shippingRepository.findById(id).orElse(null);
        List<Order> orders = orderRepository.findByShipping(shipping);
        orders.forEach(order -> order.setShipping(null));
        if (shipping != null)
        shippingRepository.delete(shipping);

        return true;
    }

    public List<Shipping> getAllUserShippings() {
        return shippingRepository.findShippingByBuyerId(sessionService.getUserBySession().getId());
    }
}
