package tn.workbot.coco_marketplace.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import tn.workbot.coco_marketplace.entities.enmus.ReviewEmotionStatus;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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



    private int rating;

    private String comment;

    @Enumerated(EnumType.STRING)
    private ReviewEmotionStatus emotionStatus;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    private Date updatedAt;


    @ManyToOne
    private User userSender;

    @JsonIgnore
    @ManyToOne
    private User DeliveryAgency;
    @ManyToOne
    @JsonIgnore
    private User DeliveryFreelancer;
    @ManyToOne
    private Product product;



}
