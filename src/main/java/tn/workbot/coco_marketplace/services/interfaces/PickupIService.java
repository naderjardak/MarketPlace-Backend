package tn.workbot.coco_marketplace.services.interfaces;

import com.google.maps.errors.ApiException;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.User;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public interface PickupIService {
    public Pickup addPickup(Pickup pickup);
    public void removePickup(Long id);
    public Pickup RetrievePickup(Long id);
    public List<Pickup> RetrievePickups();
    public Pickup updatePickup(Pickup pickup);
    public List<Pickup> RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen(Long id);
    public Pickup AssignPickupByOder(Pickup pickup,Long id);
    public List<Pickup> RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer();
    /*public Pickup AssignPickupBySeller(Pickup pickup);*/
    public List<Pickup> RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat();
    public Pickup AssignPickupByStoreAndOrder(Pickup pickup,Long id);
    public  Pickup ModifyStatusOfPickupByDelivery(String Status,Long idPickup);
    public Duration calculateDeliveryTime(Long idPickup) throws IOException, InterruptedException, ApiException;
    public int test(Long id);
    public Pickup trakingbyseller(String codePickup);
    public Pickup trakingbybuyer(String codePickup,Long idBuyer);
    public List<Pickup> retrievePickupByDeliveryMenFreelancer();
    public List<Pickup> retrievePickupByAgence();
    public List<Pickup> retrievePickupByBranch(Long idbranch);
    public List<Order> retrieveOrderByseller();
    public List<Pickup> retrievePickupBysellerAttent();
    ///////////stat Seller
    public int countPickupSellerPendingToday();
    public int countPickupSelleronTheWayToday();
    public int countPickupSellerDeliveredToday();
    public int countPickupSellerReturnToday();
    public int countPickupSellerRefundedToday();
    ///////////stat Delivery
    public int countPickupDeliveryManFreelancerPendingToday();
    public int countPickupAgencyToday();
    public int countRequestRejectedDeliveryManFreelancerToday();
    public int countRequestApprovedDeliveryManFreelancerToday();
    public int countRequestRejectedAgencyToday();
    public int countRequestApprovedAgencyToday();

   public Float SumPricePickupDeliveredByFreelancerToday();
   public Float SumPricePickupDeliveredByAgencyToday();
   public Float SumPriceDeliveryPickupisDeliveredByFreelancerToday();
   public Float SumPriceDeliveryPickupisDeliveredByAgencyToday();

   public List<Product>RetrieveProductByPickup(Long idPickup);
   /////////////stat Administrator
   public int countAgencyAdministrator();
   public int countDeliveryFreelancerAdministrator();
    public int countPickupDeliveredTodayAdministrator();
    public int countOfPickupOnTheWayTodayAdministrator();
    public int countOfPickupReturnedTodayAdministrator();
    public int countOfPickupDeliveredweekAdministrator();
    public int countOfPickupOnTheWayweekAdministrator();
    public int countOfPickupReturnedweekAdministrator();
   public Float sumOfPickupDeliveredTodayAdministrator();
   public Float sumOfPickupOnTheWayTodayAdministrator();
   public Float sumOfPickupReturnedTodayAdministrator();
    public Float sumOfPickupDeliveredweekAdministrator();
    public Float sumOfPickupOnTheWayweekAdministrator();
    public Float sumOfPickupReturnedweekAdministrator();

    ///////////Gear Delivery Alers (Kilometre || ESSENCE)
    public Float kilometreTotalConsommerParFreelancerDelivery() throws IOException, InterruptedException, ApiException;
    public String FraisEssenceTotal() throws IOException, InterruptedException, ApiException;

    //////////Envoyer Un sms si vous avez cconsoumer ton limite  CO2  ,
    public double LimiteCo2() throws IOException, InterruptedException, ApiException;
    public User UpdateTheCO2ConsoFreelancer() throws IOException, InterruptedException, ApiException;

    public List<Pickup> RetrievePickupAgencyByRequestWithStatusRequestApproved();
    public List<Pickup> RetrievePickupFreelancerByRequestWithStatusRequestApproved();






}
