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
import tn.workbot.coco_marketplace.entities.enmus.PaymentType;
import tn.workbot.coco_marketplace.entities.enmus.ProductFiltre;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;
import tn.workbot.coco_marketplace.services.interfaces.ProductInterface;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderInterface orderInterface;
    @Autowired
    ProductInterface productInterface;

    @PostMapping("AddOrder")
    public Order addOrder(@RequestBody Order order){return orderInterface.addOrder(order);}

    //getProductById
    @GetMapping("GetProductById")
    public Product getById(@RequestParam Long id) {
        return productInterface.getById(id);
    }

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @GetMapping("GetBasketOrder")
    Order GetBasketOrder(){return orderInterface.GetBasketOrder();}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @GetMapping("GetBasketProduct")
    List<ProductQuantity> getProductOfBasket(){return orderInterface.productOfBasket();}

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("GetAllOrders")
    List<Order> getAllOrders(){return orderInterface.getAllOrders();}

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("GetOrderById")
    Order getOrderById(@RequestParam Long id){return orderInterface.getOrderById(id);}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @PutMapping("AddProductToOrder")
    Boolean AddProductToOrder(@RequestBody ProductQuantity productQuantity,@RequestParam(required = false) String voucher){return orderInterface.AddProductToOrder(productQuantity,voucher);}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @PutMapping("UpdateQuantityInOrder")
    public ProductQuantity UpdateQuantiyOfProduct(@RequestParam String refProuct,@RequestParam int quantity){return orderInterface.UpdateQuantiyOfProduct(refProuct,quantity);}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @DeleteMapping("DeleteProductFromOrder")
    public ProductQuantity DeleteProductFromOrder(@RequestParam String refProduct){return orderInterface.DeleteProductFromOrder(refProduct);}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @PutMapping("AddShippingToCard")
    public Order AffectShippingAdressToOrder(@RequestBody Long idshipping){return orderInterface.AffectShippingAdressToOrder(idshipping);}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @PostMapping("payements")
    public CustemerModel payement(@RequestBody CustemerModel data) throws StripeException, MessagingException { return orderInterface.StripePayementService(data); }


    @GetMapping("ProductResearch")
    public List<Product> researchProduct(@RequestParam int maxPrix ,@RequestParam int minPrix ,@RequestParam(required = false) String nameProduct,@RequestParam(required = false) String categorie,@RequestParam(required = false) String mark,@RequestParam ProductFiltre productFiltre){return orderInterface.researchProduct(maxPrix,minPrix,nameProduct,categorie,mark,productFiltre);}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @PutMapping("EndPaimentProcess")
    public Boolean endCommandProsess(@RequestParam PaymentType paymentType, @RequestParam Boolean cardPaiment) throws MessagingException {return orderInterface.endCommandProsess(paymentType,cardPaiment);}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @GetMapping ("validateCommand")
    public String validateCommand(@RequestParam String token) throws MessagingException {return orderInterface.validateCommand(token);}


    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @DeleteMapping("DeleteBasket")
    public Order deleteBasket(){return orderInterface.deleteBasket();}

    @PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")
    @GetMapping("getAllOrdersByUserId")
    public List<Order> getAllOrdersByUserId(){return orderInterface.getAllOrdersByUserId();}

    @GetMapping("sessionReteurn")
    public boolean sessionReteurn(){return orderInterface.sessionReteurn();}

}
