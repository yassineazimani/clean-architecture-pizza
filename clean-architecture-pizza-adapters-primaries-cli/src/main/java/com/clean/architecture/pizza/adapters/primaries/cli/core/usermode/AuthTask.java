package com.clean.architecture.pizza.adapters.primaries.cli.core.usermode;

import com.clean.architecture.pizza.core.admin.auth.FetchUser;
import com.clean.architecture.pizza.core.model.UserDTO;

import java.util.Optional;

public class AuthTask {

    private FetchUser fetchUser;

    public AuthTask(FetchUser fetchUser) {
        this.fetchUser = fetchUser;
    }// AuthTask

    public Optional<UserDTO> login(int id, String password){
        return this.fetchUser.findByIdAndPassword(id, password);
    }// login()

}// AuthTask
