package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;
import tn.workbot.coco_marketplace.repositories.AgencyBranchRepository;
import tn.workbot.coco_marketplace.repositories.AgencyDeliveryManRepository;
import tn.workbot.coco_marketplace.repositories.RequestRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.AgencyBranchIService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AgencyBranchService implements AgencyBranchIService {
     @Autowired
    AgencyBranchRepository abr;
     @Autowired
    UserrRepository user;
     @Autowired
    RequestRepository rr;
     @Autowired
    AgencyDeliveryManRepository admr;

    @Override
    public AgencyBranch addAgencyBranch(AgencyBranch agencyBranch) {
        return abr.save(agencyBranch);
    }

    @Override
    public void removeAgencyBranch(Long id) {
        AgencyBranch branch=abr.findById(id).get();
        abr.deleteById(id);
    }

    @Override
    public AgencyBranch RetrieveAgencyBranch(Long id) {
         if(abr.findById(id).isPresent())
        return abr.findById(id).get();
         return new AgencyBranch();
    }

    @Override
    public List<AgencyBranch>  retrievethebranchesofeachagency() {
        User user1=user.findById(1L).get();
        List<AgencyBranch>agencyBranches=user1.getAgencyBranches();

        return  agencyBranches;
    }

    @Override
    public List<AgencyBranch> RetrieveAllAgencyBranch() {
        List<AgencyBranch> agencyBranchList = abr.findAll();
        return (List<AgencyBranch>) agencyBranchList;
    }

    @Override
    public AgencyBranch updateAgencyBranch(AgencyBranch agencyBranch) {
        return abr.save(agencyBranch);
    }

    @Override
    public AgencyBranch AssignBranchManByDeliveryAgency(AgencyBranch agencyBranch, Long Id) {
        AgencyBranch agencyBranch1=abr.save(agencyBranch);
        User user1=user.findById(Id).get();
        agencyBranch1.setDeliveryAgency(user1);
        return abr.save(agencyBranch1);
    }

    @Override
    public List<Request> test(Long id) {
        AgencyDeliveryMan agencyDeliveryMan=admr.findById(id).get();
        List<Request> req = rr.ByDeliveryMen(id);
        return  req;
    }
}
