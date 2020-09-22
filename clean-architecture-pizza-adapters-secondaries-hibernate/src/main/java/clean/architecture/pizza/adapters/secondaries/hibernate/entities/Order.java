package clean.architecture.pizza.adapters.secondaries.hibernate.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "ordercmd")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "orderStateId")
    private OrderState orderState;

    private Double total;

    private String transactionCBId;

}// Order
