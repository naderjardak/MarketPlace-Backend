package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String bandLing;

    @Temporal(TemporalType.DATE)
    private Date StartDate;

    @Temporal(TemporalType.DATE)
    private Date lastDate;

    @OneToMany
    private List<Product> productList;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany
    private List<KeyWords> listkeyWords;
}
