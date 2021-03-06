/*
 * Copyright 2020 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package clean.architecture.pizza.adapters.secondaries.hibernate.repositories;

import clean.architecture.pizza.adapters.secondaries.hibernate.config.AbstractRepository;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.User;
import clean.architecture.pizza.adapters.secondaries.hibernate.mappers.UserMapper;
import com.clean.architecture.pizza.core.model.UserDTO;
import com.clean.architecture.pizza.core.ports.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    @Override
    public Optional<UserDTO> findById(int id) {
        User user = this.entityManager.find(User.class, id);
        if(user == null){
            return Optional.empty();
        }
        try {
            this.entityManager.refresh(user);
            return Optional.of(UserMapper.INSTANCE.toDto(user));
        }catch(EntityNotFoundException e){
            return Optional.empty();
        }
    }// findById()

}// UserRepositoryImpl
