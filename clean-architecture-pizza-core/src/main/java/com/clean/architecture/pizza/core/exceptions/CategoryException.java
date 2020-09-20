package com.clean.architecture.pizza.core.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CategoryException extends Exception implements BusinessException{

    @Getter
    @Setter
    private List<String> fieldsErrors;

    public CategoryException(String message) {
        super(message);
        this.fieldsErrors = new ArrayList<>();
    }// CategoryException()

}// CategoryException
