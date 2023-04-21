package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.workbot.coco_marketplace.entities.enmus.BudgetType;
import tn.workbot.coco_marketplace.entities.enmus.ObjectiveType;
import tn.workbot.coco_marketplace.entities.enmus.genderType;

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
    @Enumerated(EnumType.STRING)
    private genderType gender;
    private int audiencesAgeMin;
    private int audiencesAgeMax;
    public float adsPoints;
    private LocalDateTime dateCreation;
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private BudgetType budgetType;
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
