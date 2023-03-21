package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.AgencyBranch;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;
import tn.workbot.coco_marketplace.entities.Request;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.services.interfaces.AgencyBranchIService;

import java.util.List;

@RestController

@RequestMapping("AgencyBranch")
@PreAuthorize("hasAuthority('DELIVERYAGENCY') || hasAuthority('ADMIN')")
public class AgencyBranchController {
    @Autowired
    AgencyBranchIService abi;

    @PostMapping("Add AgencyBranch")
    public AgencyBranch addAgencyBranch(@RequestBody AgencyBranch agencyBranch) {
        return abi.addAgencyBranch(agencyBranch);
    }

    @DeleteMapping("Delete AgencyBranch")
    public void removeAgencyBranch(@RequestParam Long id) {
        abi.removeAgencyBranch(id);
    }

    @GetMapping("RetrieveAgencyBranch")
    public AgencyBranch RetrieveAgencyBranch(@RequestParam Long id) {
        return abi.RetrieveAgencyBranch(id);
    }

    @GetMapping("RetrieveAllAgencyBranch")
    public List<AgencyBranch> RetrieveAllAgencyBranch() {
        return abi.RetrieveAllAgencyBranch();
    }

    @PutMapping("UpdateAgencyBranch")
    public AgencyBranch updateAgencyBranch(@RequestBody AgencyBranch agencyBranch) {
        return abi.updateAgencyBranch(agencyBranch);
    }

    @PostMapping("AssignBranchManByDeliveryAgency")
    public AgencyBranch AssignBranchManByDeliveryAgency(@RequestBody AgencyBranch agencyBranch, @RequestParam Long Id) {
        return abi.AssignBranchManByDeliveryAgency(agencyBranch, Id);
    }

    @GetMapping("retrievethebranchesofeachagency")
    public List<AgencyBranch>  retrievethebranchesofeachagency() {
        return abi.retrievethebranchesofeachagency();
    }

    @GetMapping("testsst")
    public List<Request> test(@RequestParam Long id){
        return abi.test(id);
    }
}
