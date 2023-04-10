package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


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
    private String x;
    private String y;


    private String IBAN;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Product> products;

    @ManyToOne
    @JsonIgnore
    private User seller;

    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private List<Request> requestsellers;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<Pickup> pickups;


}
