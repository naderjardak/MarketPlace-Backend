package tn.workbot.coco_marketplace.Oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.UserrRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
   private UserrRepository userRepository;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder(11);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("No User  found");
        }

        return new   org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true ,
                getAuthorities(List.of(user.getRole().getType().toString()))
        );
    }
    private Collection<? extends GrantedAuthority> getAuthorities(List<String>roles){
        List<GrantedAuthority>authorities = new ArrayList<>();
        for(String role: roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return  authorities;
    }
}
