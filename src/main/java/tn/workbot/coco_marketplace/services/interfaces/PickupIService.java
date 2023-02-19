package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Pickup;

import java.util.List;
import java.util.Optional;

public interface PickupIService {
    public Pickup addPickup(Pickup pickup);
    public void removePickup(Long id);
    public Pickup RetrievePickup(Long id);
    public List<Pickup> RetrievePickups();
    public Pickup updatePickup(Pickup pickup,Long id);
}
