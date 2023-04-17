package tn.workbot.coco_marketplace.services;

import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Request;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.RequestStatus;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupBuyer;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;
import tn.workbot.coco_marketplace.repositories.AgencyDeliveryManRepository;
import tn.workbot.coco_marketplace.repositories.PickupRepository;
import tn.workbot.coco_marketplace.repositories.RequestRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.RequestInterface;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    SessionService sessionService;

    @Override
    public Request addRequest(Request request) {
        return rr.save(request);
    }

    @Override
    public void removeRequest(Long id) {
       Pickup p= rr.retrievePickupbyRequestId(id);
      int nbRequest=p.getNbRequest()-1;
       p.setNbRequest(nbRequest);
       pr.save(p);
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
    public Request assignRequestDeliveryAgencyandDeliverymenandPickup(Request request, Long idPickup, Long idDeliveryMenAgency) {

        //Hadha fel la9i9a bech yetbadel bel variable mt3 el agency eli connecté tawa Session manager
        User user=sessionService.getUserBySession();
        Request request1 = rr.save(request);
        //eli fou9o sessionManger
        User u = pr.UserOfPickup(idPickup);
        Pickup p = pr.findById(idPickup).get();
        int i = pr.countrequest(idPickup);
        i=i+1;
        p.setNbRequest(i);
        pr.save(p);
        AgencyDeliveryMan agencyDeliveryMan = admr.findById(idDeliveryMenAgency).get();
        List<Request> requestList = (List<Request>) rr.findAll();
        Pickup pickup = pr.findById(idPickup).get();
        if (rr.countrequestwithsomedeliverymen(idDeliveryMenAgency, idPickup) < 1) {
            if (pickup.getGovernorate().equals(agencyDeliveryMan.getGovernorate())) {
                request1.setAgencyDeliveryMan(agencyDeliveryMan);
                request1.setAgency(user);
                request1.setPickup(pickup);
                request1.setRequestDate(LocalDateTime.now());

            }
        }
        return rr.save(request1);    }

    @Override
    public Request assignRequestDeliveryMenFreelancerandPickup(Request request, Long idPickup) {
        Request request1 = rr.save(request);
        //eliconnectetawa session id bech yet7at lena fel idSeller
        Pickup p = pr.findById(idPickup).get();
        int i = pr.countrequest(idPickup);
        i=i+1;
        p.setNbRequest(i);
        pr.save(p);
        User user=sessionService.getUserBySession();
        request1.setRequestStatus(RequestStatus.valueOf("PENDING"));
        Pickup pickup = pr.findById(idPickup).get();
        request1.setAgencyDeliveryMan(null);
        request1.setAgency(null);
        request1.setDeliveryman(user);
        request1.setPickup(pickup);
        request1.setRequestDate(LocalDateTime.now());
        user.setPoints(user.getPoints()-p.getPoints());
        ur.save(user);
        return rr.save(request1);
    }
    public String calculateDeliveryTime(Long idPickup, Long idRequest) throws IOException, InterruptedException, ApiException {

        Pickup pickup1 = pr.pickupprettolivred(idPickup);
        Request request1 = rr.findById(idRequest).get();
        if (request1.getRequestStatus().equals(RequestStatus.APPROVED)) {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyDQCUA-GfJipPTO6s9N-cJr7SUHinNMFGY")
                    .build();
            // Get the distance and travel time using the DistanceMatrixApi
            DistanceMatrixApiRequest request = new DistanceMatrixApiRequest(context)
                    .origins(pickup1.getGovernorate())
                    .destinations(pickup1.getStore().getGovernorate())
                    .mode(TravelMode.DRIVING);

            DistanceMatrix matrix = request.await();
            Distance distance = matrix.rows[0].elements[0].distance;
            if ((request1.getDeliveryman() != null && request1.getDeliveryman().getGear() != null && request1.getDeliveryman().getGear().equals("CAR"))
                    || (request1.getAgencyDeliveryMan() != null && request1.getAgencyDeliveryMan().getGearv() != null && request1.getAgencyDeliveryMan().getGearv().equals("CAR"))) {
                double averageSpeed = 80.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return String.format("%.2f", travelTimeInHours);
            } else if ((request1.getDeliveryman() != null && request1.getDeliveryman().getGear() != null && request1.getDeliveryman().getGear().equals("BIKE"))
                    || (request1.getAgencyDeliveryMan() != null && request1.getAgencyDeliveryMan().getGearv() != null && request1.getAgencyDeliveryMan().getGearv().equals("BIKE"))) {
                double averageSpeed = 10.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return String.format("%.2f", travelTimeInHours);
            } else if ((request1.getDeliveryman() != null && request1.getDeliveryman().getGear() != null && request1.getDeliveryman().getGear().equals("MOTO"))
                    || (request1.getAgencyDeliveryMan() != null && request1.getAgencyDeliveryMan().getGearv() != null && request1.getAgencyDeliveryMan().getGearv().equals("MOTO"))) {
                double averageSpeed = 30.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return String.format("%.2f", travelTimeInHours);
            } else {
                // Calculate the estimated travel time based on the gear information
                double averageSpeed = 60.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return String.format("%.2f", travelTimeInHours);
            }

        }
        return null;

    }

    @Override
    public Request assignRequesttoseller(Long idRequest, String status, Long idPickup) throws IOException, InterruptedException, ApiException {
        Request request = rr.findById(idRequest).get();
        Pickup pickup=pr.findById(idPickup).get();
        //session manager el idmt3 seller bech njibo el id mt3 el pickup wel request mel url wel status yda5elha houwa
        User seller=sessionService.getUserBySession();
        List<Request> requestsPending = new ArrayList<>();
        //bech njib el request eli saro el kol 3al pickup haki bech kif ya5tar anahi bech ylivreha ytsetaw e yetsetaw el ba9i reject auto
        requestsPending.addAll(rr.verifier2(idPickup));
        request.setRequestStatus(RequestStatus.valueOf(status));
        request.setSeller(seller);
        rr.save(request);
        if (rr.verifier(idPickup) == 1) {
            for (Request r : requestsPending) {
                r.setRequestStatus(RequestStatus.valueOf("REJECTED"));
                r.setRequestDate(LocalDateTime.now());
            }
        }
        request.setRequestDate(LocalDateTime.now());
        request.setRequestStatus(RequestStatus.valueOf(status));
        request.setSeller(seller);
        String deliverytime=calculateDeliveryTime(idPickup,idRequest);
        pickup.setDeliveryTimeInHoursBuyer(deliverytime);
        pickup.setStatusPickupSeller(StatusPickupSeller.valueOf("ASSIGNED"));
        pickup.setStatusPickupBuyer(StatusPickupBuyer.valueOf("ASSIGNED"));
        pr.save(pickup);
        return rr.save(request);
    }

    @Override
    public List<Request> retrieveRequestBySeller() {
        //session manager variable idseller
        User u=sessionService.getUserBySession();
        return rr.retrieveRequestBystore(u.getId());
    }

    @Override
    public List<Request> retrieveRequestByPickup(Long idPickup) {
        //na5o idPickup mel URL
        return rr.retrieveRequestByPickup(idPickup);
    }

    @Override
    public List<Request> RetrieveRequestByAgency() {
        //session manager variable idseller
        User u=sessionService.getUserBySession();
        return rr.RetrieveRequestByAgency(u.getId());
    }

    @Override
    public List<Request> RetrieveRequestByFreelancer() {
        //session manager variable idseller
        User u=sessionService.getUserBySession();
        return rr.RetrieveRequestByFreelancer(u.getId());
    }

    @Override
    public User RetrieveFreelancerDeliveryrByRequest(Long idRequest) {
        User u=sessionService.getUserBySession();
        return rr.retrieveFreelancerDeliveryByRequestAndStore(u.getId(),idRequest);
    }

    @Override
    public int countRequestTotalForAgencyPending() {
        User u=sessionService.getUserBySession();
        return rr.countRequestTotalOfAgency(u.getId());
    }

    @Override
    public int countRequestApprovedForAgency() {
        User u=sessionService.getUserBySession();
        return rr.countRequestApprovedForAgency(u.getId());
    }

    @Override
    public int countRequestRejectForAgency() {
        User u=sessionService.getUserBySession();
        return rr.countRequestRejectForAgency(u.getId());
    }

    @Override
    public List<Request> retrieveRequestApprovedOfPickupFreelancer() {
        User u=sessionService.getUserBySession();
        return rr.RetrieveRequestApprovedByFreelancer(u.getId());
    }

    @Override
    public List<Request> retrieveRequestApprovedOfPickupAgency() {
        User u=sessionService.getUserBySession();
        return rr.RetrieveRequestApprovedByAgency(u.getId());
    }

    @Override
    public int countRequestByPickup(Long idPickup) {
        return pr.countrequest(idPickup);
    }


    @Scheduled(cron = "0 0 11 * * *")
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
                    if (r.getAgency() != null) {
                        mailMessage.setTo(r.getAgency().getEmail());
                        mailMessage.setSubject("Request Status");
                        Context context = new Context();
                        context.setVariable("name", "Congradulations your request is accepted");
                        String emailContent = templateEngine.process("emailRequest", context);
                        mailMessage.setText(emailContent, true);
                        javaMailSender.send(message);
                    }


                    if (r.getDeliveryman() != null) {
                        mailMessage.setTo(r.getDeliveryman().getEmail());
                        mailMessage.setSubject("Request Status");
                        Context contextt = new Context();
                        contextt.setVariable("name", "Congradulations your request is accepted");
                        String emailContentt = templateEngine.process("emailRequest", contextt);
                        mailMessage.setText(emailContentt, true);
                        javaMailSender.send(message);
                    }

                }
            } else if (r.getRequestStatus().equals(RequestStatus.REJECTED)) {
                LocalDateTime requestDate = r.getRequestDate();

                if (requestDate.isAfter(now.minusMinutes(1)) && requestDate.isBefore(now.plusMinutes(1))) {
                    MimeMessage message = javaMailSender.createMimeMessage();
                    MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
                    if (r.getAgency() != null) {
                        mailMessage.setTo(r.getAgency().getEmail());
                        mailMessage.setSubject("Request Status");
                        Context context = new Context();
                        context.setVariable("name", "Sorry your request is Rejected");
                        String emailContent = templateEngine.process("emailRequest", context);
                        mailMessage.setText(emailContent, true);
                        javaMailSender.send(message);
                    }


                    if (r.getDeliveryman() != null) {
                        mailMessage.setTo(r.getDeliveryman().getEmail());
                        mailMessage.setSubject("Request Status");

                        Context contextt = new Context();
                        contextt.setVariable("name","Sorry your request is Rejected");
                        String emailContentt = templateEngine.process("emailRequest", contextt);
                        mailMessage.setText(emailContentt, true);
                        javaMailSender.send(message);
                    }

                }
            }
        }
    }


}