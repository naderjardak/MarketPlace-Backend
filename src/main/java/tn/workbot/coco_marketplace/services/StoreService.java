package tn.workbot.coco_marketplace.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.repositories.StoreRepository;
import tn.workbot.coco_marketplace.services.interfaces.StoreInterface;

import java.util.List;

@Service
public class StoreService implements StoreInterface {

    @Autowired
    StoreRepository storeRepository;




    @Override
    public Store create(Store s) {
        return null;
    }

    @Override
    public Store update(Store s) {
        return null;
    }

    @Override
    public List<Store> retrieveAll() {
        return null;
    }

    @Override
    public Store getById(Long id) {
        return null;
    }

    @Override
    public void delete(Store s) {

    }
}
