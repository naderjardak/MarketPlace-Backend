package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.services.interfaces.ShippingInterface;

import java.util.List;

@Controller
public class ShippingController {
    @Autowired
    ShippingInterface shippingInterface;

    @GetMapping("Get All Shippings By User Id")
    List<Shipping> getAllShippingsByUserID(Long idU){return shippingInterface.getAllShippingsByUserID(idU);}
    @GetMapping("Get Shipping By Id")
    Shipping getShippingById(Long id){return shippingInterface.getShippingById(id);}
    @PostMapping("Create new Shipping")
    Shipping createShipping(Shipping shipping){return shippingInterface.createShipping(shipping);}
    @PutMapping("Update Shipping Adresse")
    Shipping updateShipping(Long id, Shipping shipping){return shippingInterface.updateShipping(id,shipping);}
    @PutMapping("Delete Shipping Adresse")
    Boolean deleteShipping(Long id){return shippingInterface.deleteShipping(id);}
}
