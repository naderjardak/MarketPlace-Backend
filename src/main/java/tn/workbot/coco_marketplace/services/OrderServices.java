package tn.workbot.coco_marketplace.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;

@Service
@Slf4j
public class OrderServices implements OrderInterface {
    @Autowired
    OrderRepository orderRepository;
}
