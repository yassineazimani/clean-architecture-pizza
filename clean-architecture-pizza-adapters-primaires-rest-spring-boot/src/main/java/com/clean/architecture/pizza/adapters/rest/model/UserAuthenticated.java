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
package com.clean.architecture.pizza.adapters.rest.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserAuthenticated implements UserDetails {

    private Integer id;

    private String password;

    public UserAuthenticated(Integer id, String password) {
        this.id = id;
        this.password = password;
    }// UserAuthenticated()

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }// getAuthorities()

    @Override
    public String getPassword() {
        return this.password;
    }// getPassword()

    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }// getUsername()

    public Integer getId(){
        return this.id;
    }// getId()

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }// isAccountNonExpired()

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }// isAccountNonLocked()

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }// isCredentialsNonExpired()

    @Override
    public boolean isEnabled() {
        return true;
    }// isEnabled()

}// UserAuthenticated
