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
package com.clean.architecture.pizza.core.admin.auth;

import com.clean.architecture.pizza.core.model.UserDTO;
import com.clean.architecture.pizza.core.ports.PasswordEncoder;
import com.clean.architecture.pizza.core.ports.UserRepository;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class FetchUser {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public FetchUser(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }// FetchUser()

    public Optional<UserDTO> findById(int id) {
        return this.userRepository.findById(id);
    }// findById()

    public Optional<UserDTO> findByIdAndPassword(int id, String password) {
        if(StringUtils.isEmpty(password)){
            return Optional.empty();
        }
        Optional<UserDTO> optUserDto = this.findById(id);
        if(!optUserDto.isPresent()){
            return optUserDto;
        }
        UserDTO user = optUserDto.get();
        return passwordEncoder.isEquals(password, user.getPassword()) ? Optional.of(user)
                                                                        : Optional.empty();
    }// findByIdAndPassword()

}// FetchUser
