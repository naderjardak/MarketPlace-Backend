package entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Role implements Serializable {


    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType type;



}
