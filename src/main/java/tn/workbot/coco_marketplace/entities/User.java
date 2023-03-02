package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private Float GearAge;

    // Define a field to store the average rating of the user (buyer_seller,deliveryAgency, DeliveryFreelancer)
    private float rating;

    // Define a field to store the number of ratings that have been given for the user(buyer_seller,deliveryAgency, DeliveryFreelancer)
    private int numberOfRatings;



    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
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

   @OneToMany(mappedBy = "deliveryman")
    private List<Request>requestsdeliverymen;

   @OneToMany(mappedBy = "seller")
    private List<Request>requestsellers;

   @OneToMany(mappedBy = "Agency")
    private List<Request>requestsAgencys;
   @OneToMany(mappedBy = "deliveryAgency")
    private  List<AgencyBranch>agencyBranches;

  /* @OneToMany(mappedBy = "seller")
    private  List<Pickup>pickupsSeller;*/
    /*
@OneToMany(mappedBy = "deliveryFreelancer")
private List<Pickup>PickupdeliverymenFreelancer;
    @OneToMany(mappedBy = "seller")
    private List<Pickup>Pickupsellers;
    @OneToMany(mappedBy = "deliveryAgency")
    private List<Pickup>PickupAgencys;
*/

}


