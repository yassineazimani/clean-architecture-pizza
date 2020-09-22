package clean.architecture.pizza.adapters.secondaries.hibernate.mappers;

import clean.architecture.pizza.adapters.secondaries.hibernate.entities.Product;
import com.clean.architecture.pizza.core.model.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "quantityOrdered", ignore = true)
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO product);

}// ProductMapper