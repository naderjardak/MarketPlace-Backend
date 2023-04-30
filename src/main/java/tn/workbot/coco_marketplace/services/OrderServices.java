package tn.workbot.coco_marketplace.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tn.workbot.coco_marketplace.Api.OrderMailSenderService;
import tn.workbot.coco_marketplace.Api.OrderTwilioService;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.Model.CustemerModel;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.PaymentType;
import tn.workbot.coco_marketplace.entities.enmus.ProductFiltre;
import tn.workbot.coco_marketplace.entities.enmus.StatusOrderType;
import tn.workbot.coco_marketplace.repositories.*;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class OrderServices implements OrderInterface {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShippingRepository shippingRepository;
    @Autowired
    ShippingServices shippingServices;
    @Autowired
    ProductQuantityRepository productQuantityRepository;
    @Autowired
    UserrRepository userrRepository;
    @Autowired
    PromotionCodeRepository promotionCodeRepository;
    @Autowired
    OrderMailSenderService orderMailSenderService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    SessionService sessionService;


    @Value("${stripe.api.key}")
    private String stripeApiKey;
    @Value("${Days.To.Delete.After}")
    private int days;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Order addOrder(Order order){
        order.setBuyer(sessionService.getUserBySession());
        order.setStatus(StatusOrderType.BASKET);
        return orderRepository.save(order);
    }

    @Override
    public Order GetBasketOrder() {
        return orderRepository.BasketExistance(sessionService.getUserBySession().getId());
    }

    @Override
    public List<ProductQuantity> productOfBasket() {
        Order order = orderRepository.BasketExistance(sessionService.getUserBySession().getId());
        List<ProductQuantity> productquantity=new ArrayList<>();
        if(order==null)
            return productquantity;
        return orderRepository.orderProductList(order.getId());
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }




    @Override
    public Boolean AddProductToOrder(ProductQuantity productQuantity,String voucher) {
        System.out.println(productQuantity.getProduct().getId());

        Order order = orderRepository.BasketExistance(sessionService.getUserBySession().getId());
        if (order == null) {
            User user=userrRepository.findById(sessionService.getUserBySession().getId()).get();
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            order = new Order();
            order.setCreationDate(currentDate);
            order.setSum(0);
            order.setDeliveryPrice(0);
            order.setBuyer(user);
            order.setStatus(StatusOrderType.BASKET);
            order.setProductsWeightKg(0);
            order.setProductQuantities(new ArrayList<>());
            order.setPromotionCodeList(new ArrayList<>());
            order=orderRepository.save(order);
        }
        else
        {
            System.out.println("----" +productQuantity.getProduct().getId()+"-----"+order.getId());
            ProductQuantity productQuantity1=productQuantityRepository.findByProductIdAndOrderId(productQuantity.getProduct().getId(),order.getId());
            if(productQuantity1!=null)
                productQuantityRepository.delete(productQuantity1);
        }

        Product product=productRepository.findById(productQuantity.getProduct().getId()).get();
        productQuantity.setProduct(product);
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        PromotionCode promotionCode=promotionCodeRepository.findByProductIdAndVoucher(product.getId(), voucher);
        if(promotionCode!=null )
        {
            if((currentDate.after(promotionCode.getStartDate()) && currentDate.before(promotionCode.getEndtDate())))
            {
                order.getPromotionCodeList().add(promotionCode);
            } else
                return false;
        }
        productQuantity.setOrder(order);
        productQuantity=productQuantityRepository.save(productQuantity);
        orderRepository.save(order);
        UpdateQuantiyOfProduct(productQuantity.getProduct().getReference(),productQuantity.getQuantity());
        return true;
    }

    @Override
    public ProductQuantity UpdateQuantiyOfProduct(String refProuct,int quantity)
    {
        if(quantity==0)
        {
            return DeleteProductFromOrder(refProuct);
        }

        Order order= orderRepository.BasketExistance(sessionService.getUserBySession().getId());
        ProductQuantity productQuantity = productQuantityRepository.findByOrderIdAndProductReference(order.getId(),refProuct);
        productQuantity.setQuantity(quantity);
        productQuantity=productQuantityRepository.save(productQuantity);

        List<PromotionCode> promotionCodeList=order.getPromotionCodeList();
        List<ProductQuantity> productQuantityList=order.getProductQuantities();
        float sum=0;
        if (promotionCodeList.size()>0)
        {
            for(PromotionCode p :promotionCodeList)
            {
                ProductQuantity productQuantity1=productQuantityRepository.findByProductIdAndOrderId(p.getProduct().getId(),order.getId());
                sum+=productQuantity1.getQuantity()*(productQuantity1.getProduct().getProductPrice()-p.getDiscountValue());
            }
        }
        boolean var=true;
        float weight=0;
        for (ProductQuantity pq :productQuantityList)
        {
            var=true;
            if (promotionCodeList.size()>0)
            for(PromotionCode p :promotionCodeList)
            {
                ProductQuantity productQuantity1=productQuantityRepository.findByProductIdAndOrderId(p.getProduct().getId(),order.getId());
                if(productQuantity1==pq)
                    var=false;
            }
            if (var)
                sum+=pq.getQuantity()*pq.getProduct().getProductPrice();
            weight+=pq.getQuantity()*pq.getProduct().getProductWeight();

        }

        if(weight<=1)
            order.setDeliveryPrice(6);
        else if(weight<=10)
            order.setDeliveryPrice(6*weight);
        else
            order.setDeliveryPrice(60+(weight-10)*2);

        order.setProductsWeightKg(weight);
        order.setSum(sum);
        orderRepository.save(order);
        return productQuantity;
    }


    @Override
    public ProductQuantity DeleteProductFromOrder(String refProduct) {
        Order order = orderRepository.BasketExistance(sessionService.getUserBySession().getId());
       List<ProductQuantity> productQuantity = productQuantityRepository.findByProductReferenceAndOrderId(refProduct, order.getId());

        if (productQuantity.size()!=0) {
            for(int i=0;i<productQuantity.size();i++) {
                order.getProductQuantities().remove(productQuantity.get(i));
                productQuantityRepository.delete(productQuantity.get(i));
            }

        }
        if (order.getProductQuantities().size() == 0) {
            orderRepository.deleteById(order.getId());
            return productQuantity.get(0);
        }
        List<PromotionCode> promotionCodeList=order.getPromotionCodeList();
        List<ProductQuantity> productQuantityList=order.getProductQuantities();
        float sum=0;
        if (promotionCodeList.size()>0)
        {

            for(PromotionCode p :promotionCodeList)
            {
                ProductQuantity productQuantity1=productQuantityRepository.findByProductIdAndOrderId(p.getProduct().getId(),order.getId());
                sum+=productQuantity1.getQuantity()*(productQuantity1.getProduct().getProductPrice()-p.getDiscountValue());
            }
        }
        boolean var=true;
        float weight=0;
        for (ProductQuantity pq :productQuantityList)
        {
            var=true;
            if (promotionCodeList.size()>0)
            for(PromotionCode p :promotionCodeList)
            {
                ProductQuantity productQuantity1=productQuantityRepository.findByProductIdAndOrderId(p.getProduct().getId(),order.getId());
                if(productQuantity1==pq)
                    var=false;
            }
            if (var)
                sum+=pq.getQuantity()*pq.getProduct().getProductPrice();
            weight+=pq.getQuantity()*pq.getProduct().getProductWeight();

        }

        if(weight<=1)
            order.setDeliveryPrice(6);
        else if(weight<=10)
            order.setDeliveryPrice(6*weight);
        else
            order.setDeliveryPrice(60+(weight-10)*2);

        order.setProductsWeightKg(weight);
        order.setSum(sum);
        orderRepository.save(order);
        return productQuantity.get(0);
    }

    @Override
    public Order AffectShippingAdressToOrder(Long idshipping) {
        Order order = orderRepository.BasketExistance(sessionService.getUserBySession().getId());
        Shipping shipping=shippingRepository.findById(idshipping).get();
        order.setShipping(shipping);
        return orderRepository.save(order);
    }
    //===
    @Autowired
    UserDetailsService userDetailsService;



    @Override
    public Boolean endCommandProsess(PaymentType paymentType,Boolean cardPaiment) throws MessagingException {
        User user=userrRepository.findById(sessionService.getUserBySession().getId()).get();
        Order order= orderRepository.BasketExistance(user.getId());
        if(order.getShipping()==null)
            return false;

        String msg="";
        if (paymentType == PaymentType.CASH_ON_DELIVERY)
        {
            //Mail api
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("From Coco Market");
            Context context = new Context();
            String msag="Have a nice day, your order is confirmed successfully.";
            context.setVariable("msg",msag);
            String lien="http://localhost:8081/order/validateCommand?token="+generateToken(user);
            context.setVariable("link",lien);
            String name="Thank you, "+user.getFirstName().substring(0,1).toUpperCase()+user.getFirstName().substring(1);
            context.setVariable("name",name);
            String emailContent = templateEngine.process("OrderConfirmation", context);
            mailMessage.setText(emailContent, true);
            javaMailSender.send(message);

        }
        else if(paymentType == PaymentType.BANK_CARD && cardPaiment)
        {
            order.setStatus(StatusOrderType.ACCEPTED_PAYMENT);
            order.setPayment(PaymentType.BANK_CARD);
            msg+="From Coco Market, Have a nice day "+order.getBuyer().getFirstName()+" "+order.getBuyer().getLastName()+" your Payment By card is confirmed successfully.";
            orderMailSenderService.sendEmail(order.getBuyer().getEmail(),"Payment is confirmed","From Coco Market, Have a nice day "+order.getBuyer().getFirstName()+" "+order.getBuyer().getLastName()+" your Payment By card is confirmed successfully.");
            //Twilio mna7iha 3al flous
            //OrderTwilioService.sendSMS(msg);
            List<String> refList=orderRepository.reflist();
            String referance="";
            do{
                referance=generateRandomNumber(10);
            }while (refList.contains(referance));
            order.setRef(referance);
            List<ProductQuantity> lpq=order.getProductQuantities();
            for (ProductQuantity pq :lpq)
            {
                pq.getProduct().setNumberOfPurchase(pq.getProduct().getNumberOfPurchase()+pq.getQuantity());
                productRepository.save(pq.getProduct());
            }
        }
        else
        {
            order.setStatus(StatusOrderType.REFUSED_PAYMENT);
            orderRepository.save(order);
            msg+="From Coco Market, Have a nice day "+order.getBuyer().getFirstName()+" "+order.getBuyer().getLastName()+" you have error in Payment with card please retry with a valid card.";
            //Twilio mna7iha 3al flous
            OrderTwilioService.sendSMS(msg);
            return false;
        }
        orderRepository.save(order);
        return true;
    }



    @Override
    public Boolean deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public float SummOrder() {
        Order order = orderRepository.BasketExistance(sessionService.getUserBySession().getId());
        List<PromotionCode> promotionCodeList = order.getPromotionCodeList();
        float sum = 0;
        if (promotionCodeList.size() == 0)
        {
            for (ProductQuantity pq : order.getProductQuantities()) {
                sum += pq.getQuantity() * pq.getProduct().getProductPriceBeforeDiscount();
            }
        }
        else
        {
            List<Long> idList=new ArrayList<>();
            for (PromotionCode p :promotionCodeList)
            {
                idList.add(p.getProduct().getId());
            }
            boolean var=true;
            for (ProductQuantity pq : order.getProductQuantities()) {
            var=true;
                for (Long i:idList) {
                    PromotionCode promotionCode = promotionCodeRepository.findById(i).get();
                    if (promotionCode.getProduct().getId() == pq.getProduct().getId()) {
                        sum += pq.getQuantity() * (pq.getProduct().getProductPriceBeforeDiscount() - promotionCode.getDiscountValue());
                        var = false;
                    }
                }
                if (var)
                sum += pq.getQuantity() * pq.getProduct().getProductPriceBeforeDiscount();
            }
        }
        return sum;
    }

    @Override
    public Map<String, Integer> statsByStatusType() {
        List<Order> orderList = orderRepository.findAll();
        Map<String, Integer> stats = new HashMap<>();
        int BasketCount = 0;
        int WAITING_FOR_PAYMENTCount = 0;
        int ACCEPTED_PAYMENTCount = 0;
        int REFUSED_PAYMENTCount = 0;

        stats.put("AllCount", orderList.size());
        for (Order o : orderList) {
            if (o.getStatus().equals(StatusOrderType.BASKET)) {
                BasketCount += 1;
            } else if (o.getStatus().equals(StatusOrderType.WAITING_FOR_PAYMENT)) {
                WAITING_FOR_PAYMENTCount += 1;
            } else if (o.getStatus().equals(StatusOrderType.ACCEPTED_PAYMENT)) {
                ACCEPTED_PAYMENTCount += 1;
            } else {
                REFUSED_PAYMENTCount += 1;
            }
        }
        stats.put(StatusOrderType.BASKET.toString(), BasketCount);
        stats.put(StatusOrderType.WAITING_FOR_PAYMENT.toString(), WAITING_FOR_PAYMENTCount);
        stats.put(StatusOrderType.ACCEPTED_PAYMENT.toString(), ACCEPTED_PAYMENTCount);
        stats.put(StatusOrderType.REFUSED_PAYMENT.toString(), REFUSED_PAYMENTCount);

        return stats;
    }

    @Override
    public List<Map<String,Integer>> statsByStatusTypeOrdred() {
        List<Map<String,Integer>> results = orderRepository.RankUsersByOrdersAcceptedPayement();
        return results.subList(0, Math.min(10, results.size()));
    }

    @Override
    public List<String> PDFstatsByStatusTypeOrdred() {
        return orderRepository.PDFRankUsersByOrdersAcceptedPayement();
    }

    @Override
    public List<Map<String, Integer>> GovernoratTopShipped() {
        return orderRepository.RankGouvernoratByNbOrders();
    }



    @Override
    public CustemerModel StripePayementService(CustemerModel data) throws StripeException, MessagingException {
        Stripe.apiKey = stripeApiKey;

        // Create a customer object with a new card
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("name", data.getName());
        customerParams.put("email", data.getEmail());
        customerParams.put("source", data.getCardToken()); // Card token obtained from Stripe.js
        Customer customer = Customer.create(customerParams);

        // Charge the customer's card
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", data.getPaidAmount()); // Amount in cents ($10)
        chargeParams.put("currency", "usd");
        chargeParams.put("customer", customer.getId());
        Charge charge = Charge.create(chargeParams);

        // Check if the payment succeeded and set the payment status in the data object
        if (charge.getPaid()) {
            endCommandProsess(PaymentType.BANK_CARD, true);
            data.setPaidAmount(charge.getAmount());
        } else {
            endCommandProsess(PaymentType.BANK_CARD, false);
        }

        return data;
    }


    @Override
    @Scheduled(cron = "0 0 5 * * ?")
    public void deleteOrderAfterDateExmiration() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.DATE, -days);
        Date newDate = calendar.getTime();
        List<Order> orderList = orderRepository.deleteOrderByStatusAndCreationDate(newDate);
        orderRepository.deleteAll(orderList);
    }

    @Override
    public List<Product> researchProduct(float maxPrix , float minPrix , String nameProduct, String categorie, String mark, ProductFiltre productFiltre)
    {
        float aux=0;
        if(maxPrix<minPrix)
        {
            aux=maxPrix;
            maxPrix=minPrix;
            minPrix=aux;
        }
        if(maxPrix==0)
        {
            maxPrix = orderRepository.maxProductPrice();
        }
        if (nameProduct==null)
        {
            nameProduct="%";
        }
        if(categorie==null)
        {
            categorie="%";
        }
        if(mark==null)
        {
            mark="%";
        }
        if(productFiltre == ProductFiltre.TOP_RATED)
        { return orderRepository.researchProductTOPRATED(maxPrix,minPrix,"%"+nameProduct+"%",categorie,"%"+mark+"%");}
        if(productFiltre == ProductFiltre.ASCENDING_PRICE)
            return orderRepository.researchProductASCENDINGPRICE(maxPrix,minPrix,"%"+nameProduct+"%",categorie,"%"+mark+"%");
        if(productFiltre == ProductFiltre.DECREASING_PRICE)
            return orderRepository.researchProductDECREASINGPRICE(maxPrix,minPrix,"%"+nameProduct+"%",categorie,"%"+mark+"%");
        if(productFiltre == ProductFiltre.MOST_REQUESTED)
            return orderRepository.researchProductMOSTREQUESTED(maxPrix,minPrix,"%"+nameProduct+"%",categorie,"%"+mark+"%");

        return orderRepository.researchProductNEWARRIVAL(maxPrix, minPrix, "%" + nameProduct + "%", categorie, "%" + mark + "%");
    }


    @Override
    public String validateCommand(String token) throws MessagingException
    {
        String email=parseToken(token);
        System.out.println(email);
        if(orderRepository.findUserByEmail(email)==1)
        {
            User user=userrRepository.findById(sessionService.getUserBySession().getId()).get();
            Order order= orderRepository.BasketExistance(user.getId());
            order.setStatus(StatusOrderType.WAITING_FOR_PAYMENT);
            order.setPayment(PaymentType.CASH_ON_DELIVERY);
            List<String> refList=orderRepository.reflist();
            String referance="";
            do{
                referance=generateRandomNumber(10);
            }while (refList.contains(referance));
            order.setRef(referance);

            List<ProductQuantity> lpq=order.getProductQuantities();
            for (ProductQuantity pq :lpq)
            {
                pq.getProduct().setNumberOfPurchase(pq.getProduct().getNumberOfPurchase()+pq.getQuantity());
                productRepository.save(pq.getProduct());
            }
            orderRepository.save(order);

            return "Your command is successfully confirmed";
        }
        return "Expired Link Token";
    }


    //coded with HS256
    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject("Order Verification To Cash ON Delivery")
                .claim("Email", user.getEmail())
                .signWith(SignatureAlgorithm.HS256, "sfsdgsgCHzqAgJGJYStdpwmoAKPTklmEFyyBOPeytXFBiIHxmpVOfGfEJZrKndJGbBvHFKxWJMOoscracHuHmCBBrvWnRlmaqcVaEpshDGtWlhOnuaLoulhINcrSwxjgfdhoQOBHlBWHHczEKeuAJbJDeOAvxaRVmhziltjjyaWgQuzTQXjomaGGWdXxcAhOoSUSCiQNpfsXTQWbalDevCDpcEXCBGWZkIsPaREvYixXTSCpcGyZfIMRZfJFAlWZ")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .compact();
    }


    //uncoded
    @Override
    public String parseToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey("sfsdgsgCHzqAgJGJYStdpwmoAKPTklmEFyyBOPeytXFBiIHxmpVOfGfEJZrKndJGbBvHFKxWJMOoscracHuHmCBBrvWnRlmaqcVaEpshDGtWlhOnuaLoulhINcrSwxjgfdhoQOBHlBWHHczEKeuAJbJDeOAvxaRVmhziltjjyaWgQuzTQXjomaGGWdXxcAhOoSUSCiQNpfsXTQWbalDevCDpcEXCBGWZkIsPaREvYixXTSCpcGyZfIMRZfJFAlWZ").parseClaimsJws(token);
        return claims.getBody().get("Email", String.class);
    }


    @Override
    public String generateRandomNumber(int n) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    @Override
    public Order deleteBasket() {
    Order order=orderRepository.BasketExistance(sessionService.getUserBySession().getId());
    orderRepository.delete(order);
    return order;
    }

    @Override
    public List<Order> getAllOrdersByUserId() {
        return orderRepository.findOrderByBuyerIdAndStatusIsNotLike(sessionService.getUserBySession().getId(),StatusOrderType.BASKET);
    }


    @Override
    public boolean sessionReteurn() {
        User user=sessionService.getUserBySession();
    return user!=null;
    }

    @Override
    public List<Order> getBestOrdersUser() {
        List<Order> results = orderRepository.usersBestOrders();
        return results.subList(0, Math.min(10, results.size()));
    }
}



