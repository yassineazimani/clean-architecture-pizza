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
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StatsOrdersUT {

    private StatsOrders statsOrders;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AuthenticationUser authenticationUser;

    @Before
    public void setUp(){
        this.statsOrders = new StatsOrders(orderRepository, authenticationUser);
    }// setUp()

    @Test
    public void get_sum_total_orders_by_products_should_return_stats_about_orangina_and_lemon_pie() throws AuthenticationException {
        Mockito.when(this.authenticationUser.isAuthenticated())
                .thenReturn(true);
        Mockito.when(this.orderRepository.getTotalSumByProducts())
                .thenReturn(stubStats());
        List<StatsSumOrderTotalByProductsDTO> stats = this.statsOrders.getSumTotalOrdersByProducts();
        Assertions.assertThat(stats).isNotEmpty();
        Assertions.assertThat(stats).hasSize(2);
        StatsSumOrderTotalByProductsDTO stat1 = stats.get(0);
        StatsSumOrderTotalByProductsDTO stat2 = stats.get(1);
        Assertions.assertThat(stat1.getName()).isEqualTo("Orangina");
        Assertions.assertThat(stat1.getTotal()).isEqualTo(28.90);
        Assertions.assertThat(stat2.getName()).isEqualTo("Lemon Pie");
        Assertions.assertThat(stat2.getTotal()).isEqualTo(94.35);
    }// get_sum_total_orders_by_products_should_return_stats_about_orangina_and_lemon_pie()

    public void get_sum_total_orders_by_products_should_throw_authentication_exception_when_user_is_not_logged(){
        Assertions.assertThatCode(() -> this.statsOrders.getSumTotalOrdersByProducts())
                .hasMessage("You need to login")
                .isInstanceOf(AuthenticationException.class);
    }// get_sum_total_orders_by_products_should_throw_authentication_exception_when_user_is_not_logged()

    private List<StatsSumOrderTotalByProductsDTO> stubStats(){
        List<StatsSumOrderTotalByProductsDTO> stats = new ArrayList<>();
        stats.add(new StatsSumOrderTotalByProductsDTO(28.90, "Orangina"));
        stats.add(new StatsSumOrderTotalByProductsDTO(94.35, "Lemon Pie"));
        return stats;
    }// stubStats()

}// StatsOrdersUT
