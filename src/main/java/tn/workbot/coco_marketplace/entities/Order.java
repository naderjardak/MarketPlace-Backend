package tn.workbot.coco_marketplace.entities;

import lombok.*;
import tn.workbot.coco_marketplace.entities.enmus.PaymentType;
import tn.workbot.coco_marketplace.entities.enmus.StatusOrderType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "[order]")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ref;
    private float sum;
    private String orderCode;

    @Enumerated(EnumType.STRING)
    private PaymentType payment;

    @Enumerated(EnumType.STRING)
    private StatusOrderType status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    //
    @OneToMany(mappedBy = "order")
    private List<Pickup> pickups;

    @ManyToOne
    private Shipping shipping;

    @ManyToOne
    private User buyer;

    @OneToMany(mappedBy = "order")
    private List<ProductQuantity> productQuantities;

}
