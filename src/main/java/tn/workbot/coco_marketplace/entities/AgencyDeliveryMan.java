package tn.workbot.coco_marketplace.entities;

import lombok.*;
import tn.workbot.coco_marketplace.entities.enmus.TypeOfGear;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AgencyDeliveryMan implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private  Long id;
    private  String firstName;
    private  String lastName;
    private  int cin;
    private  String gearv;
    private  String governorate;
    private  String city;
    private  String gearmatricuel;
    @Enumerated(EnumType.STRING)
    private TypeOfGear typeOfGear;

    @ManyToOne
    private AgencyBranch agencyBranch;

    @OneToMany(mappedBy = "agencyDeliveryMan")
    private List<Pickup> pickups;



}
