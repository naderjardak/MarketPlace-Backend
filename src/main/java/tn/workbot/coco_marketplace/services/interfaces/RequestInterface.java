package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Request;

import java.util.List;

public interface RequestInterface {
    public Request addRequest(Request request);
    public void removeRequest(Long id);
    public Request RetrieveRequest(Long id);
    public List<Request> RetrieveRequests();
    public Request updateRequest(Request request);
    public Request assignRequestDeliveryAgencyandDeliverymenandPickup(Request request, Long idDeliveryAgency, Long idPickup,Long idDeliveryMenAgency);
    public Request assignRequestDeliveryMenFreelancerandPickup(Request request, Long idDeliveryMenFreelancer, Long idPickup);

    public Request assignRequesttoseller(Long idRequest,Long idSeller,String status);
}
