package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.AgencyBranch;
import tn.workbot.coco_marketplace.repositories.AgencyBranchRepository;
import tn.workbot.coco_marketplace.services.interfaces.AgencyBranchIService;

import java.util.List;

@Service
public class AgencyBranchService implements AgencyBranchIService {
     @Autowired
    AgencyBranchRepository abr;

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
        return abr.findById(id).get();
    }

    @Override
    public List<AgencyBranch> RetrieveAllAgencyBranch() {
        List<AgencyBranch> agencyBranchList = abr.findAll();
        return (List<AgencyBranch>) agencyBranchList;
    }

    @Override
    public AgencyBranch updateAgencyBranch(AgencyBranch agencyBranch,Long id) {
        agencyBranch.setId(id);
        return abr.save(agencyBranch);
    }
}
