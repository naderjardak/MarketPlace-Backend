package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tn.workbot.coco_marketplace.entities.Model.auth.ConfirmationToken;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.ConfirmationTokenRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.MailSenderService;
import tn.workbot.coco_marketplace.services.UserService;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private MailSenderService emailService;
    @Autowired
    UserInterface userInterface;

    @Autowired
    UserService userService;

    @Autowired
    MailSenderService mailSenderService;


    @Autowired
    UserrRepository userrRepository;

    PasswordEncoder passwordEncoder;

    {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/addUser")
    public User Create(@RequestBody User u) {

            userInterface.Create(u);

            ConfirmationToken confirmationToken = new ConfirmationToken(u);

            confirmationTokenRepository.save(confirmationToken);

            emailService.sendEmail(u.getEmail(), "Complete Registration!", "To confirm your account, please check your token : "
                    + "token=" + confirmationToken.getConfirmationToken());



            return u;
        }

    @DeleteMapping("/deleteUser")
    void DeleteById(@RequestParam long id) {
        userInterface.DeleteById(id);
    }

    @PutMapping("/updateUser")
    public User update(@RequestBody User u) {
        return userInterface.update(u);
    }

    @GetMapping("/selectUserById")
    User GetById(@RequestParam long id) {
        return userInterface.GetById(id);
    }

    @GetMapping("/selectUserAll")
    public List<User> GetAll() {
        return userInterface.GetAll();
    }

    @PutMapping("/affectRole")
    public void affectRoleAtUser(@RequestParam long idRole, @RequestParam long idUser) {
        userInterface.affectRoleAtUser(idRole, idUser);
    }

//      @GetMapping("/getUser")
//        public User findByEmail(@RequestParam String email){
//        return userInterface.findByEmail(email);
//}

    @GetMapping("/confirm-account")
    public String confirmUserAccount(@RequestParam("token") String  ConfirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(ConfirmationToken);
        String msg;

        if (token != null) {

            User user = userrRepository.findUserByEmail(token.getUserEntity().getEmail());

            userrRepository.save(user);

            msg = "accountVerified";
        } else {

            msg = "error";
        }
        return  msg;
    }
 }





