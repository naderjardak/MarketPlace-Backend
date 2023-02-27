package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductQuantity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

   @ManyToOne
   @JsonIgnore
   private Order order;

    @ManyToOne
    private Product product;

    @OneToOne(mappedBy = "productQuantity")
    private ClaimSav claimSav;

}
