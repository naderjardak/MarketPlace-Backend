package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.workbot.coco_marketplace.entities.enmus.ObjectiveType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ads implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String audiencesGovernorate;
    private String gender;
    private int audiencesAge;
    private LocalDateTime dateCreation;
    private Boolean enabled;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;
    @Enumerated(EnumType.STRING)
    private ObjectiveType objectiveType;
    @JsonIgnore
    @ManyToOne
    private Product product;


}
