package com.clean.architecture.pizza.core.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ProductException extends Exception implements BusinessException{

    @Getter
    @Setter
    private List<String> fieldsErrors;

    public ProductException(String message) {
        super(message);
        this.fieldsErrors = new ArrayList<>();
    }// ProductException()

}// ProductException
