package clean.architecture.pizza.adapters.secondaries.hibernate.mappers;

import clean.architecture.pizza.adapters.secondaries.hibernate.entities.Category;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDto(Category cat);

    Category toEntity(CategoryDTO cat);

}// ProductMapper
