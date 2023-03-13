package tn.workbot.coco_marketplace.Dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.workbot.coco_marketplace.services.UserService;


@Getter
@Setter

public class NewPassword {
    private String newPassword;
}
