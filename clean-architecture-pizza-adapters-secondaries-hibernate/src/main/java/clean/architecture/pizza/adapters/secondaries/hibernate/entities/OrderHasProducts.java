package clean.architecture.pizza.adapters.secondaries.hibernate.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "order_has_products")
public class OrderHasProducts {

    @EmbeddedId
    private OrderHasProductsId id = new OrderHasProductsId();

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private Integer quantity;

}// OrderHasProducts
