package tn.workbot.coco_marketplace.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.StoreRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.StoreInterface;

import java.util.List;

@Service
public class StoreService implements StoreInterface {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserrRepository userrRepository;

    @Autowired
    SessionService sessionService;

    @Override
    public Store create(Store s) {
        //TODO : set user id from session
        User user = sessionService.getUserBySession();
        s.setSeller(user);
        s.setName(s.getName().toUpperCase());
        return storeRepository.save(s);
    }

    @Override
    public Store update(Store s) {
        return storeRepository.save(s);
    }

    @Override
    public List<Store> retrieveAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store getById(Long id) {
        if(storeRepository.findById(id).isPresent())
        return storeRepository.findById(id).get();

        return new Store();
    }
    @Override
    public void delete(Store s) {
        storeRepository.delete(s);
    }

    @Override
    public List<Store> getStoresByUser(Long idUser) {
        return storeRepository.findStoreBySeller(userrRepository.findById(idUser).get());
    }


}
