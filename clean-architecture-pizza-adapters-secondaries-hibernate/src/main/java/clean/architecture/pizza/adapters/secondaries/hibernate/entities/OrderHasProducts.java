package clean.architecture.pizza.adapters.secondaries.hibernate.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "order_has_products")
public class OrderHasProducts {

    @EmbeddedId
    private OrderHasProductsId id;

    private Integer quantity;

    public OrderHasProducts(OrderHasProductsId id) {
        this.id = id;
    }// OrderHasProducts()

}// OrderHasProducts
