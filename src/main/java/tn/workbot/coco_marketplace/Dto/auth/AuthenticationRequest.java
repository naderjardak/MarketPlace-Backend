package tn.workbot.coco_marketplace.Dto.auth;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private  String login;
    private  String password;
}
