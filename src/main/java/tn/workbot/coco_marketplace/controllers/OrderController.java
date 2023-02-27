package tn.workbot.coco_marketplace.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Model.CustemerModel;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.entities.Shipping;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderInterface orderInterface;

    @GetMapping("GetAllOrders")
    List<Order> getAllOrders(){return orderInterface.getAllOrders();}

    @GetMapping("GetOrderById")
    Order getOrderById(@RequestParam Long id){return orderInterface.getOrderById(id);}

    @PutMapping("AddProductToOrder")
    Boolean AddProductToOrder(@RequestBody ProductQuantity productQuantity){return orderInterface.AddProductToOrder(productQuantity);}

    @PutMapping("UpdateQuantityInOrder")
    public ProductQuantity UpdateQuantiyOfProduct(@RequestParam Long refProuct,@RequestParam int quantity){return UpdateQuantiyOfProduct(refProuct,quantity);}

    @DeleteMapping("DeleteProductFromOrder")
    public ProductQuantity DeleteProductFromOrder(@RequestParam Long refProduct){return orderInterface.DeleteProductFromOrder(refProduct);}

    @PutMapping("AddShippingToCard")
    public Order AffectShippingAdressToOrder(@RequestBody Shipping shipping){return orderInterface.AffectShippingAdressToOrder(shipping);}

    @PostMapping("payements")
    public CustemerModel payement(@RequestBody CustemerModel data) throws StripeException { return orderInterface.StripePayementService(data); }

    @GetMapping("ProductResearch")
    public List<Product> research(@RequestParam int maxPrix ,@RequestParam int minPrix ,@RequestParam(required = false) String nameProduct,@RequestParam(required = false) String categorie,@RequestParam(required = false) String mark){return orderInterface.research(maxPrix,minPrix,nameProduct,categorie,mark);}
    /*
    //no need for this now
    @PutMapping("EndPaimentProcess")
    public Boolean endCommandProsess(@RequestParam PaymentType paymentType,@RequestParam Boolean cardPaiment) {return orderInterface.endCommandProsess(paymentType,cardPaiment);}
    */

}
