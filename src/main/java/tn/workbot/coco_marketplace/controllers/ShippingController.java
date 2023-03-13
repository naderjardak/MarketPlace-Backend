package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.services.interfaces.ShippingInterface;

import java.util.List;

@RestController
@RequestMapping("buyerShipping")
public class ShippingController {
    @Autowired
    ShippingInterface shippingInterface;

    @GetMapping("GetAllShippingsByUserId")
    List<Shipping> getAllShippingsByUserID(Long idU){return shippingInterface.getAllShippingsByUserID(idU);}
    @GetMapping("GetShippingById")
    Shipping getShippingById(Long id){return shippingInterface.getShippingById(id);}
    @PostMapping("CreateNewShipping")
    Shipping createShipping(Shipping shipping){return shippingInterface.createShipping(shipping);}
    @PutMapping("UpdateShippingAdresse")
    Shipping updateShipping(Long id, Shipping shipping){return shippingInterface.updateShipping(id,shipping);}
    @DeleteMapping("DeleteShippingAdresse")
    Boolean deleteShipping(Long id){return shippingInterface.deleteShipping(id);}
}
