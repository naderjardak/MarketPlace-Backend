package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupBuyer;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private Boolean orderOfTheSomeSeller;
    private String comment;
    private String governorate;
    private String city;
    private String codePickup;
    private String shippingStatus;
    private Boolean payed;
    private LocalDateTime dateCreationPickup;
    @Enumerated(EnumType.STRING)
    private StatusPickupSeller statusPickupSeller;
    @Enumerated(EnumType.STRING)
    private StatusPickupBuyer statusPickupBuyer;

    @JsonIgnore
    @ManyToOne
    private Order order;
    @JsonIgnore
    @OneToMany(mappedBy = "pickup", cascade = CascadeType.ALL)
    private List<Request> requests;
    @JsonIgnore
    @ManyToOne
    private Store store;


}
