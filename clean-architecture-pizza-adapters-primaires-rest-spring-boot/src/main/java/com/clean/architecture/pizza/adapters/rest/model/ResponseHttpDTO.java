/*
 * Copyright 2020 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clean.architecture.pizza.adapters.rest.model;

public class ResponseHttpDTO {

    /**
     * Représente le résultat renvoyé par le endpoint
     */
    private Object value;

    /**
     * Représente la liste des erreurs renvoyées par le endpoint
     */
    private Object errors;

    public ResponseHttpDTO() {}

    public ResponseHttpDTO(Object value, Object errors) {
        this.value = value;
        this.errors = errors;
    }// ResponseHttpDTO()

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

}// ResponseHttpDTO
