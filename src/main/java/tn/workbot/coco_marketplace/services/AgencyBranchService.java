package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.AgencyBranch;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;
import tn.workbot.coco_marketplace.entities.Request;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.RequestStatus;
import tn.workbot.coco_marketplace.repositories.AgencyBranchRepository;
import tn.workbot.coco_marketplace.repositories.AgencyDeliveryManRepository;
import tn.workbot.coco_marketplace.repositories.RequestRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.AgencyBranchIService;

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
    @Autowired
    SessionService sessionService;


    @Override
    public AgencyBranch addAgencyBranch(AgencyBranch agencyBranch) {
        return abr.save(agencyBranch);
    }

    @Override
    public void removeAgencyBranch(Long id) {
        AgencyBranch branch = abr.findById(id).get();
        List<Request> requests = (List<Request>) rr.findAll();
        boolean hasMatch = false;
        for (Request r : requests) {
            if (r.getAgencyDeliveryMan().getAgencyBranch().getId().equals(branch.getId())) {
                if (!r.getRequestStatus().equals(RequestStatus.APPROVED)) {
                    if (rr.countApproved(id) == 0) {
                        hasMatch = true;
                        break;
                    }
                }
            }
        }
        if (!hasMatch) {
            abr.deleteById(id);
        }
    }

    @Override
    public AgencyBranch RetrieveAgencyBranch(Long id) {
        if (abr.findById(id).isPresent())
            return abr.findById(id).get();
        return new AgencyBranch();
    }

    @Override
    public List<AgencyBranch> retrievethebranchesofeachagency() {
        User u=sessionService.getUserBySession();
        List<AgencyBranch> agencyBranches = u.getAgencyBranches();
        return agencyBranches;
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
    public AgencyBranch AssignBranchManByDeliveryAgency(AgencyBranch agencyBranch) {
        AgencyBranch agencyBranch1 = abr.save(agencyBranch);
        User u=sessionService.getUserBySession();
        agencyBranch1.setDeliveryAgency(u);
        agencyBranch1.setX(0);
        agencyBranch1.setY(0);
        return abr.save(agencyBranch1);
    }

    @Override
    public List<Request> test(Long id) {
        AgencyDeliveryMan agencyDeliveryMan = admr.findById(id).get();
        List<Request> req = rr.ByDeliveryMen(id);
        return req;
    }

    @Override
    public int countAgencyBranchesInAgency() {
        User u=sessionService.getUserBySession();
        return abr.countAgencyBranch(u.getId());
    }

    @Override
    public int countDeliveryMenInAllAgencyBranchesForAgench() {
        User u=sessionService.getUserBySession();
        return abr.countDeliveryMenInAllBranchtoAgency(u.getId());
    }

    @Override
    public List<AgencyBranch> retrieveAgencyBranchOfUser() {
        User u=sessionService.getUserBySession();
        return abr.retrieveAgency(u.getId());
    }

    @Override
    public int countDeliveryMenInAgency(Long idBranch) {
        return abr.countDeliveryMenInBranchByIdBranch(idBranch);
    }

    @Override
    public AgencyBranch updatebRANCHwithMAP(Long idBranch, AgencyBranch agencyBranch) {
        AgencyBranch agencyBranch1=abr.findById(idBranch).get();
        agencyBranch.setDeliveryAgency(agencyBranch1.getDeliveryAgency());
        return abr.save(agencyBranch);
    }
}
