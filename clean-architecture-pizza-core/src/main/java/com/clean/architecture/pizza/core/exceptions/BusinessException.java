package com.clean.architecture.pizza.core.exceptions;

import java.util.List;

public interface BusinessException {

    void setFieldsErrors(List<String> errors);

    List<String> getFieldsErrors();

}// BusinessException
