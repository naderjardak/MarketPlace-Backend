package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AgencyBranch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String governorate;
    private String city;
    private String gpsPoint;
    private String BrandName;
    @JsonIgnore
    @ManyToOne
    User deliveryAgency;
    @JsonIgnore
    @OneToMany(mappedBy = "agencyBranch",cascade = CascadeType.ALL)
    private List<AgencyDeliveryMan> agencyDeliveryMEN;



}
