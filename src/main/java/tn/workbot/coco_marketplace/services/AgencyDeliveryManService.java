package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;
import tn.workbot.coco_marketplace.repositories.AgencyDeliveryManRepository;
import tn.workbot.coco_marketplace.services.interfaces.AgencyDeliveryManIService;

import java.util.List;
import java.util.Optional;

@Service
public class AgencyDeliveryManService implements AgencyDeliveryManIService {
    @Autowired
    AgencyDeliveryManRepository admr;

    @Override
    public AgencyDeliveryMan addAgencyDeliveryMan(AgencyDeliveryMan agencyDeliveryMan) {
        return admr.save(agencyDeliveryMan);
    }

    @Override
    public AgencyDeliveryMan UpdateAgencyDeliveryMan(AgencyDeliveryMan agencyDeliveryMan,Long id) {
        agencyDeliveryMan.setId(id);
        return admr.save(agencyDeliveryMan );
    }

    @Override
    public void removeAgencyDeliveryMan(Long id) {
        admr.deleteById(id);
    }

    @Override
    public Optional<AgencyDeliveryMan> RetrieveAgencyDeliveryMan(Long id) {
        return admr.findById(id);
    }

    @Override
    public List<AgencyDeliveryMan> RetrieveAgencyDeliveryMen() {
        return (List<AgencyDeliveryMan>) admr.findAll();
    }
}
