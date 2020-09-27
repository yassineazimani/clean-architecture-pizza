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
package com.clean.architecture.pizza.core.stub;

import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;

import java.util.Collections;
import java.util.Date;

public class OrdersStub {

    public static OrderDTO getSingleProductOrder(boolean withId, OrderStateEnum orderStateEnum){
        ProductDTO pizza = ProductsStub.getPizza4Fromages(4);
        OrderDTO order = new OrderDTO();
        if(withId){
            order.setId(1);
        }
        order.setProducts(Collections.singletonList(pizza));
        order.setOrderState(orderStateEnum);
        order.setOrderDate(new Date());
        order.setTotal(pizza.getPrice());
        return order;
    }// getSingleProductOrder()

}
