package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Store;

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
    public List<Pickup> testretrieve();
    public List<Pickup> RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat();


}
