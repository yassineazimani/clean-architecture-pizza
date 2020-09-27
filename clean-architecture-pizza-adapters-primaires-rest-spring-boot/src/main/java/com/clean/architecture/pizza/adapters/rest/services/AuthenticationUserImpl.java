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

import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Implémentation du port AuthenticationUser
 */
public class AuthenticationUserImpl implements AuthenticationUser {

    /**
     * Contrôle si l'administrateur est connecté.
     * @return
     */
    @Override
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return auth != null;
    }// isAuthenticated()

}// AuthenticationUserImpl()
