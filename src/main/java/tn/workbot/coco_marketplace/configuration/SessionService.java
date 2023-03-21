package tn.workbot.coco_marketplace.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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

    public User getUserBySession(){


    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Get the user details from the authentication object
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    /// Get the user email from the user details object
    String username = userDetails.getUsername();

    return userrRepository.findUserByEmail(username);
    }
}
