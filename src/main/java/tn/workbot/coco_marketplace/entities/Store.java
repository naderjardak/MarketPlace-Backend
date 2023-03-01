package tn.workbot.coco_marketplace.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String governorate;
    private String city;
    private String gpsPoint;

    private String IBAN;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    private List<Product> products;

    @ManyToOne
    private User seller;

    @OneToMany(mappedBy = "seller")
    private List<Request> requestsellers;

    @OneToMany(mappedBy = "store")
    private List<Pickup> pickups;

    @OneToMany(mappedBy = "store")
    private List<User> suppliers;
}
