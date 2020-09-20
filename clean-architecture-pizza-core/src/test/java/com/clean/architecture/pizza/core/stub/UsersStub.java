package com.clean.architecture.pizza.core.stub;

import com.clean.architecture.pizza.core.model.UserDTO;

public class UsersStub {

    public static UserDTO getUserAdmin(){
        return new UserDTO(1584690, "password");
    }// getUserAdmin()

}// UsersStub
