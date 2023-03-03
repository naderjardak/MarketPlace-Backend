package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tn.workbot.coco_marketplace.Api.OrderMailSenderService;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.SupplierRequest;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.SupplierRequestStatus;
import tn.workbot.coco_marketplace.repositories.SupplierRequestRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.SupplierRequestInterface;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

@Service
public class SupplierRequestService implements SupplierRequestInterface {

    @Autowired
    SupplierRequestRepository supplierRequestRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserrRepository userrRepository;
    @Autowired
    OrderMailSenderService mailSenderService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;


    @Override
    public SupplierRequest create(SupplierRequest s, Long productId) throws MessagingException {
        User user = userrRepository.findById(1L).get();
        Product product = productService.getById(productId);
        s.setProduct(product);
        s.setSupplier(user);
        Date currentDate = new Date(); // date actuelle
        int daysToAdd = 1; // nombre de jours à ajouter

        // Ajouter des jours en millisecondes
        long newTimeInMillis = currentDate.getTime() + (daysToAdd * 24 * 60 * 60 * 1000);
        boolean diff = s.getDeliveryDateTime().before(new Date(newTimeInMillis));
        if (product.isAutomaticRequestAcceptance() && product.getUnityPriceFromSupplier() != 0 && s.getUnityPrice() <= product.getUnityPriceFromSupplier() && s.getQuantity() == product.getDeliveryQuantity() && diff) {
            s.setRequestStatus(SupplierRequestStatus.ACCEPTED);
            product.setUnityPriceFromSupplier(s.getUnityPrice());
            //-1 quand la commande est confirmé et pas encore livré
            product.setQuantity(-1);
        } else
            s.setRequestStatus(SupplierRequestStatus.WAITING_FOR_VALIDATION);
        supplierRequestRepository.save(s);
        productService.update(product);
        String st = "Good Morning\n \nthe request of " + user.getBrandName() + "on " + product.getName() + " was accepted\nhere are some details : \n"
                + "Unity Price : " + s.getUnityPrice() + " Quantity : " + s.getQuantity() + " Delivery Date : " + s.getDeliveryDateTime() + "\n \n Best Regards";
//        mailSenderService.sendEmail(product.getStore().getSeller().getEmail(),"Supplier Request Accepted Automatically",st);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        mailMessage.setTo(product.getStore().getSeller().getEmail());
        mailMessage.setSubject("Supplier Request Accepted Automatically");
        Context context = new Context();
        context.setVariable("messageContent", st);
        String emailContent = templateEngine.process("AcceptedSupplierRequestEmail", context);
        mailMessage.setText(emailContent, true);
        javaMailSender.send(message);
        return s;
    }

    @Override
    public SupplierRequest update(SupplierRequest s) {
        return supplierRequestRepository.save(s);
    }

    @Override
    public List<SupplierRequest> retrieveAll() {
        return supplierRequestRepository.findAll();
    }

    @Override
    public SupplierRequest getById(Long id) {
        if (supplierRequestRepository.findById(id).isPresent()) {
            return supplierRequestRepository.findById(id).get();
        }
        return new SupplierRequest();
    }

    @Override
    public void deleteById(Long id) throws Exception {
        SupplierRequest request = supplierRequestRepository.findById(id).get();
        if (request.getRequestStatus().equals(SupplierRequestStatus.WAITING_FOR_VALIDATION))
            supplierRequestRepository.delete(request);
        else throw new Exception("Error, you request is already treated, you can't delete it");
    }

    @Override
    public List<Product> retriveProductsOutOfStock() {
        return productService.retrieveAll().stream().filter(p -> p.getQuantity() == 0).toList();
    }

    @Override
    public List<SupplierRequest> retriveRequestsByProduct(Long idProduct) {
        return productService.getById(idProduct).getSupplierRequests()
                .stream().filter(s -> s.getRequestStatus().equals(SupplierRequestStatus.WAITING_FOR_VALIDATION)).toList();

    }

    @Override
    public void accpetRequestBySeller(Long supplierRequestId) {
        if (supplierRequestRepository.findById(supplierRequestId).isPresent()) {
            SupplierRequest supplierRequest = supplierRequestRepository.findById(supplierRequestId).get();
            List<SupplierRequest> supplierRequests = supplierRequest.getProduct().getSupplierRequests();
            for (SupplierRequest s : supplierRequests) {
                if (s.equals(supplierRequest)) {
                    s.setRequestStatus(SupplierRequestStatus.ACCEPTED);
                    s.getProduct().setUnityPriceFromSupplier(s.getUnityPrice());
                    //-1 quand la commande est confirmé et pas encore livré
                    s.getProduct().setQuantity(-1);
                } else {
                    s.setRequestStatus(SupplierRequestStatus.REJECTED);
                }
                this.update(s);
            }
        }

    }

    @Override
    public void confirmRequestDelivery(Long supplierRequestId) {
        if (supplierRequestRepository.findById(supplierRequestId).isPresent()) {
            SupplierRequest supplierRequest = supplierRequestRepository.findById(supplierRequestId).get();
            Product product = supplierRequest.getProduct();
            product.setUnityPriceFromSupplier(supplierRequest.getUnityPrice());
            product.setQuantity(supplierRequest.getQuantity());
            productService.update(product);
            supplierRequest.setRequestStatus(SupplierRequestStatus.DELIVERED);
            supplierRequestRepository.save(supplierRequest);
        }
    }
}
