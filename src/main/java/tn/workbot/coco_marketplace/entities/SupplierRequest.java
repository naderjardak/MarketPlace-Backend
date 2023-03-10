package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import tn.workbot.coco_marketplace.entities.enmus.SupplierRequestStatus;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SupplierRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private float unityPrice;
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    private int quantity;
    @Temporal(TemporalType.TIME)
    private Date deliveryTime;

    @Enumerated(EnumType.STRING)
    private SupplierRequestStatus requestStatus;
    @ManyToOne
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JsonIgnore
    private User supplier;

    private String reference;

}
