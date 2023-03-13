package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.Dto.auth.AccountResponse;
import tn.workbot.coco_marketplace.Dto.auth.ResetPassword;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.MailSenderService;
import tn.workbot.coco_marketplace.services.UserService;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("ForgetPassword")
public class ForgetPassword {

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

    @PostMapping("/checkEmail")
    public String resetPasswordEmail(@RequestBody ResetPassword resetPassword, HttpServletRequest request) {
        User user = userService.findByEmail(resetPassword.getEmail());
        AccountResponse accountResponse = new AccountResponse();
        if (user != null) {
            // String code = UserCode.getCode();
            user.setResetToken(UUID.randomUUID().toString());
            userService.Create(user);
            String appUrl = request.getScheme() + "://" + request.getServerName();
            mailSenderService.sendEmail(resetPassword.getEmail(), "Forget password", "To reset your password, check your token :\n" +
                    "token=" + user.getResetToken());

            accountResponse.setResult("User Found");
        } else {
            accountResponse.setResult("Forgot Password");
        }
        return "redirect:/forgot-password?success";
    }


    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam(required = false) String token,
                                Model model) {


        User user = this.userService.findByResetToken(token);
        if (user == null) {
            model.addAttribute("error", "Invalid Token");
            return "Invalid Token";
        } else {

            model.addAttribute("token", token);

            return "token valid";
        }

    }

    @PostMapping("/changePass")
    public String processResetPassword(@RequestParam("token") String token, @RequestParam String Password ) {
 AccountResponse accountResponse = new AccountResponse();


        // Find the user associated with the reset token
        User user = userService.findByResetToken(token);

        if (user != null) {
                String a = this.passwordEncoder.encode(Password);
                System.out.println(a);
                user.setPassword(a);
                userrRepository.save(user);
        }
        return "password changed";
    }

}
