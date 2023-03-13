package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import tn.workbot.coco_marketplace.entities.enmus.SupplierRequestStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

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
    private User supplier;

    private String reference;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SupplierRequest that = (SupplierRequest) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
