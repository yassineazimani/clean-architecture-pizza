package clean.architecture.pizza.adapters.secondaries.hibernate.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "state")
public class OrderState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

}// OrderState
