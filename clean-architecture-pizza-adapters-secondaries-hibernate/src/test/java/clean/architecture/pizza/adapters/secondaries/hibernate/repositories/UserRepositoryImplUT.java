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

import com.clean.architecture.pizza.core.exceptions.TransactionException;
import com.clean.architecture.pizza.core.model.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

@RunWith(JUnit4.class)
public class UserRepositoryImplUT {

    private static UserRepositoryImpl userRepository;

    @BeforeClass
    public static void setUp(){
        userRepository = new UserRepositoryImpl();
    }// setUp()

    @Test
    public void find_by_id_should_return_user_when_id_is_109856(){
        try {
            this.userRepository.begin();
            Optional<UserDTO> optUser = userRepository.findById(109856);
            Assertions.assertThat(optUser).isPresent();
            optUser.ifPresent(userDTO -> {
                Assertions.assertThat(userDTO.getId()).isEqualTo(109856);
                Assertions.assertThat(userDTO.getPassword()).isEqualTo("$2a$12$boWhkrsHVdNZhQIgQCq/sum1IV.Wp1SIb9rJMxsAue6TkT5Fge9je");
            });
            this.userRepository.commit();
        }catch (TransactionException te){
            this.userRepository.rollback();
        }
    }// find_by_id_should_return_user_when_id_is_109856()

    @Test
    public void find_by_id_should_empty_optional_when_id_is_1(){
        try {
            this.userRepository.begin();
            Optional<UserDTO> optUser = userRepository.findById(1);
            Assertions.assertThat(optUser).isNotPresent();
            this.userRepository.commit();
        }catch (TransactionException te){
            this.userRepository.rollback();
        }
    }// find_by_id_should_empty_optional_when_id_is_1()

}// UserRepositoryImplUT
