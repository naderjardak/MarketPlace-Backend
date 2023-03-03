package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.Dto.auth.AuthenticationRequest;
import tn.workbot.coco_marketplace.Dto.auth.AuthenticationResponse;
import tn.workbot.coco_marketplace.configuration.JWT;

@RestController
@RequestMapping("/Authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWT jwt;

    @PostMapping("/auth")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest Request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        Request.getLogin(),
                        Request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(Request.getLogin());
        final  String JWTT = jwt.generateToken(userDetails);

        return new AuthenticationResponse(JWTT);
    }




}

