package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.RequestStatus;
import tn.workbot.coco_marketplace.repositories.*;
import tn.workbot.coco_marketplace.services.interfaces.AgencyDeliveryManIService;

import java.util.List;
import java.util.Optional;

@Service
public class AgencyDeliveryManService implements AgencyDeliveryManIService {
    @Autowired
    AgencyDeliveryManRepository admr;
    @Autowired
    AgencyBranchRepository  abr;
    @Autowired
    RequestRepository rr;
    @Autowired
    UserrRepository ur;
    @Autowired
    PickupRepository pr;

    @Override
    public AgencyDeliveryMan addAgencyDeliveryMan(AgencyDeliveryMan agencyDeliveryMan) {
        return admr.save(agencyDeliveryMan);
    }

    @Override
    public AgencyDeliveryMan UpdateAgencyDeliveryMan(AgencyDeliveryMan agencyDeliveryMan) {
        return admr.save(agencyDeliveryMan );
    }

    @Override
    public void removeAgencyDeliveryMan(Long id) {

        AgencyDeliveryMan man=admr.findById(id).get();
        List<Request> requests= (List<Request>) rr.findAll();
        for (Request r:requests) {
            if(r.getAgencyDeliveryMan().getId().equals(man.getId()))
            {
                if(!r.getRequestStatus().equals(RequestStatus.APPROVED))
                {
                    if(rr.countApprovedDeliveryAgence(id)==0) {
                        admr.deleteById(id);
                    }
                }

            }

        }
    }

    @Override
    public AgencyDeliveryMan RetrieveAgencyDeliveryMan(Long id) {
        if(admr.findById(id).isPresent())
        return admr.findById(id).get();
        return new AgencyDeliveryMan();

    }

    @Override
    public List<AgencyDeliveryMan> RetrieveAgencyDeliveryMen() {
        return (List<AgencyDeliveryMan>) admr.findAll();
    }

    @Override
    public AgencyDeliveryMan AssignAgencyDeliveryManByBranch(AgencyDeliveryMan agencyDeliveryMan, Long Id) {
        AgencyDeliveryMan agencyDeliveryMan1=admr.save(agencyDeliveryMan);
        AgencyBranch agencyBranch=abr.findById(Id).get();
        agencyDeliveryMan1.setAgencyBranch(agencyBranch);
        return admr.save(agencyDeliveryMan1);
    }

    @Override
    public List<AgencyDeliveryMan> RetrieveDeliverymenByagencyWhenThegovernorateOfPickupisSomeGovernorateofdeliverymen(Long idPickup) {
       //session el id mt3 agency
        User u=ur.findById(4L).get();
        Pickup p=pr.findById(idPickup).get();
        return admr.deliveryMenByAgency(u.getId(),p.getId());
    }
}
