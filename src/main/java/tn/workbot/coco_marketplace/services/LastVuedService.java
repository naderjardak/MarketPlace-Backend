package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.LastVued;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.LastVuedRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.LastVuedInterface;

import java.util.List;
import java.util.Objects;

@Service
public class LastVuedService implements LastVuedInterface {

    @Autowired
    LastVuedRepository lastVuedRepository;
    @Autowired
    UserrRepository userrRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SessionService sessionService;

    @Override
    public void createNewVueOrAddNb(Long id) {
        //User Session Manager
        User user=userrRepository.findById(sessionService.getUserBySession().getId()).get();
        Product product=productRepository.findById(id).get();
        LastVued lastVued=lastVuedRepository.findByUserAndProductVued(user,product);
        if (lastVued!=null)
        {
            lastVued.setNbrVued(1+ lastVued.getNbrVued());
        }
        else {
            lastVued = new LastVued();
            lastVued.setProductVued(product);
            lastVued.setNbrVued(1);
            lastVued.setUser(user);
            autoClearLastVued();
        }
        lastVuedRepository.save(lastVued);
    }

    @Override
    public List<Product> afficherLastVued() {
        User user=userrRepository.findById(sessionService.getUserBySession().getId()).get();
        return lastVuedRepository.allVuedBynbVued(user.getId());
    }

    @Override
    public void autoClearLastVued() {
        User user=userrRepository.findById(sessionService.getUserBySession().getId()).get();
        List<LastVued> lastVuedList=lastVuedRepository.allListVuedByUser(user.getId());
        int i=0;
        for(LastVued lv :lastVuedList)
        {
            if(i>20)
            {
                lastVuedRepository.delete(lv);
            }
            i++;
        }
    }
}
