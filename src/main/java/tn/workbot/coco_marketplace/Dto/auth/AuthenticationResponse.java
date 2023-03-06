package tn.workbot.coco_marketplace.Dto.auth;


import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

    private String accessToken;
}
