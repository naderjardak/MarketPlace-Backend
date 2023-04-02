package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.workbot.coco_marketplace.entities.enmus.RequestStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private LocalDateTime RequestDate;
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne
    private User deliveryman;
    @JsonIgnore
    @ManyToOne
    private User seller;
    @ManyToOne
    private User Agency;

    @ManyToOne
    private Pickup pickup;

    @ManyToOne
    private AgencyDeliveryMan agencyDeliveryMan;

}
