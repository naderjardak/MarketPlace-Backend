package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tn.workbot.coco_marketplace.entities.Model.auth.ConfirmationToken;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.ConfirmationTokenRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.MailSenderService;
import tn.workbot.coco_marketplace.services.UserService;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

@RestController
@RequestMapping("/Registre")
public class Registration {

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

//    @PostMapping("/registerUser")
//    public String registerUser(@RequestBody User userEntity){
//
//        ModelAndView modelAndView = new ModelAndView();
//
//        User existingUser = userrRepository.findUserByEmail(userEntity.getEmail());
//        if (existingUser != null) {
//            modelAndView.addObject("message", "This email already exists!");
//            modelAndView.setViewName("error");
//        } else {
//            userrRepository.save(userEntity);
//
//            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);
//
//            confirmationTokenRepository.save(confirmationToken);
//
//            emailService.sendEmail(userEntity.getEmail(), "Complete Registration!", "To confirm your account, please click here : "
//                    + "http://localhost:8081/Registre/confirm-account?token=" + confirmationToken.getConfirmationToken());
//
//
//            modelAndView.addObject("email", userEntity.getEmail());
//
//            modelAndView.setViewName("successfulRegisteration");
//        }
//
//        return "successfulRegisteration";
//    }

//    @GetMapping("/confirm-account")
//    public String confirmUserAccount(@RequestParam("token") String  ConfirmationToken)
//    {
//        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(ConfirmationToken);
//        ModelAndView modelAndView = new ModelAndView();
//
//        if (token != null) {
//            User user = userrRepository.findUserByEmail(token.getUserEntity().getEmail());
//            userrRepository.save(user);
//            modelAndView.setViewName("accountVerified");
//        } else {
//            modelAndView.addObject("message", "The link is invalid or broken!");
//            modelAndView.setViewName("error");
//        }
//
//        return "accountVerified";
//    }

}
