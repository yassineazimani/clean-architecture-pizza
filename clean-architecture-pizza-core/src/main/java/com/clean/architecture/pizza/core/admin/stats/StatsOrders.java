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

    /**
     * Récupère les sommes de toutes les commandes en fonction de chaque
     * produit les composant. Attention : Cette fonction ne retourne
     * pas les produits les plus vendus en fonction de leurs sommes !
     * @return
     * @throws AuthenticationException
     */
    public List<StatsSumOrderTotalByProductsDTO> getSumTotalOrdersByProducts() throws AuthenticationException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        return this.orderRepository.getTotalSumByProducts();
    }// getSumTotalOrdersByProducts()

}// StatsOrders
