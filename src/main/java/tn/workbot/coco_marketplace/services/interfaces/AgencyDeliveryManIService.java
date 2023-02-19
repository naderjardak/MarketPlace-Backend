package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;
import tn.workbot.coco_marketplace.entities.Pickup;

import java.util.List;
import java.util.Optional;

public interface AgencyDeliveryManIService {
    public AgencyDeliveryMan addAgencyDeliveryMan(AgencyDeliveryMan agencyDeliveryMan);
    public AgencyDeliveryMan UpdateAgencyDeliveryMan(AgencyDeliveryMan agencyDeliveryMan,Long id);
    public void removeAgencyDeliveryMan(Long id);
    public Optional<AgencyDeliveryMan> RetrieveAgencyDeliveryMan(Long id);
    public List<AgencyDeliveryMan> RetrieveAgencyDeliveryMen();
}
