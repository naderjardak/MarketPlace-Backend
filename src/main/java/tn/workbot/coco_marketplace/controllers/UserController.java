package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.workbot.coco_marketplace.Api.StatStorePDF;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.configuration.SpringSecurityConfiguration;
import tn.workbot.coco_marketplace.entities.Model.auth.ConfirmationToken;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.RoleType;
import tn.workbot.coco_marketplace.repositories.ConfirmationTokenRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.MailSenderService;
import tn.workbot.coco_marketplace.services.UserService;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("User")
public class UserController {
    @Autowired
    UserInterface userInterface;
    @Autowired
    UserService userService;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    SessionService sessionService;
    @Autowired
    UserrRepository userrRepository;
    PasswordEncoder passwordEncoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private MailSenderService emailService;
    @Autowired
    SpringSecurityConfiguration springSecurityConfiguration;

    {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/add")
    public User Create(@RequestBody User u,@RequestParam long idRole) {

        userInterface.Create(u,idRole);

        ConfirmationToken confirmationToken = new ConfirmationToken(u);

        confirmationTokenRepository.save(confirmationToken);

        emailService.sendEmail(u.getEmail(), "Complete Registration!", "To confirm your account, please check your token : "
                + "token=" + confirmationToken.getConfirmationToken());


        return u;
    }
    @PutMapping("/affectRole")
    public void affectRoleAtUser(@RequestParam long idRole, @RequestParam long idUser) {
        userInterface.affectRoleAtUser(idRole, idUser);
    }
   // @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("users")
    public List<User> GetAll() {
        return userInterface.GetAll();
    }

   // @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("user/{id}")
    User getUserById(@RequestParam long id) {

        return userInterface.getUserById(id);
    }

   // @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("update/{id}")
    public User updateUserById(@RequestParam long id, @RequestBody User u) {
        return userInterface.updateUserByID(id, u);
    }

   // @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("delete/{id}")
    void DeleteById(@RequestParam long id) {
        userInterface.DeleteById(id);
    }


    @PutMapping("/update")
    public User update(@RequestBody User u) {
        return userInterface.update(u);
    }




    @GetMapping("/confirm-account")
    public String confirmUserAccount(@RequestParam("token") String ConfirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(ConfirmationToken);
        String msg;

        if (token != null) {

            User user = userrRepository.findUserByEmail(token.getUserEntity().getEmail());

            userrRepository.save(user);

            msg = "accountVerified";
        } else {

            msg = "error";
        }
        return msg;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/PDF_StatStore", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> PDF_RankGouvernoratByOrdersNumber() throws IOException {
        List<String> stats = userrRepository.SellersGroupeByCityname();

        ByteArrayInputStream bis = StatStorePDF.SellersGroupeByCitynamee(stats);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "attachment; filename=" + new Date(System.currentTimeMillis()) + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("getUserBySession")
    public User getUserBySession() {
        return sessionService.getUserBySession();
    }
    @GetMapping("role")
    public RoleType GetRole(@RequestParam String email){
    User user1=userrRepository.getUserByEmail(email);
        return  user1.getRole().getType();
    }

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        userInterface.storeFile(file);
    }

    @GetMapping("StatsByRole")
    public List<Map<String, Integer>> statsByRole(){return userInterface.statsByRole();}

    @GetMapping("getAllRoles")
    public  List<Role> getAllRolesd(){return  userInterface.getAllRolesd();}
}





