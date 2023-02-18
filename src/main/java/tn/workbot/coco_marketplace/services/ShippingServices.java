package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.repositories.ShippingRepository;
import tn.workbot.coco_marketplace.services.interfaces.ShippingInterface;

@Service
@Slf4j
public class ShippingServices implements ShippingInterface {
    @Autowired
    ShippingRepository shippingRepository;

}
