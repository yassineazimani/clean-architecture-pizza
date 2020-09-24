package com.clean.architecture.pizza.core.ports;

public interface PasswordEncoder {

    String encode(String rawPassword);

    boolean isEquals(String rawPassword, String encodedPassword);

}// PasswordEncoder
