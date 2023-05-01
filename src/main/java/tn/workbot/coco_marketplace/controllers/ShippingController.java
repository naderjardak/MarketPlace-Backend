package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.services.interfaces.ShippingInterface;

import java.util.List;

@RestController
@RequestMapping("buyerShipping")
@PreAuthorize("hasAuthority('BUYER')||hasAuthority('ADMIN')")

public class ShippingController {
    @Autowired
    ShippingInterface shippingInterface;

    @GetMapping("GetAllShippingsByUserId")
    List<Shipping> getAllShippingsByUserID(@RequestParam Long idU){return shippingInterface.getAllShippingsByUserID(idU);}
    @GetMapping("GetShippingById")
    Shipping getShippingById(@RequestParam Long id){return shippingInterface.getShippingById(id);}
    @PostMapping("CreateNewShipping")
    Shipping createShipping(@RequestBody Shipping shipping){return shippingInterface.createShipping(shipping);}
    @PutMapping("UpdateShippingAdresse")
    Shipping updateShipping(@RequestParam Long id, @RequestBody Shipping shipping){return shippingInterface.updateShipping(id,shipping);}
    @DeleteMapping("DeleteShippingAdresse")
    Boolean deleteShipping(@RequestParam Long id){return shippingInterface.deleteShipping(id);}
    @GetMapping("getAllUserShippings")
    List<Shipping> getAllUserShippings(){return shippingInterface.getAllUserShippings();}
}
