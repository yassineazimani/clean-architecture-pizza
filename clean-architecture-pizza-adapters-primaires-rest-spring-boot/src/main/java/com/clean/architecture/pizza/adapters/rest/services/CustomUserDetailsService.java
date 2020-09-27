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
package com.clean.architecture.pizza.adapters.rest.services;

import com.clean.architecture.pizza.adapters.rest.model.UserAuthenticated;
import com.clean.architecture.pizza.core.admin.auth.FetchUser;
import com.clean.architecture.pizza.core.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private FetchUser fetchUser;

    @Autowired
    public CustomUserDetailsService(FetchUser fetchUser) {
        this.fetchUser = fetchUser;
    }// CustomUserDetailsService()

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Integer id = Integer.valueOf(username);
        UserDTO user = this.fetchUser.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + username));
        return new UserAuthenticated(
                user.getId(),
                user.getPassword());
    }// loadUserByUsername()

    public UserDetails loadUserById(Integer userID) throws UsernameNotFoundException {
        UserDTO user = this.fetchUser.findById(userID)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userID));
        return new UserAuthenticated(
                user.getId(),
                user.getPassword());
    }// loadUserById()

}// CustomUserDetailsService
