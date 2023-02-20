package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.repositories.PickupRepository;
import tn.workbot.coco_marketplace.services.interfaces.PickupIService;

import java.util.List;
import java.util.Optional;

@Service
public class PickupService implements PickupIService {
    @Autowired
    PickupRepository pr;

    @Override
    public Pickup addPickup(Pickup pickup) {
        return pr.save(pickup);
    }

    @Override
    public void removePickup(Long id) {
         pr.deleteById(id);
    }

    @Override
    public Pickup RetrievePickup(Long id) {
        if(pr.findById(id).isPresent())
        return pr.findById(id).get();
        return new Pickup();
    }

    @Override
    public List<Pickup> RetrievePickups() {
        return (List<Pickup>) pr.findAll();
    }

    @Override
    public Pickup updatePickup(Pickup pickup) {
        return pr.save(pickup);
    }
}
