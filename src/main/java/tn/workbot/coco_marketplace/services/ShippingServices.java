package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.entities.User;
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
    SessionService sessionService;


    public List<Shipping> getAllShippingsByUserID(Long idU) {
        return shippingRepository.findShippingListByUserId(idU);
    }

    public Shipping getShippingById(Long id) {
        return shippingRepository.findById(id).orElse(null);
    }

    public Shipping createShipping(Shipping shipping) {
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
        Shipping existingShipping = shippingRepository.findById(id).orElse(null);
        if (existingShipping == null) {
            return false;
        }
        shippingRepository.delete(existingShipping);
        return true;
    }

    public List<Shipping> getAllUserShippings() {
        return shippingRepository.findShippingByBuyerId(sessionService.getUserBySession().getId());
    }
}
