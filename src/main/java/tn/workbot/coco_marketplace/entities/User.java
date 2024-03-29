package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.workbot.coco_marketplace.entities.enmus.genderType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private LocalDate BirthDate;
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
    //co2 consoummer
    private double co2;

   private  String resetToken;

   public String getResetToken() {
        return resetToken;
    }
        // private String code;

    // Define a field to store the average rating of the user (buyer_seller,deliveryAgency, DeliveryFreelancer)
    private float rating;

    // Define a field to store the number of ratings that have been given for the user(buyer_seller,deliveryAgency, DeliveryFreelancer)
    private int numberOfRatings;
    private String LevelDelivery;
    private Float KilometreConsomer;
    private String FraisEssance;
    private Integer deliveryPoints;
    private Double adsPoints;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
    private List<Order> orders;
    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Store> stores;


    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ClaimSav> claimSavList;

    @OneToMany(mappedBy = "userSender")
    @JsonIgnore
    private List<Review> reviewsSent;

    @OneToMany(mappedBy = "DeliveryAgency")
    @JsonIgnore
    private List<Review> reviewsOnDA;

    @OneToMany(mappedBy = "DeliveryFreelancer")
    @JsonIgnore
    private List<Review> reviewsOnDF;

    @OneToMany(mappedBy = "deliveryman")
    @JsonIgnore
    private List<Request> requestsdeliverymen;

    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private List<Request> requestsellers;
    @JsonIgnore
    @OneToMany(mappedBy = "Agency")
    private List<Request> requestsAgencys;
    @OneToMany(mappedBy = "deliveryAgency")
    private List<AgencyBranch> agencyBranches;


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

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private List<SupplierRequest> supplierRequests;




}


