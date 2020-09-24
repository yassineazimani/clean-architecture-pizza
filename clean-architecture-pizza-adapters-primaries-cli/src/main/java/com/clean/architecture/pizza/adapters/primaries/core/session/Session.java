package com.clean.architecture.pizza.adapters.primaries.core.session;

import com.clean.architecture.pizza.core.model.UserDTO;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import lombok.Getter;
import lombok.Setter;

public class Session implements AuthenticationUser {

    @Getter
    @Setter
    private UserDTO userLogged;

    @Override
    public boolean isAuthenticated() {
        return this.userLogged != null;
    }// isAuthenticated()

}// Session()
