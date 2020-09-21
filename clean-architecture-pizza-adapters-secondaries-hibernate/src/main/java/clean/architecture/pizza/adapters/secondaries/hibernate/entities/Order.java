package clean.architecture.pizza.adapters.secondaries.hibernate.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(
            name = "order_has_products",
            joinColumns = {@JoinColumn(name = "orderid")},
            inverseJoinColumns = {@JoinColumn(name = "productid")})
    private Set<Product> products = new HashSet<>();

}// Order
