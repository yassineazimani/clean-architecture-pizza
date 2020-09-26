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
