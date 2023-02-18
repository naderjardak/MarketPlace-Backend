package entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClaimSav implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String reference;

    private String description;

    private String status;

    private ClaimSavType claimSavType;

    //TO DO :Dear Houssem, complete your attributes

    @ManyToOne
    private User user;




}
