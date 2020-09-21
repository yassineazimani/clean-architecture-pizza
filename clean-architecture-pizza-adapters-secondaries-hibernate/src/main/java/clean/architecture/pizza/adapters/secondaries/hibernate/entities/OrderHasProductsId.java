package clean.architecture.pizza.adapters.secondaries.hibernate.entities;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderHasProductsId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orderId;

    private Integer productId;

}// OrderHasProductsId
