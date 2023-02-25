package tn.workbot.coco_marketplace.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //TO DO :Dear Houssem, complete your attributes

    private int rating;

    private String comment;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    private Date updatedAt;


    @ManyToOne
    private User userSender;

    @ManyToOne
    private User DeliveryAgency;
    @ManyToOne
    private User DeliveryFreelancer;
    @ManyToOne
    private Product product;


}
