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
package com.clean.architecture.pizza.adapters.primaries.cli.core.usermode;

import com.clean.architecture.pizza.core.admin.auth.FetchUser;
import com.clean.architecture.pizza.core.model.UserDTO;

import java.util.Optional;

/**
 * Gestion de l'authentification d'un utilisateur
 */
public class AuthTask {

    private FetchUser fetchUser;

    public AuthTask(FetchUser fetchUser) {
        this.fetchUser = fetchUser;
    }// AuthTask

    /**
     * Connecte un utilisateur
     * @param id Identifiant de l'utilisateur
     * @param password Mot de passe de l'utilisateur
     * @return Optional {@see UserDTO}
     */
    public Optional<UserDTO> login(int id, String password){
        return this.fetchUser.findByIdAndPassword(id, password);
    }// login()

}// AuthTask
