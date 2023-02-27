package tn.workbot.coco_marketplace.services;

import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.*;
import tn.workbot.coco_marketplace.repositories.*;
import tn.workbot.coco_marketplace.services.interfaces.PickupIService;

import javax.swing.text.Position;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PickupService implements PickupIService {
    @Autowired
    PickupRepository pr;
    @Autowired
    OrderRepository or;
    @Autowired
    StoreRepository sr;
    @Autowired
    UserrRepository ur;
    @Autowired
    AgencyBranchRepository abr;
    @Autowired
    RequestRepository rr;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Pickup addPickup(Pickup pickup) {
        Random random = new Random(); //java.util.Random
        pickup.setStatusPickupSeller(StatusPickupSeller.valueOf("PICKED"));
        pickup.setStatusPickupBuyer(StatusPickupBuyer.valueOf("PLACED"));
        int randomNumber = random.nextInt(9000) + 1000;  // generates a random number between 1000 and 9999
        String prefix = "216";
        String code = prefix + randomNumber;
        List<Pickup> pickups = (List<Pickup>) pr.findAll();
        for (Pickup p : pickups) {
            if (p.getCodePickup() != code) {
                pickup.setCodePickup(code);
            } else {
                int randomNumber1 = random.nextInt(100) + 100;
                String code1 = prefix + randomNumber + randomNumber1;
                pickup.setCodePickup(code1);
            }
        }
        pickup.setCodePickup(code);
        pickup.setDateCreationPickup(LocalDateTime.now());
        return pr.save(pickup);
    }
    @Transactional
    @Override
    public void removePickup(Long id) {
        Pickup pickup = pr.findById(id).orElseThrow(() -> new NotFoundException("Pickup not found"));
        String PICKED = "PICKED";
        if (!pickup.getStatusPickupSeller().equals(StatusPickupSeller.PICKED)) {
            throw new IllegalStateException("Cannot remove pickup with status other than PICKED");
        }
        pr.deleteById(id);
    }

    @Override
    public Pickup RetrievePickup(Long id) {
        if (pr.findById(id).isPresent())
            return pr.findById(id).get();
        return new Pickup();
    }

    @Override
    public List<Pickup> RetrievePickups() {
        return (List<Pickup>) pr.findAll();
    }

    @Override
    public Pickup updatePickup(Pickup pickup) {
        return pr.save(pickup);
    }/**/

    @Override
    public List<Pickup> RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen(Long id) {
        return null;
    }

    @Override
    public Pickup AssignPickupByOder(Pickup pickup, Long id) {
        Pickup p = pr.save(pickup);
        Order order = or.findById(id).get();
        p.setOrder(order);
        return pr.save(pickup);
    }

    @Override
    public List<Pickup> RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer() {
        //HADHI session variable
        User u = ur.findById(3L).get();
        List<Store> store = (List<Store>) sr.findAll();
        List<Store> storesInSameGovernorate = sr.findByGovernorate(u.getGovernorate());
        List<Pickup> pickups = new ArrayList<>();
        //ELI 3ANDHOUM BIKE MATODHA7RELHOUM KEN EL PICKUPS ELI BECH TTLVRA FEN HOUMA W STORE YABDA FARD GOVERNORATE
        if ((u.getGear().equals("BIKE")) || (u.getGear().equals("MOTO"))) {
            for (Store s : store) {
                if (u.getGovernorate().equals(s.getGovernorate())) {
                    return pr.findByGovernorate(s.getGovernorate());
                }
            }
        } else if (u.getGear().equals("CAR")) {
            for (Store storee : storesInSameGovernorate) {
                pickups.addAll(storee.getPickups());
            }

            return pickups;
        }
        return Collections.emptyList();
    }


    @Override
    public List<Pickup> RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat() {
        //sessionManager Variable
        User u = ur.findById(4L).get();
        List<Store> stores = sr.findAll();
        List<AgencyBranch> agencyBranches = new ArrayList<>();
        List<AgencyDeliveryMan> agencyDeliveryManArrayList = new ArrayList<>();
        ArrayList<Pickup> store = new ArrayList<>();
        agencyBranches.addAll(u.getAgencyBranches());
        for (AgencyBranch ab : agencyBranches) {
            for (Store s : stores) {
                if (s.getGovernorate().equals(ab.getGovernorate())) {
                    store.addAll(s.getPickups());
                }
            }
        }
        return store;
    }

    @Override
    public Pickup AssignPickupByStoreAndOrder(Pickup pickup,Long id) {
        //Variable Of Session Manager
        Store store2=pr.storeoforder(id,1L);
        Store store3=sr.findById(1L).get();
        Pickup pickup1 = pr.save(pickup);
        pr.countstoreorder(id);
        System.out.println(pr.countstoreorder(id));
        if(pr.countstoreorder(id)>1) {
            Random random = new Random(); //java.util.Random
            pickup1.setStatusPickupSeller(StatusPickupSeller.valueOf("PICKED"));
            pickup1.setStatusPickupBuyer(StatusPickupBuyer.valueOf("PLACED"));
            int randomNumber = random.nextInt(9000) + 1000;  // generates a random number between 1000 and 9999
            String prefix = "216";
            String code = prefix + randomNumber;
            List<Pickup> pickups = (List<Pickup>) pr.findAll();
            //ken el code el random mawjoud y3awed yrandom code a5er hhhh
            for (Pickup p : pickups) {
                if (p.getCodePickup() != code) {
                    pickup1.setCodePickup(code);
                } else {
                    int randomNumber1 = random.nextInt(100) + 100;
                    String code1 = prefix + randomNumber + randomNumber1;
                    pickup1.setCodePickup(code1);
                }
            }
            Order order = or.findById(id).get();
            pickup1.setOrder(order);
            pickup1.setCodePickup(code);
            pickup1.setDateCreationPickup(LocalDateTime.now());
            pickup1.setOrderOfTheSomeSeller(false);
            pickup1.setStore(store2);
            pickup1.setGovernorate(order.getShipping().getGovernorate());
            pickup1.setCity(order.getShipping().getCity());
            List<Product> products=pr.productoforder(id,1L);
            pickup1.setSum(order.getSum());
            if(order.getPayment().equals(PaymentType.BANK_CARD)){
                    pickup1.setPayed(true);
            }
            else {
                pickup1.setPayed(false);
            }
            return pr.save(pickup1);

        }
        else {
            Random random = new Random(); //java.util.Random
            pickup1.setStatusPickupSeller(StatusPickupSeller.valueOf("PICKED"));
            pickup1.setStatusPickupBuyer(StatusPickupBuyer.valueOf("PLACED"));
            int randomNumber = random.nextInt(9000) + 1000;  // generates a random number between 1000 and 9999
            String prefix = "216";
            String code = prefix + randomNumber;
            List<Pickup> pickups = (List<Pickup>) pr.findAll();
            for (Pickup p : pickups) {
                if (p.getCodePickup() != code) {
                    pickup1.setCodePickup(code);
                } else {
                    int randomNumber1 = random.nextInt(100) + 100;
                    String code1 = prefix + randomNumber + randomNumber1;
                    pickup1.setCodePickup(code1);
                }
            }
            Order order = or.findById(id).get();
            pickup1.setOrder(order);
            pickup1.setCodePickup(code);
            pickup1.setDateCreationPickup(LocalDateTime.now());
            pickup1.setOrderOfTheSomeSeller(true);
            pickup1.setStore(store2);
            pr.save(pickup1);
            pickup1.setGovernorate(order.getShipping().getGovernorate());
            pickup1.setCity(order.getShipping().getCity());
            List<Product> products=pr.productoforder(id,1L);
            float totalPrice = 0;
            for (Product product:products) {
                totalPrice += product.getProductPrice();
            }
            float sum = totalPrice;
            pickup1.setSum(sum);

            if(order.getPayment().equals(PaymentType.BANK_CARD)){
                pickup1.setPayed(true);
            }
            else {
                pickup1.setPayed(false);
            }
            return pr.save(pickup1);
        }
    }

    @Override
    public Pickup ModifyStatusOfPickupByDelivery(String Status, Long idPickup) {
        Pickup pickup = pr.findById(idPickup).get();
        if (Status.equals("SHIPPED")) {
            pickup.setStatusPickupBuyer(StatusPickupBuyer.valueOf("SHIPPED"));
            pickup.setStatusPickupSeller(StatusPickupSeller.valueOf("ONTHEWAY"));
            pr.save(pickup);
        } else if (Status.equals("DELIVERED")) {
            pickup.setStatusPickupBuyer(StatusPickupBuyer.valueOf("DELIVERED"));
            pickup.setStatusPickupSeller(StatusPickupSeller.valueOf("DELIVERED"));
            pr.save(pickup);
        } else if (Status.equals("RETURN")) {
            pickup.setStatusPickupBuyer(StatusPickupBuyer.valueOf("RETURN"));
            pickup.setStatusPickupSeller(StatusPickupSeller.valueOf("RETURN"));
            pr.save(pickup);
        } else {
            pickup.setStatusPickupBuyer(StatusPickupBuyer.valueOf("REFUNDED"));
            pickup.setStatusPickupSeller(StatusPickupSeller.valueOf("REFUNDED"));
            pr.save(pickup);
        }
        return null;
    }

    @Override
    public Duration calculateDeliveryTime(Long idPickup) throws IOException, InterruptedException, ApiException {

        Pickup pickup1=pr.pickupprettolivred(idPickup);
        Request request1=  rr.findById(pickup1.getId()).get();
        if(request1.getRequestStatus().equals(RequestStatus.APPROVED)) {
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
                    || (request1.getAgencyDeliveryMan() != null && request1.getAgencyDeliveryMan().getGearv() != null && request1.getAgencyDeliveryMan().getGearv().equals("CAR"))){
                double averageSpeed = 60.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return Duration.ofHours((long) travelTimeInHours);
            }
            else if ((request1.getDeliveryman() != null && request1.getDeliveryman().getGear() != null && request1.getDeliveryman().getGear().equals("BIKE"))
                    || (request1.getAgencyDeliveryMan() != null && request1.getAgencyDeliveryMan().getGearv() != null && request1.getAgencyDeliveryMan().getGearv().equals("BIKE"))){
                double averageSpeed = 10.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return Duration.ofHours((long) travelTimeInHours);
            }
            else if ((request1.getDeliveryman() != null && request1.getDeliveryman().getGear() != null && request1.getDeliveryman().getGear().equals("MOTO"))
                    || (request1.getAgencyDeliveryMan() != null && request1.getAgencyDeliveryMan().getGearv() != null && request1.getAgencyDeliveryMan().getGearv().equals("MOTO"))){
                double averageSpeed = 30.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return Duration.ofHours((long) travelTimeInHours);
            }
            else {
                // Calculate the estimated travel time based on the gear information
                double averageSpeed = 60.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return Duration.ofHours((long) travelTimeInHours);
            }

        }
        return null;

    }

    @Override
    public int test(Long id) {
        User user=ur.findById(id).get();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("subject");
        mailMessage.setText("body");
        javaMailSender.send(mailMessage);
        return 1;
    }

    @Override
    public Pickup trakingbyseller(String codePickup) {
        //session varaible
        User u=ur.findById(1L).get();
        return pr.trakingS(codePickup,u.getId()) ;
    }

    @Override
    public Pickup trakingbybuyer(String codePickup,Long idBuyer) {
        //idBuyer mel session manager
        return pr.trakingB(codePickup,idBuyer);
    }

}
