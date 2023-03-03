package tn.workbot.coco_marketplace.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Model.auth.ExtendedUser;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        tn.workbot.coco_marketplace.entities.User user = userService.findByEmail(email);
        List<SimpleGrantedAuthority> authorities =new ArrayList<>();

        String role = user.getRole().getType().toString();
        authorities.add(new SimpleGrantedAuthority(role));

        return new ExtendedUser(user.getEmail() ,user.getPassword(),authorities);
    }
}

