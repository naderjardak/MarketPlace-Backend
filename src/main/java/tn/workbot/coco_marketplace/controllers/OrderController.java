package tn.workbot.coco_marketplace.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Model.CustemerModel;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.entities.enmus.PaymentType;
import tn.workbot.coco_marketplace.entities.enmus.ProductFiltre;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("order")
@PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
public class OrderController {

    @Autowired
    OrderInterface orderInterface;

    @GetMapping("GetAllOrders")
    List<Order> getAllOrders(){return orderInterface.getAllOrders();}

    @GetMapping("GetOrderById")
    Order getOrderById(@RequestParam Long id){return orderInterface.getOrderById(id);}

    @PutMapping("AddProductToOrder")
    Boolean AddProductToOrder(@RequestBody ProductQuantity productQuantity,@RequestParam(required = false) String voucher){return orderInterface.AddProductToOrder(productQuantity,voucher);}

    @PutMapping("UpdateQuantityInOrder")
    public ProductQuantity UpdateQuantiyOfProduct(@RequestParam String refProuct,@RequestParam int quantity){return orderInterface.UpdateQuantiyOfProduct(refProuct,quantity);}

    @DeleteMapping("DeleteProductFromOrder")
    public ProductQuantity DeleteProductFromOrder(@RequestParam String refProduct){return orderInterface.DeleteProductFromOrder(refProduct);}

    @PutMapping("AddShippingToCard")
    public Order AffectShippingAdressToOrder(@RequestBody Shipping shipping){return orderInterface.AffectShippingAdressToOrder(shipping);}

    @PostMapping("payements")
    public CustemerModel payement(@RequestBody CustemerModel data) throws StripeException, MessagingException { return orderInterface.StripePayementService(data); }

    @GetMapping("ProductResearch")
    public List<Product> researchProduct(@RequestParam int maxPrix ,@RequestParam int minPrix ,@RequestParam(required = false) String nameProduct,@RequestParam(required = false) String categorie,@RequestParam(required = false) String mark,@RequestParam ProductFiltre productFiltre){return orderInterface.researchProduct(maxPrix,minPrix,nameProduct,categorie,mark,productFiltre);}

    @PutMapping("EndPaimentProcess")
    public Boolean endCommandProsess(@RequestParam PaymentType paymentType, @RequestParam Boolean cardPaiment) throws MessagingException {return orderInterface.endCommandProsess(paymentType,cardPaiment);}

    @GetMapping ("validateCommand")
    public String validateCommand(@RequestParam String token) throws MessagingException {return orderInterface.validateCommand(token);}
}
