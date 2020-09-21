package clean.architecture.pizza.adapters.secondaries.hibernate.mappers;

import clean.architecture.pizza.adapters.secondaries.hibernate.entities.User;
import com.clean.architecture.pizza.core.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User cat);

    User toEntity(UserDTO cat);

}// ProductMapper
