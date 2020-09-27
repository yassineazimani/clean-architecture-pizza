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
package com.clean.architecture.pizza.core.model;

import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private Integer id;

    private Date orderDate;

    private OrderStateEnum orderState;

    private double total;

    private List<ProductDTO> products;

    private String transactionCBId;

    public OrderDTO(){
        this.orderState = OrderStateEnum.PENDING;
        this.orderDate = new Date();
        this.total = 0.;
        this.products = new ArrayList<>();
    }// OrderDTO()

}// OrderDTO
