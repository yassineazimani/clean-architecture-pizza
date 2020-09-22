package clean.architecture.pizza.adapters.secondaries.hibernate.mappers;

import clean.architecture.pizza.adapters.secondaries.hibernate.entities.Order;
import com.clean.architecture.pizza.core.model.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(ignore = true, target = "orderState")
    @Mapping(ignore = true, target = "products")
    OrderDTO toDto(Order order);

    @Mapping(ignore = true, target = "orderState")
    Order toEntity(OrderDTO order);

}// ProductMapper
