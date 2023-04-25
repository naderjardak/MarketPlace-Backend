package tn.workbot.coco_marketplace.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.Dto.auth.AuthenticationRequest;
import tn.workbot.coco_marketplace.Dto.auth.AuthenticationResponse;
import tn.workbot.coco_marketplace.configuration.JWT;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.configuration.SpringSecurityConfiguration;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.auth.ApplicationUserDetailsService;

@RestController
@CrossOrigin(origins = "*")
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

    @Autowired
    SessionService SessionService;


    @PostMapping("/auth")
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
         /*   HttpHeaders headers=new HttpHeaders();
            headers.set("Authorization","Bearer" + JWTT);*/
            return new AuthenticationResponse(JWTT);
        } else {
            // Le mot de passe est incorrect, retourner une erreur d'authentification
            throw new BadCredentialsException("Invalid username or password");
        }


    }





}

