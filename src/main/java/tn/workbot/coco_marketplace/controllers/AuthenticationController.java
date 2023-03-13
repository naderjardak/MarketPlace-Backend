package tn.workbot.coco_marketplace.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tn.workbot.coco_marketplace.Dto.auth.AuthenticationRequest;
import tn.workbot.coco_marketplace.Dto.auth.AuthenticationResponse;
import tn.workbot.coco_marketplace.configuration.JWT;
import tn.workbot.coco_marketplace.configuration.SpringSecurityConfiguration;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.auth.ApplicationUserDetailsService;

@RestController
@RequestMapping("Authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationUserDetailsService userDetailsService;
    @Autowired
    private JWT jwt;
    @Autowired
    UserrRepository userrRepository;


    @PostMapping("auth")
    @SecurityRequirements
    public AuthenticationResponse authenticate(@NotNull @RequestBody AuthenticationRequest Request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        Request.getLogin(),
                        Request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(Request.getLogin());
        // Vérifier si le mot de passe fourni correspond au mot de passe enregistré pour l'utilisateur
        if (userDetails != null && SpringSecurityConfiguration.matchPassword(Request.getPassword(), userDetails.getPassword())) {
            final String JWTT = jwt.generateToken(userDetails);
            return new AuthenticationResponse(JWTT);
        } else {
            // Le mot de passe est incorrect, retourner une erreur d'authentification
            throw new BadCredentialsException("Invalid username or password");
        }


    }


}

