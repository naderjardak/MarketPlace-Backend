package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private Long id;                          // unique identifier of the order

    private String ref;                       // reference of the order
    private float sum;                        // total amount of the order
    private float deliveryPrice;
    private float productsWeightKg;          //weight kg
    private String orderCode;                  // code of the order


    @Enumerated(EnumType.STRING)
    private PaymentType payment;              // payment type of the order

    @Enumerated(EnumType.STRING)
    private StatusOrderType status;           // status of the order

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;                // date of creation of the order


    @OneToMany(mappedBy = "order")    //
    @JsonIgnore
    private List<Pickup> pickups;

    @ManyToOne
    private Shipping shipping;

    @ManyToOne
    @JsonIgnore
    private User buyer;

    @OneToMany(mappedBy = "order")
    private List<ProductQuantity> productQuantities;

    @OneToMany
    private List<PromotionCode> promotionCodeList;

}
