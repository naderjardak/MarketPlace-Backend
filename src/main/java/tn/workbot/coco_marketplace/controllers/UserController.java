package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tn.workbot.coco_marketplace.Api.OrderStatsPDFGenerator;
import tn.workbot.coco_marketplace.Api.StatStorePDF;
import tn.workbot.coco_marketplace.entities.Model.auth.ConfirmationToken;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.ConfirmationTokenRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.MailSenderService;
import tn.workbot.coco_marketplace.services.UserService;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
//@RequestMapping("User")
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

    @PostMapping("/add")
    public User Create(@RequestBody User u) {

            userInterface.Create(u);

            ConfirmationToken confirmationToken = new ConfirmationToken(u);

            confirmationTokenRepository.save(confirmationToken);

            emailService.sendEmail(u.getEmail(), "Complete Registration!", "To confirm your account, please check your token : "
                    + "token=" + confirmationToken.getConfirmationToken());



            return u;
        }
    @GetMapping("/users")
    public List<User> GetAll() {
        return userInterface.GetAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@RequestParam long id) {

        return userInterface.getUserById(id);
    }


    @PutMapping("/update/{id}")
    public User updateUserById(@RequestParam long id,@RequestBody User u) {
        return userInterface.updateUserByID( id,u);
    }

    @DeleteMapping("/delete/{id}")
    void DeleteById(@RequestParam long id) {
        userInterface.DeleteById(id);
    }

    @PutMapping("/update")
    public User update(@RequestBody User u) {
        return userInterface.update(u);
    }

    @PutMapping("/affectRole")
    public void affectRoleAtUser(@RequestParam long idRole, @RequestParam long idUser) {
        userInterface.affectRoleAtUser(idRole, idUser);
    }


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


    @GetMapping(value = "/PDF_StatStore", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> PDF_RankGouvernoratByOrdersNumber() throws IOException {
        List<String> stats = userrRepository.SellersGroupeByCityname();

        ByteArrayInputStream bis = StatStorePDF.SellersGroupeByCitynamee(stats);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "attachment; filename="+new Date(System.currentTimeMillis())+".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    //test
 }





