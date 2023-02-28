package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Request;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.RequestStatus;
import tn.workbot.coco_marketplace.repositories.AgencyDeliveryManRepository;
import tn.workbot.coco_marketplace.repositories.PickupRepository;
import tn.workbot.coco_marketplace.repositories.RequestRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.RequestInterface;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RequestService implements RequestInterface {
    @Autowired
    RequestRepository rr;
    @Autowired
    PickupRepository pr;
    @Autowired
    UserrRepository ur;
    @Autowired
    AgencyDeliveryManRepository admr;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Request addRequest(Request request) {
        return rr.save(request);
    }

    @Override
    public void removeRequest(Long id) {
        rr.deleteById(id);
    }

    @Override
    public Request RetrieveRequest(Long id) {
        if (rr.findById(id).isPresent())
            return rr.findById(id).get();
        return new Request();
    }

    @Override
    public List<Request> RetrieveRequests() {
        return (List<Request>) rr.findAll();
    }

    @Override
    public Request updateRequest(Request request) {
        return rr.save(request);
    }

    @Override
    public Request assignRequestDeliveryAgencyandDeliverymenandPickup(Request request, Long idDeliveryAgency, Long idPickup, Long idDeliveryMenAgency) {

        //Hadha fel la9i9a bech yetbadel bel variable mt3 el agency eli connecté tawa Session manager
        User user = ur.findById(idDeliveryAgency).get();
        User u=pr.UserOfPickup(idPickup);
        Pickup p=pr.findById(idPickup).get();
        int i=pr.countrequest(u.getId());
        i=i+1;
        p.setNbRequest(i);
        pr.save(p);
        AgencyDeliveryMan agencyDeliveryMan = admr.findById(idDeliveryMenAgency).get();
        List<Request> requestList = (List<Request>) rr.findAll();
        Pickup pickup = pr.findById(idPickup).get();
        if (rr.countrequestwithsomedeliverymen(idDeliveryMenAgency, idPickup) < 1) {
            if (pickup.getGovernorate().equals(agencyDeliveryMan.getGovernorate())) {
                Request request1 = rr.save(request);
                request1.setAgencyDeliveryMan(agencyDeliveryMan);
                request1.setAgency(user);
                request1.setPickup(pickup);
                request1.setRequestDate(LocalDateTime.now());
                return rr.save(request);
            }
        }
        return null;
    }

    @Override
    public Request assignRequestDeliveryMenFreelancerandPickup(Request request, Long idDeliveryMenFreelancer, Long idPickup) {
        Request request1 = rr.save(request);
        //eliconnectetawa session id bech yet7at lena fel idSeller
        User u=pr.UserOfPickup(idPickup);
        Pickup p=pr.findById(idPickup).get();
        int i=pr.countrequest(u.getId());
        i=i+1;
        p.setNbRequest(i);
        pr.save(p);
        User user = ur.findById(idDeliveryMenFreelancer).get();
        request1.setRequestStatus(RequestStatus.valueOf("PENDING"));
        Pickup pickup = pr.findById(idPickup).get();
        request1.setDeliveryman(user);
        request1.setPickup(pickup);
        request1.setRequestDate(LocalDateTime.now());
        return rr.save(request);
    }

    @Override
    public Request assignRequesttoseller(Long idRequest, Long idSeller, String status, Long idPickup) {
        Request request = rr.findById(idRequest).get();
        //session manager el idmt3 seller bech njibo el id mt3 el pickup wel request mel url wel status yda5elha houwa
        User seller = ur.findById(idSeller).get();
        List<Request> requestsPending = new ArrayList<>();
        //bech njib el request eli saro el kol 3al pickup haki bech kif ya5tar anahi bech ylivreha ytsetaw e yetsetaw el ba9i reject auto
        requestsPending.addAll(rr.verifier2(idPickup));
        request.setRequestStatus(RequestStatus.valueOf(status));
        request.setSeller(seller);
        rr.save(request);
        if (rr.verifier(idPickup) == 1) {
            for (Request r : requestsPending) {
                r.setRequestStatus(RequestStatus.valueOf("REJECTED"));
            }
        }
        request.setRequestDate(LocalDateTime.now());
        request.setRequestStatus(RequestStatus.valueOf(status));
        request.setSeller(seller);
        return rr.save(request);
    }

    @Override
    public List<Request> retrieveRequestBySeller() {
        //session manager variable idseller
        return rr.retrieveRequestBystore(2L);
    }

    @Override
    public List<Request> retrieveRequestByPickup(Long idPickup) {
        //na5o idPickup mel URL
        return rr.retrieveRequestByPickup(idPickup);
    }

    @Scheduled(cron = "*/60 * * * * *")
    public void sendMailToApprovedAndRejectedRequestWithTimeContrainte() throws MessagingException {
        System.out.println("test");
        List<Request> requests = (List<Request>) rr.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Request r : requests) {
            if (r.getRequestStatus().equals(RequestStatus.APPROVED)) {
                LocalDateTime requestDate = r.getRequestDate();

                if (requestDate.isAfter(now.minusMinutes(1)) && requestDate.isBefore(now.plusMinutes(1))) {
                    MimeMessage message = javaMailSender.createMimeMessage();
                    MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
                    mailMessage.setTo(r.getDeliveryman().getEmail());
                    mailMessage.setSubject("Request Status");
                    Context context = new Context();
                    String emailContent = templateEngine.process("emailRequest", context);
                    mailMessage.setText(emailContent, true);
                    javaMailSender.send(message);
                }
            } else if (r.getRequestStatus().equals(RequestStatus.REJECTED)) {
                LocalDateTime requestDate = r.getRequestDate();

                if (requestDate.isAfter(now.minusMinutes(1)) && requestDate.isBefore(now.plusMinutes(1))) {
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(r.getDeliveryman().getEmail());
                    mailMessage.setSubject("Request Status");
                    mailMessage.setText("Rejected");
                    javaMailSender.send(mailMessage);
                } else if (r.getRequestStatus().equals(RequestStatus.PENDING)) {
                    LocalDateTime requestDate1 = r.getRequestDate();

                    if (requestDate1.isAfter(now.minusMinutes(1)) && requestDate1.isBefore(now.plusMinutes(1))) {
                        SimpleMailMessage mailMessage = new SimpleMailMessage();
                        mailMessage.setTo(r.getDeliveryman().getEmail());
                        mailMessage.setSubject("Request Status");
                        mailMessage.setText("Rejected");
                        javaMailSender.send(mailMessage);
                    }
                }
            }
        }
    }


}
