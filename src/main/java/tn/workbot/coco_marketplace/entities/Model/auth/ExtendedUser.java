package tn.workbot.coco_marketplace.entities.Model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

public class ExtendedUser extends User {

    public  ExtendedUser(String username, String password
            , Collection<? extends GrantedAuthority> authorities){
        super(username,password,authorities);
    }


}

