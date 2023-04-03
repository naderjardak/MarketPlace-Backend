package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.Api.StatStorePDF;
import tn.workbot.coco_marketplace.entities.Model.auth.ConfirmationToken;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.ConfirmationTokenRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.MailSenderService;
import tn.workbot.coco_marketplace.services.UserService;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

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

    @PostMapping("/add")
    public User Create(@RequestBody User u) {

            userInterface.Create(u);

            ConfirmationToken confirmationToken = new ConfirmationToken(u);

            confirmationTokenRepository.save(confirmationToken);

            emailService.sendEmail(u.getEmail(), "Complete Registration!", "To confirm your account, please check your token : "
                    + "token=" + confirmationToken.getConfirmationToken());



            return u;
        }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public List<User> GetAll() {
        return userInterface.GetAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{id}")
    User getUserById(@RequestParam long id) {

        return userInterface.getUserById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public User updateUserById(@RequestParam long id,@RequestBody User u) {
        return userInterface.updateUserByID( id,u);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    void DeleteById(@RequestParam long id) {
        userInterface.DeleteById(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
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


 }





