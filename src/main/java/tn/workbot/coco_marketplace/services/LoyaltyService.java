package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.Loyalty;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.LoyaltyRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.LoyaltyInterface;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LoyaltyService implements LoyaltyInterface {

    @Autowired
    LoyaltyRepository loyaltyRepository;

    @Autowired
    UserrRepository userrRepository;

    @Autowired
    SessionService sessionService;

    @Override
    public String generateLoyaltyLink() {
        User user=userrRepository.findById(sessionService.getUserBySession().getId()).get();
        Loyalty  lt= loyaltyRepository.loyaltyExistance(user.getId());
        if(lt==null)
        {
            String token = UUID.randomUUID().toString();
            String link = "http://localhost:8081/claim?link=" + token;
            Loyalty loyalty = new Loyalty();
            loyalty.setUser(user);
            loyalty.setPoints(0);
            loyalty.setLastActivity(LocalDateTime.now());
            loyalty.setLink(token);
            loyaltyRepository.save(loyalty);
            return link;
        }
        return lt.getLink();
    }

    @Override
    public ResponseEntity<Void> claimReward( String link) {
        Loyalty loyalty = loyaltyRepository.findByLink(link);
        if (loyalty.getLink() == null) {
            return ResponseEntity.notFound().build();
        }
        if (loyalty.getLastActivity().plusDays(1).isBefore(LocalDateTime.now())) {
            int pt=loyalty.getPoints();
            loyalty.setPoints(pt+1);
            loyalty.setLastActivity(LocalDateTime.now());
            loyaltyRepository.save(loyalty);
            //redirection to coco signe in page
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public int claimedPaoints(){
        //Session Manager
        User user=userrRepository.findById(sessionService.getUserBySession().getId()).get();
        return loyaltyRepository.pointsClaimed(user.getId());
    }
}
