package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupBuyer;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pickup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String availableDeliver;
    private String comment;
    private String governorate;
    private String city;
    private String codePickup;
    private String shippingStatus;
    private String payed;
    @Temporal(TemporalType.DATE)
    private Date dateCreationPickup;
    @Enumerated(EnumType.STRING)
    private StatusPickupSeller statusPickupSeller;
    @Enumerated(EnumType.STRING)
    private StatusPickupBuyer statusPickupBuyer;
@JsonIgnore
    @ManyToOne
    private AgencyDeliveryMan agencyDeliveryMan;
@JsonIgnore
    @ManyToOne
    private User deliveryFreelancer;
@JsonIgnore
    @ManyToOne
    private Order order;



}
