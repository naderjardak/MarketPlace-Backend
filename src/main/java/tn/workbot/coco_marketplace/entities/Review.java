package tn.workbot.coco_marketplace.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id", nullable = false)
    private Long id;

    //TO DO :Dear Houssem, complete your attributes

    private int rating;

    private String comment;


    @ManyToOne
    private User userSender;

    @ManyToOne
    private User DeliveryAgency;
    @ManyToOne
    private User DeliveryFreelancer;
    @ManyToOne
    private Product product;


}
