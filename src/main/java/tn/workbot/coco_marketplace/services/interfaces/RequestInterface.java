package tn.workbot.coco_marketplace.services.interfaces;

import com.google.maps.errors.ApiException;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Request;

import java.io.IOException;
import java.util.List;

public interface RequestInterface {
    public Request addRequest(Request request);
    public void removeRequest(Long id);
    public Request RetrieveRequest(Long id);
    public List<Request> RetrieveRequests();
    public Request updateRequest(Request request);
    public Request assignRequestDeliveryAgencyandDeliverymenandPickup(Request request, Long idPickup,Long idDeliveryMenAgency);
    public Request assignRequestDeliveryMenFreelancerandPickup(Request request, Long idPickup);

    public Request assignRequesttoseller(Long idRequest,String status,Long idPickup) throws IOException, InterruptedException, ApiException;
    public List<Request> retrieveRequestBySeller();
    public List<Request> retrieveRequestByPickup(Long idPickup);
    public List<Request> RetrieveRequestByAgency();
    public List<Request> RetrieveRequestByFreelancer();
}