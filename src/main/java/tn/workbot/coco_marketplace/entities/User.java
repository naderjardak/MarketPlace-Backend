package tn.workbot.coco_marketplace.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.workbot.coco_marketplace.entities.enmus.genderType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String FirstName;
    private String LastName;
    private String email;
    private String password;
    private boolean enabled;
    private boolean TokenExpired;
    private boolean banned;
    private String PhoneNumber;

    @Temporal(TemporalType.DATE)
    private Date BirthDate;
    private String image;
    @Enumerated(EnumType.STRING)
    private genderType gender;
    private String identity;
    private String BrandName;
    private String BrandLogo;
    private String justification;
    private String governorate;
    private String city;
    private String gear;
    private String DriveLicense;

    // Define a field to store the average rating of the user (buyer_seller,deliveryAgency, DeliveryFreelancer)
    private float rating;

    // Define a field to store the number of ratings that have been given for the user(buyer_seller,deliveryAgency, DeliveryFreelancer)
    private int numberOfRatings;


    @OneToMany(mappedBy = "deliveryAgency")
    private List<AgencyBranch> agencyBranches;

    @OneToMany(mappedBy = "deliveryFreelancer")
    private List<Pickup> pickups;

    @OneToMany(mappedBy = "buyer")
    private List<Order> orders;

    @OneToMany(mappedBy = "seller")
    private List<Store> stores;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<ClaimSav> claimSavList;

    @OneToMany(mappedBy = "userSender")
    private List<Review> reviewsSent;

    @OneToMany(mappedBy = "DeliveryAgency")
    private List<Review> reviewsOnDA;

    @OneToMany(mappedBy = "DeliveryFreelancer")
    private List<Review> reviewsOnDF;




}


