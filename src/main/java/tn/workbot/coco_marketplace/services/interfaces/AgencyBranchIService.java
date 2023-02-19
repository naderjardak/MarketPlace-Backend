package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.AgencyBranch;

import java.util.List;

public interface AgencyBranchIService {
    public AgencyBranch addAgencyBranch(AgencyBranch agencyBranch);
    public void removeAgencyBranch(Long id);
    public AgencyBranch RetrieveAgencyBranch(Long id);
    public List<AgencyBranch> RetrieveAllAgencyBranch();
    public AgencyBranch updateAgencyBranch(AgencyBranch agencyBranch);
}
