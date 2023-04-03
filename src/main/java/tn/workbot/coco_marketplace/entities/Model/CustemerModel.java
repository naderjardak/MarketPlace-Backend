package tn.workbot.coco_marketplace.entities.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustemerModel
{
    private String customerId;
    private String name;
    private String email;
    private Long paidAmount;
    private String cardToken;
}
