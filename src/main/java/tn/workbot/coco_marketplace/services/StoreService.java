package tn.workbot.coco_marketplace.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.repositories.StoreRepository;
import tn.workbot.coco_marketplace.services.interfaces.StoreInterface;

@Service
public class StoreService implements StoreInterface {

    @Autowired
    StoreRepository storeRepository;


    @Override
    public int test() {
        return 0;
    }
}
