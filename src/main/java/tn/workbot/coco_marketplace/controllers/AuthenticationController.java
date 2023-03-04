package tn.workbot.coco_marketplace.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.workbot.coco_marketplace.Dto.auth.AuthenticationRequest;
import tn.workbot.coco_marketplace.Dto.auth.AuthenticationResponse;
import tn.workbot.coco_marketplace.configuration.JWT;
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
        final String JWTT = jwt.generateToken(userDetails);

        return new AuthenticationResponse(JWTT);
    }


}

