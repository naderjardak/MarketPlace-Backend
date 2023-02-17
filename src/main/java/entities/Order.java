package entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ref;
    private float sum;
    private String orderCode;

    @Enumerated(EnumType.STRING)
    private PaymentType payment;

    @Enumerated(EnumType.STRING)
    private StatusOrderType status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
}
