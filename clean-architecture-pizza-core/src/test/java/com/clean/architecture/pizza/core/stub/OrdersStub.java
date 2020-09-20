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
