package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.AgencyBranch;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.AgencyBranchRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.AgencyBranchIService;

import java.util.List;

@Service
public class AgencyBranchService implements AgencyBranchIService {
     @Autowired
    AgencyBranchRepository abr;
     @Autowired
    UserrRepository user;

    @Override
    public AgencyBranch addAgencyBranch(AgencyBranch agencyBranch) {
        return abr.save(agencyBranch);
    }

    @Override
    public void removeAgencyBranch(Long id) {
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
}
