package clean.architecture.pizza.adapters.secondaries.hibernate.repositories;

import clean.architecture.pizza.adapters.secondaries.hibernate.config.AbstractRepository;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.User;
import clean.architecture.pizza.adapters.secondaries.hibernate.mappers.UserMapper;
import com.clean.architecture.pizza.core.model.UserDTO;
import com.clean.architecture.pizza.core.ports.UserRepository;

import java.util.Optional;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    @Override
    public Optional<UserDTO> findById(int id) {
        User user = this.entityManager.find(User.class, id);
        if(user == null){
            return Optional.empty();
        }
        this.entityManager.refresh(user);
        return Optional.of(UserMapper.INSTANCE.toDto(user));
    }// findById()

}// UserRepositoryImpl
