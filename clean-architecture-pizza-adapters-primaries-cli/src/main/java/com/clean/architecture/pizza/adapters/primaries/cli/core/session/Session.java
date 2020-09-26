package com.clean.architecture.pizza.adapters.primaries.cli.core.session;

import com.clean.architecture.pizza.core.model.UserDTO;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import lombok.Getter;
import lombok.Setter;

/**
 * Implémentation du port AuthenticationUser
 */
public class Session implements AuthenticationUser {

    @Getter
    @Setter
    private UserDTO userLogged;

    /**
     * Indique si l'utilisateur est authentifié.
     * @return boolean
     */
    @Override
    public boolean isAuthenticated() {
        return this.userLogged != null;
    }// isAuthenticated()

}// Session()
