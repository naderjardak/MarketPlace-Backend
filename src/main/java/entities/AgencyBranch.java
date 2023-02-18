package entities;

import lombok.*;

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

    @ManyToOne
    User deliveryAgency;

    @OneToMany(mappedBy = "agencyBranch")
    private List<AgencyDeliveryMan> agencyDeliveryMEN;



}
