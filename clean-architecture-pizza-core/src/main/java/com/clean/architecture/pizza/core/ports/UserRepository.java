package com.clean.architecture.pizza.core.ports;

import com.clean.architecture.pizza.core.model.UserDTO;

import java.util.Optional;

public interface UserRepository {

    Optional<UserDTO> findById(int id);

}// UserRepository
