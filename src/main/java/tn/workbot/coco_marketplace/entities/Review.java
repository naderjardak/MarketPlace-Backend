package tn.workbot.coco_marketplace.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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



    private int rating;

    private String comment;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    private Date updatedAt;


    @ManyToOne
    private User userSender;

    @ManyToOne
    private User DeliveryAgency;
    @ManyToOne
    private User DeliveryFreelancer;
    @ManyToOne
    private Product product;


    public String hideBadWords(String comment) {

        List<String> badWords = Arrays.asList("bad", "terrible", "awful", "hate", "dislike");

        for (String badWord : badWords) {
            comment = comment.toLowerCase(Locale.ROOT).replaceAll("(?i)" + badWord, "*".repeat(badWord.length()));
        }

        return comment;
    }
}
