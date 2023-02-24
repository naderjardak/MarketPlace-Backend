package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupBuyer;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;
import tn.workbot.coco_marketplace.repositories.*;
import tn.workbot.coco_marketplace.services.interfaces.PickupIService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class PickupService implements PickupIService {
    @Autowired
    PickupRepository pr;
    @Autowired
    OrderRepository or;
    @Autowired
    StoreRepository sr;
    @Autowired
    UserrRepository ur;
    @Autowired
    AgencyBranchRepository abr;

    @Override
    public Pickup addPickup(Pickup pickup) {
        Random random = new Random(); //java.util.Random
        pickup.setStatusPickupSeller(StatusPickupSeller.valueOf("PICKED"));
        pickup.setStatusPickupBuyer(StatusPickupBuyer.valueOf("PLACED"));
        int randomNumber = random.nextInt(9000) + 1000;  // generates a random number between 1000 and 9999
        String prefix = "216";
        String code = prefix + randomNumber;
        List<Pickup>pickups= (List<Pickup>) pr.findAll();
        for (Pickup p:pickups) {
                      if(p.getCodePickup()!=code){
                          pickup.setCodePickup(code);
                      }
                      else {
                          int randomNumber1 = random.nextInt(100) + 100;
                          String code1 = prefix + randomNumber + randomNumber1;
                          pickup.setCodePickup(code1);
                      }
        }
        pickup.setCodePickup(code);
        pickup.setDateCreationPickup(LocalDateTime.now());
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
    }/**/

    @Override
    public List<Pickup> RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen(Long id) {
        return null;
    }

    @Override
    public Pickup AssignPickupByOder(Pickup pickup, Long id) {
        Pickup p=pr.save(pickup);
        Order order=or.findById(id).get();
        p.setOrder(order);
        return pr.save(pickup);
    }

    @Override
    public List<Pickup> RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer() {
        //HADHI session variable
        User u = ur.findById(1L).get();
        List<Store> store= (List<Store>) sr.findAll();
        List<Store> storesInSameGovernorate = sr.findByGovernorate(u.getGovernorate());
        List<Pickup> pickups = new ArrayList<>();
        //ELI 3ANDHOUM BIKE MATODHA7RELHOUM KEN EL PICKUPS ELI BECH TTLVRA FEN HOUMA W STORE YABDA FARD GOVERNORATE
       if((u.getGear().equals("BIKE")) || (u.getGear().equals("MOTO")))
       {
           for (Store s : store) {
               if (u.getGovernorate().equals(s.getGovernorate())) {
                   return pr.findByGovernorate(s.getGovernorate());
               }
           }
       }
       else if(u.getGear().equals("CAR"))
       {
           for (Store storee : storesInSameGovernorate) {
               pickups.addAll(storee.getPickups());
           }

           return pickups;
       }
        return  Collections.emptyList();
    }


    @Override
    public List<Pickup> RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat() {
        User u = ur.findById(1L).get();
        List<AgencyBranch> agencyBranch=abr.findAll();
        List<User> users= (List<User>) ur.findAll();
        List<Store>stores=sr.findAll();
        List<AgencyBranch> agencyBranches = new ArrayList<>();
        ArrayList<Pickup> store = new ArrayList<>();
        agencyBranches.addAll(u.getAgencyBranches());
        for (AgencyBranch ab:agencyBranches) {
            for(Store s:stores){
                if(s.getGovernorate().equals(ab.getGovernorate())){
                    store.addAll(s.getPickups());
                }
            }
        }
        return store ;
    }



}
