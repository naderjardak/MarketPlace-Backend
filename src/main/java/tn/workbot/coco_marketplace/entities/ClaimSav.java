package tn.workbot.coco_marketplace.entities;


import com.fasterxml.jackson.databind.DatabindException;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavStatusType;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClaimSav implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String reference;

    private String object;

    private String body;

    private String screenshot;

    @Enumerated(EnumType.STRING)
    private ClaimSavStatusType status;

    @Enumerated(EnumType.STRING)
    private ClaimSavType claimSavType;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    ///TO DO :Dear Houssem, complete your attributes

    @ManyToOne
    private User user;

    @OneToOne
    private ProductQuantity productQuantity;






}





