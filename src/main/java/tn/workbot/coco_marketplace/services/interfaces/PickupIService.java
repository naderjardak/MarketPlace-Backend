package tn.workbot.coco_marketplace.services.interfaces;

import com.google.maps.errors.ApiException;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Store;

import javax.swing.text.Position;
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




}
