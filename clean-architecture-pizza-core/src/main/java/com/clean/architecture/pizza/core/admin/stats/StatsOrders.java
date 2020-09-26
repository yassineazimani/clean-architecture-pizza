package com.clean.architecture.pizza.core.admin.stats;

import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.model.StatsSumOrderTotalByProductsDTO;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.OrderRepository;

import java.util.Collections;
import java.util.List;

public class StatsOrders {

    private OrderRepository orderRepository;

    private AuthenticationUser authenticationUser;

    public StatsOrders(OrderRepository orderRepository,
                       AuthenticationUser authenticationUser) {
        this.orderRepository = orderRepository;
        this.authenticationUser = authenticationUser;
    }// StatsOrders

    public List<StatsSumOrderTotalByProductsDTO> getSumTotalOrdersByProducts() throws AuthenticationException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        return this.orderRepository.getTotalSumByProducts();
    }// getSumTotalOrdersByProducts()

}// StatsOrders
