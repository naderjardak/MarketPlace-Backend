package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @GeneratedValue
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

    @OneToMany(mappedBy = "buyer_seller")
    private List<Review> reviews;


}


