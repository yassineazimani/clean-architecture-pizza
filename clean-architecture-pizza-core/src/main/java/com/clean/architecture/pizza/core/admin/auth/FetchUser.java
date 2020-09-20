package com.clean.architecture.pizza.core.admin.auth;

import com.clean.architecture.pizza.core.model.UserDTO;
import com.clean.architecture.pizza.core.ports.UserRepository;

import java.util.Optional;

public class FetchUser {

    private UserRepository userRepository;

    public FetchUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }// FetchUser()

    public Optional<UserDTO> findById(int id) {
        return this.userRepository.findById(id);
    }// findById()

}// FetchUser
