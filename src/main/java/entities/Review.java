package entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //TO DO :Dear Houssem, complete your attributes


    @ManyToOne
    private User buyer_seller;

    @ManyToOne
    private Pickup pickup;

    @ManyToOne
    private Product product;










}
