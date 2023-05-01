package tn.workbot.coco_marketplace.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.UserrRepository;

@Service
public class SessionService {

    @Autowired
    UserrRepository userrRepository;

    public User getUserBySession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            return userrRepository.findUserByEmail(username);
        } else {
            return null; // or throw an exception, depending on your requirements
        }
    }
}
