package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Request;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.RequestStatus;
import tn.workbot.coco_marketplace.repositories.AgencyDeliveryManRepository;
import tn.workbot.coco_marketplace.repositories.PickupRepository;
import tn.workbot.coco_marketplace.repositories.RequestRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.RequestInterface;

import java.util.ArrayList;
import java.util.Collections;
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
    @Override
    public Request addRequest(Request request) {
        return rr.save(request);
    }

    @Override
    public void removeRequest(Long id) {
        rr.deleteById(id);
    }

    @Override
    public Request RetrieveRequest(Long id) {
        if(rr.findById(id).isPresent())
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
    public Request assignRequestDeliveryAgencyandDeliverymenandPickup(Request request, Long idDeliveryAgency, Long idPickup,Long idDeliveryMenAgency) {

        //Hadha fel la9i9a bech yetbadel bel variable mt3 el agency eli connecté tawa Session manager
        User user=ur.findById(idDeliveryAgency).get();
        AgencyDeliveryMan agencyDeliveryMan=admr.findById(idDeliveryMenAgency).get();
        List<Request>requestList= (List<Request>) rr.findAll();
        Pickup pickup=pr.findById(idPickup).get();
     /*   int i = 0;
        for (Request r:requestList) {
            if(r.getAgencyDeliveryMan().getId().equals(idDeliveryAgency)){
                i++;
            }
        }*/
        if(rr.countrequestwithsomedeliverymen(idDeliveryMenAgency,idPickup)<1) {
            if (pickup.getGovernorate().equals(agencyDeliveryMan.getGovernorate())) {
                Request request1 = rr.save(request);
                request1.setAgencyDeliveryMan(agencyDeliveryMan);
                request1.setAgency(user);
                request1.setPickup(pickup);
                return rr.save(request);
            }
        }
        return null;
    }

    @Override
    public Request assignRequestDeliveryMenFreelancerandPickup(Request request, Long idDeliveryMenFreelancer, Long idPickup) {
        Request request1=rr.save(request);
        User user=ur.findById(idDeliveryMenFreelancer).get();
        request1.setRequestStatus(RequestStatus.valueOf("PENDING"));
        Pickup pickup=pr.findById(idPickup).get();
        request1.setDeliveryman(user);
        request1.setPickup(pickup);
        return rr.save(request);
    }

    @Override
    public Request assignRequesttoseller(Long idRequest, Long idSeller, String status,Long idPickup) {
        Request request=rr.findById(idRequest).get();
        User seller=ur.findById(idSeller).get();
        List<Request> requestsPending=new ArrayList<>();
        requestsPending.addAll(rr.verifier2(idPickup));
        request.setRequestStatus(RequestStatus.valueOf(status));
        request.setSeller(seller);
        rr.save(request);
        if(rr.verifier(idPickup)==1){
            for (Request r:requestsPending) {
                r.setRequestStatus(RequestStatus.valueOf("REJECTED"));
            }
        }
        request.setRequestStatus(RequestStatus.valueOf(status));
        request.setSeller(seller);
        return rr.save(request);
    }





}
