package tn.workbot.coco_marketplace.services;

import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.workbot.coco_marketplace.Api.OpenWeatherMapClient;
import tn.workbot.coco_marketplace.Api.PickupTwilio;
import tn.workbot.coco_marketplace.Api.ScraperEssence;
import tn.workbot.coco_marketplace.Dto.auth.NewPassword;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.*;
import tn.workbot.coco_marketplace.repositories.*;
import tn.workbot.coco_marketplace.services.interfaces.PickupIService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DecimalFormat;

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
    @Autowired
    OpenWeatherMapClient openWeatherMapClient;
    @Autowired
    PickupTwilio pickupTwilio;
    @Autowired
    ScraperEssence se;


    @Override
    public Pickup addPickup(Pickup pickup) {
        Random random = new Random(); //java.util.Random
        pickup.setStatusPickupSeller(StatusPickupSeller.valueOf("PICKED"));
        pickup.setStatusPickupBuyer(StatusPickupBuyer.valueOf("PLACED"));
        int randomNumber = random.nextInt(9000) + 1000;  // generates a random number betweeen 1000 and 9999
        String prefix = "216";
        String code = prefix + randomNumber;
        List<Pickup> pickups = (List<Pickup>) pr.findAll();
        for (Pickup p : pickups) {
            if (p.getCodePickup() != code) {
                pickup.setCodePickup(code);
            } else {
                int randomNumber1 = random.nextInt(100) + 100;
                String code1 = prefix + randomNumber + randomNumber1;
                pickup.setCodePickup(code1);//
            }
        }
        pickup.setCodePickup(code);
        pickup.setDateCreationPickup(LocalDateTime.now());
        return pr.save(pickup);
    }

    @Transactional
    @Override
    public void removePickup(Long id) {
        Pickup pickup = pr.findById(id).get();
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
        double a = openWeatherMapClient.getWeather(u.getGovernorate());
        List<Store> store = (List<Store>) sr.findAll();
        List<Store> storesInSameGovernorate = sr.findByGovernorate(u.getGovernorate());

        List<Pickup> pickups = new ArrayList<>();
        //ELI 3ANDHOUM BIKE MATODHA7RELHOUM KEN EL PICKUPS ELI BECH TTLVRA FEN HOUMA W STORE YABDA FARD GOVERNORATE
        if (a > 2) {
            if ((u.getGear().equals("BIKE")) || (u.getGear().equals("MOTO"))) {
                for (Store s : store) {
                    if (u.getGovernorate().equals(s.getGovernorate())) {
                        return pr.findByGovernorate(s.getGovernorate());
                    }
                }
            }
        }
        if (u.getGear().equals("CAR")) {
            for (Store storee : storesInSameGovernorate) {

                pickups.addAll(storee.getPickups());
            }
        }
        return pickups;
    }



    @Override
    public List<Pickup> RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat() {
        //sessionManager Variable
        User u = ur.findById(4L).get();
        List<Store> stores = sr.findAll();
        List<AgencyBranch> agencyBranches = new ArrayList<>();
        Set<Pickup> pickups = new HashSet<>(); // use Set instead of ArrayList
        agencyBranches.addAll(u.getAgencyBranches());
        for (AgencyBranch ab : agencyBranches) {
            for (Store s : stores) {
                if (s.getGovernorate().equals(ab.getGovernorate())) {
                    pickups.addAll(s.getPickups()); // use addAll instead of add
                }
            }
        }
        return new ArrayList<>(pickups); // convert Set to ArrayList
    }

    @Override
    public Pickup AssignPickupByStoreAndOrder(Pickup pickup, Long id, Long IdSotre) {
        //Variable Of Session Manager
        Store store2 = pr.storeoforder(IdSotre, id, 1L);
        Store storeer = sr.findById(IdSotre).get();
        Pickup pickup1 = pr.save(pickup);
        Order order = or.findById(id).get();
        List<Pickup> pickups = (List<Pickup>) pr.findAll();
        Random random = new Random(); //java.util.Random
        int randomNumber = random.nextInt(9000) + 1000;  // generates a random number between 1000 and 9999
        String prefix = "216";
        String code = prefix + randomNumber;
        pr.countstoreorder(id);
        float totalPrice = 0;
        List<Product> productList = pr.productOfTheStoreById(IdSotre, id, 1L);
        int storeofsomeUser = pr.countstoreofproductinorderOfSomeseller(IdSotre, id, 1L);
        if (pr.countstoreorder(id) == 1) {

            pickup1.setStatusPickupSeller(StatusPickupSeller.valueOf("PICKED"));
            pickup1.setStatusPickupBuyer(StatusPickupBuyer.valueOf("PLACED"));
            ///
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
            pickup1.setCodePickup(code);
            //
            pickup1.setOrder(order);
            pickup1.setDateCreationPickup(LocalDateTime.now());
            // pickup1.setOrderOfTheSomeSeller(true);
            pickup1.setStore(storeer);
            pickup1.setSum(order.getSum());
            if (order.getPayment().equals(PaymentType.BANK_CARD)) {
                pickup1.setPayed(true);
            } else {
                pickup1.setPayed(false);
            }
        } else {
            if (storeofsomeUser >= 1) {
                for (Product p : productList) {
                    totalPrice += p.getProductPrice();
                    pickup1.setStore(storeer);
                    pickup1.setOrder(order);

                    //ken el code el random mawjoud y3awed yrandom code a5er hhhh
                    for (Pickup pc : pickups) {
                        if (pc.getCodePickup() != code) {
                            pickup1.setCodePickup(code);
                        } else {
                            int randomNumber1 = random.nextInt(100) + 100;
                            String code1 = prefix + randomNumber + randomNumber1;
                            pickup1.setCodePickup(code1);
                        }
                    }
                    pickup1.setCodePickup(code);
                    pickup1.setSum(totalPrice);
                }

            }/* else {

                pickup1.setStatusPickupSeller(StatusPickupSeller.valueOf("PICKED"));
                pickup1.setStatusPickupBuyer(StatusPickupBuyer.valueOf("PLACED"));

                for (Pickup p : pickups) {
                    if (p.getCodePickup() != code) {
                        pickup1.setCodePickup(code);
                    } else {
                        int randomNumber1 = random.nextInt(100) + 100;
                        String code1 = prefix + randomNumber + randomNumber1;
                        pickup1.setCodePickup(code1);
                    }
                }
                pickup1.setOrder(order);
                pickup1.setCodePickup(code);
                pickup1.setDateCreationPickup(LocalDateTime.now());
                pickup1.setOrderOfTheSomeSeller(false);
                pickup1.setStore(store2);
                pr.save(pickup1);
                List<Product> products = pr.productoforder(id, 1L);


                for (Product product : products) {
                    totalPrice += product.getProductPrice();
                }
                float sum = totalPrice;
                pickup1.setSum(sum);

                if (order.getPayment().equals(PaymentType.BANK_CARD)) {
                    pickup1.setPayed(true);
                } else {
                    pickup1.setPayed(false);
                }
            }
            */
        }
        return pr.save(pickup1);
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

        Pickup pickup1 = pr.pickupprettolivred(idPickup);
        Request request1 = pr.Requestprettolivred(idPickup);
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
                double averageSpeed = 60.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return Duration.ofHours((long) travelTimeInHours);
            } else if ((request1.getDeliveryman() != null && request1.getDeliveryman().getGear() != null && request1.getDeliveryman().getGear().equals("BIKE"))
                    || (request1.getAgencyDeliveryMan() != null && request1.getAgencyDeliveryMan().getGearv() != null && request1.getAgencyDeliveryMan().getGearv().equals("BIKE"))) {
                double averageSpeed = 10.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return Duration.ofHours((long) travelTimeInHours);
            } else if ((request1.getDeliveryman() != null && request1.getDeliveryman().getGear() != null && request1.getDeliveryman().getGear().equals("MOTO"))
                    || (request1.getAgencyDeliveryMan() != null && request1.getAgencyDeliveryMan().getGearv() != null && request1.getAgencyDeliveryMan().getGearv().equals("MOTO"))) {
                double averageSpeed = 30.0; // km/h
                double distanceInKm = distance.inMeters / 1000.0;
                double travelTimeInHours = distanceInKm / averageSpeed;
                // Return the estimated delivery time as a Duration object
                return Duration.ofHours((long) travelTimeInHours);
            } else {
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
        User user = ur.findById(id).get();
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
        User u = ur.findById(1L).get();
        return pr.trakingS(codePickup, u.getId());
    }

    @Override
    public Pickup trakingbybuyer(String codePickup, Long idBuyer) {
        //idBuyer mel session manager
        return pr.trakingB(codePickup, idBuyer);
    }

    @Override
    public List<Pickup> retrievePickupByDeliveryMenFreelancer() {
        //session manager
        User u = ur.findById(4L).get();
        return pr.pickupOfDeliveryMenFreelancer(u.getId());
    }

    @Override
    public List<Pickup> retrievePickupByAgence() {
        //session Manager Variable
        User u = ur.findById(1L).get();
        return pr.pickupOfAgency(u.getId());
    }

    @Override
    public List<Pickup> retrievePickupByBranch(Long idbranch) {
        //session manager mt3 el agence
        User u = ur.findById(1L).get();
        return pr.pickupOfBranch(u.getId(), idbranch);
    }

    @Override
    public List<Order> retrieveOrderByseller(Long idStore) {
        //session manager idseller
        User u = ur.findById(1L).get();
        return pr.orderOfstore(idStore,u.getId());
    }

    @Override
    public List<Pickup> retrievePickupBysellerAttent() {
        /////session manager
        User u = ur.findById(1L).get();
        return pr.PickupBySeller(u.getId());
    }

    ///////stat
    @Override
    public int countPickupSellerPendingToday() {
        //Session Manager idSeller
        User u = ur.findById(1L).get();
        return pr.countPickupSellerPendingToday(u.getId());
    }

    @Override
    public int countPickupSelleronTheWayToday() {
        //Session Manager idSeller
        User u = ur.findById(1L).get();
        return pr.countPickupSelleronTheWayToday(u.getId());
    }

    @Override
    public int countPickupSellerDeliveredToday() {
        //Session Manager idSeller
        User u = ur.findById(1L).get();
        return pr.countPickupSellerDeliveredToday(u.getId());
    }

    @Override
    public int countPickupSellerReturnToday() {
        //Session Manager idSeller
        User u = ur.findById(1L).get();
        return pr.countPickupSellerReturnToday(u.getId());
    }

    @Override
    public int countPickupSellerRefundedToday() {
        //Session Manager idSeller
        User u = ur.findById(1L).get();
        return pr.countPickupSellerRefundedToday(u.getId());
    }

    @Override
    public int countPickupDeliveryManFreelancerPendingToday() {
        //Session Manager ManFreelancer
        User u = ur.findById(3L).get();
        return pr.countPickupDeliveryManFreelancerPendingToday(u.getId());
    }

    @Override
    public int countPickupAgencyToday() {
        //Session Manager Agency
        User u = ur.findById(4L).get();
        return pr.countPickupAgencyToday(u.getId());
    }

    @Override
    public int countRequestRejectedDeliveryManFreelancerToday() {
        //Session Manager DeliveryManFreelancer
        User u = ur.findById(4L).get();
        return pr.countRequestRejectedDeliveryManFreelancerToday(u.getId());
    }

    @Override
    public int countRequestApprovedDeliveryManFreelancerToday() {
        //Session Manager DeliveryManFreelancer
        User u = ur.findById(4L).get();
        return pr.countRequestApprovedDeliveryManFreelancerToday(u.getId());
    }

    @Override
    public int countRequestRejectedAgencyToday() {
        //Session Manager Agency
        User u = ur.findById(4L).get();
        return pr.countRequestRejectedAgencyToday(u.getId());
    }

    @Override
    public int countRequestApprovedAgencyToday() {
        //Session Manager Agency
        User u = ur.findById(4L).get();
        return pr.countRequestApprovedAgencyToday(u.getId());
    }

    @Override
    public Float SumPricePickupDeliveredByFreelancerToday() {
        //Session Manager Agency
        User u = ur.findById(3L).get();
        List<Pickup> pickups = new ArrayList<>();
        pickups.addAll(pr.SumPricePickupDeliveredByFreelancerToday(u.getId()));
        Float sum = Float.valueOf(0);
        for (Pickup p : pickups) {
            sum = p.getSum() + sum;
        }
        return sum;
    }

    @Override
    public Float SumPricePickupDeliveredByAgencyToday() {
        //Session Manager Agency
        User u = ur.findById(4L).get();
        List<Pickup> pickups = new ArrayList<>();
        pickups.addAll(pr.SumPricePickupDeliveredByAgencyToday(u.getId()));
        Float sum = Float.valueOf(0);
        for (Pickup p : pickups) {
            sum = p.getSum() + sum;
        }
        return sum;
    }

    @Override
    public Float SumPriceDeliveryPickupisDeliveredByFreelancerToday() {
        return null;
    }

    @Override
    public Float SumPriceDeliveryPickupisDeliveredByAgencyToday() {
        return null;
    }

    @Override
    public List<Product> RetrieveProductByPickup(Long idPickup) {
        List<Product> s = pr.ProductBystorebyPickup(idPickup);
        return s;
    }

    ///////////////stat Administrator
    @Override
    public int countAgencyAdministrator() {
        return pr.countAgencyAdministrator();
    }

    @Override
    public int countDeliveryFreelancerAdministrator() {
        return pr.countDeliveryFreelancerAdministrator();
    }

    @Override
    public int countPickupDeliveredTodayAdministrator() {
        return pr.countPickupDeliveredTodayAdministrator();
    }

    @Override
    public int countOfPickupOnTheWayTodayAdministrator() {
        return pr.countOfPickupOnTheWayTodayAdministrator();
    }

    @Override
    public int countOfPickupReturnedTodayAdministrator() {
        return pr.countOfPickupReturnedTodayAdministrator();
    }

    @Override
    public int countOfPickupDeliveredweekAdministrator() {
        return pr.countOfPickupDeliveredweekAdministrator();
    }

    @Override
    public int countOfPickupOnTheWayweekAdministrator() {
        return pr.countOfPickupOnTheWayweekAdministrator();
    }

    @Override
    public int countOfPickupReturnedweekAdministrator() {
        return pr.countOfPickupReturnedweekAdministrator();
    }

    @Override
    public Float sumOfPickupDeliveredTodayAdministrator() {
        List<Pickup> pickups = new ArrayList<>();
        pickups.addAll(pr.sumOfPickupDeliveredTodayAdministrator());
        Float sum = Float.valueOf(0);
        for (Pickup p : pickups) {
            sum = p.getSum() + sum;
        }
        return sum;
    }

    @Override
    public Float sumOfPickupOnTheWayTodayAdministrator() {
        List<Pickup> pickups = new ArrayList<>();
        pickups.addAll(pr.sumOfPickupOnTheWayTodayAdministrator());
        Float sum = Float.valueOf(0);
        for (Pickup p : pickups) {
            sum = p.getSum() + sum;
        }
        return sum;
    }

    @Override
    public Float sumOfPickupReturnedTodayAdministrator() {
        List<Pickup> pickups = new ArrayList<>();
        pickups.addAll(pr.sumOfPickupReturnedTodayAdministrator());
        Float sum = Float.valueOf(0);
        for (Pickup p : pickups) {
            sum = p.getSum() + sum;
        }
        return sum;
    }

    @Override
    public Float sumOfPickupDeliveredweekAdministrator() {
        List<Pickup> pickups = new ArrayList<>();
        pickups.addAll(pr.sumOfPickupDeliveredweekAdministrator());
        Float sum = Float.valueOf(0);
        for (Pickup p : pickups) {
            sum = p.getSum() + sum;
        }
        return sum;
    }

    @Override
    public Float sumOfPickupOnTheWayweekAdministrator() {
        List<Pickup> pickups = new ArrayList<>();
        pickups.addAll(pr.sumOfPickupOnTheWayweekAdministrator());
        Float sum = Float.valueOf(0);
        for (Pickup p : pickups) {
            sum = p.getSum() + sum;
        }
        return sum;
    }

    @Override
    public Float sumOfPickupReturnedweekAdministrator() {
        List<Pickup> pickups = new ArrayList<>();
        pickups.addAll(pr.sumOfPickupReturnedweekAdministrator());
        Float sum = Float.valueOf(0);
        for (Pickup p : pickups) {
            sum = p.getSum() + sum;
        }
        return sum;
    }

    @Override
    public Float kilometreTotalConsommerParFreelancerDelivery() throws IOException, InterruptedException, ApiException {
        //session manager idUser
        User u = ur.findById(3L).get();
        List<Pickup> pickups = pr.SumKilometreINCar(u.getId());
        List<Pickup> pickups1 = pr.AgencyINCar(u.getId());
        float kiloSum = 0;
        //////
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDQCUA-GfJipPTO6s9N-cJr7SUHinNMFGY")
                .build();
        // Get the distance and travel time using the DistanceMatrixApi
        if (u.getRole().getType().equals(RoleType.DELIVERYMEN)) {
            for (Pickup p : pickups) {
                DistanceMatrixApiRequest request = new DistanceMatrixApiRequest(context)
                        .origins(p.getGovernorate())
                        .destinations(p.getStore().getGovernorate())
                        .mode(TravelMode.DRIVING);

                DistanceMatrix matrix = request.await();
                Distance distance = matrix.rows[0].elements[0].distance;
                double distanceInKm = distance.inMeters / 1000.0;
                kiloSum = (float) distanceInKm + kiloSum;
            }
        } else if (u.getRole().getType().equals(RoleType.DELIVERYAGENCY)) {
            for (Pickup p : pickups1) {
                DistanceMatrixApiRequest request = new DistanceMatrixApiRequest(context)
                        .origins(p.getGovernorate())
                        .destinations(p.getStore().getGovernorate())
                        .mode(TravelMode.DRIVING);

                DistanceMatrix matrix = request.await();
                Distance distance = matrix.rows[0].elements[0].distance;
                double distanceInKm = distance.inMeters / 1000.0;
                kiloSum = (float) distanceInKm + kiloSum;
            }
        }

        return kiloSum;

    }

    @Override
    public String FraisEssenceTotal() throws Exception {
        //sessionManager
        User user = ur.findById(3L).get();
        Float kiloSum = kilometreTotalConsommerParFreelancerDelivery();
        double price = Float.valueOf(0);
        double priceEssnceliters = Double.parseDouble(se.scrapePage("https://fr.globalpetrolprices.com/Tunisia/gasoline_prices/"));
        if (user.getRole().getType().equals(RoleType.DELIVERYMEN)) {
            if (user.getGear().equals("CAR")) {
                if (user.getGearAge() > 0 && user.getGearAge() < 5) {
                    price = kiloSum * (5.8 / 100) * priceEssnceliters;
                } else if (user.getGearAge() >= 5 && user.getGearAge() < 10) {
                    price = kiloSum * (6.9 / 100) * priceEssnceliters;
                } else if (user.getGearAge() >= 10) {
                    price = kiloSum * (7.8 / 100) * priceEssnceliters;
                }
            }
        } else if (user.getRole().getType().equals(RoleType.DELIVERYAGENCY)) {
            if (user.getGearAge() > 0 && user.getGearAge() < 5) {
                price = kiloSum * (5.8 / 100) * priceEssnceliters;
            } else if (user.getGearAge() >= 5 && user.getGearAge() < 10) {
                price = kiloSum * (6.9 / 100) * priceEssnceliters;
            } else if (user.getGearAge() >= 10) {
                price = kiloSum * (7.8 / 100) * priceEssnceliters;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedNumber = decimalFormat.format(price);
        return formattedNumber;
    }


    @Override
    public double LimiteCo2() throws IOException, InterruptedException, ApiException {
        //sessionManager
        User user = ur.findById(3L).get();
        double co2kilo = user.getCo2();
        if (co2kilo > 1200) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("You Consume Your Limit Of CO2");
            mailMessage.setText("you must give the world a tree if you don't like to get a strike in COCO market");
            javaMailSender.send(mailMessage);
            //PickupTwilio.sendSMS("You Consume Your Limit Of CO2 ,you must give the world a tree if you don't like to get a strike in COCO market");
        }
        return co2kilo;
    }

    @Override
    public User UpdateTheCO2ConsoFreelancer() throws IOException, InterruptedException, ApiException {
        User user = ur.findById(3L).get();
        Float kiloSum = kilometreTotalConsommerParFreelancerDelivery();
        double co2kilo = Float.valueOf(0);

        double Co2Car = 2.55;
        if (user.getGear().equals("CAR")) {
            if (user.getGearAge() > 0 && user.getGearAge() < 5) {
                co2kilo = kiloSum * (5.8 / 100) * Co2Car;
            } else if (user.getGearAge() >= 5 && user.getGearAge() < 10) {
                co2kilo = kiloSum * (6.9 / 100) * Co2Car;
            } else if (user.getGearAge() >= 10) {
                co2kilo = kiloSum * (7.8 / 100) * Co2Car;
            }

        }
        user.setCo2(co2kilo);
        return ur.save(user);
    }

    @Override
    public List<Pickup> RetrievePickupAgencyByRequestWithStatusRequestApproved() {
        //sessionManager
        User agency = ur.findById(1L).get();
        return pr.ListePickupByStatusAPPROVEDRequest(agency.getId());
    }

    @Override
    public List<Pickup> RetrievePickupFreelancerByRequestWithStatusRequestApproved() {
        //sessionManager
        User freelancer = ur.findById(1L).get();
        return pr.ListePickupByStatusAPPROVEDRequestFreelancer(freelancer.getId());
    }

    @Override
    public Set<Store> RetrieveStoreOfUser() {
        User user = ur.findById(1L).get();
        List<Store> stores = sr.findAll();
        Set<Store> stores1 = new TreeSet<>((s1, s2) -> Long.compare(s1.getId(), s2.getId())); // Use TreeSet with custom Comparator
        for (Store store : stores) {
            if (store.getSeller().getId().equals(user.getId())) {
                stores1.add(store);
            }
        }
        return stores1;
    }

    @Scheduled(cron = "* * * 27 * *")
    public void ModifyTheLevelOfDeliveryAgencyMonthly() {
        List<User> users = pr.ListOfDeliveryAgencywithStatusPickupDelivered();
        List<User> freelancer = pr.ListOfFreelancerwithStatusPickupDelivered();
        for (User u : users) {
            int uu = pr.countPickupdeliveredMonthlyByAgency(u.getId());
            if (uu >= 1 && uu < 100) {
                u.setLevelDelivery("Level 1");
                ur.save(u);
            } else if (uu >= 100 && uu < 500) {
                u.setLevelDelivery("Level 2");
                ur.save(u);
            } else if (uu >= 500) {
                u.setLevelDelivery("Top Rated Delivery");
                ur.save(u);
            }
        }
        for (User fr:freelancer) {

            int uu = pr.countPickupdeliveredMonthlyByFreelancer(fr.getId());
            if (uu > 0 && uu < 100) {
                fr.setLevelDelivery("Level 1");
                ur.save(fr);
            } else if (uu > 100 && uu < 500) {
                fr.setLevelDelivery("Level 2");
                ur.save(fr);
            } else if (uu > 500) {
                fr.setLevelDelivery("Top Rated Delivery");
                ur.save(fr);
            }

        }
    }

}
