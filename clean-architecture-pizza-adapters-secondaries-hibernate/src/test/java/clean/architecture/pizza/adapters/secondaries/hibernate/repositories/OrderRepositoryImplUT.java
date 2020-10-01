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
package clean.architecture.pizza.adapters.secondaries.hibernate.repositories;

import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.StatsSumOrderTotalByProductsDTO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class OrderRepositoryImplUT {

    private OrderRepositoryImpl orderRepository;

    private SimpleDateFormat sdf;

    @Before
    public void setUp(){
        this.orderRepository = new OrderRepositoryImpl();
        this.sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }// setUp()

    @Test
    public void find_by_id_should_return_first_order_when_id_is_1(){
        Optional<OrderDTO> optOrder = this.orderRepository.findById(1);
        Assertions.assertThat(optOrder).isPresent();
        optOrder.ifPresent(order -> {
            Assertions.assertThat(order.getId()).isEqualTo(1);
            Assertions.assertThat(this.sdf.format(order.getOrderDate())).isEqualTo("22/09/2020 20:16");
            Assertions.assertThat(order.getOrderState()).isEqualTo(OrderStateEnum.PENDING);
            Assertions.assertThat(order.getTotal()).isEqualTo(35.6);
            Assertions.assertThat(order.getTransactionCBId()).isNull();
            Assertions.assertThat(order.getProducts()).isEmpty();
        });
    }// find_by_id_should_return_first_order_when_id_is_1()

    @Test
    public void find_by_33_should_return_order_33_when_id_is_33(){
        Optional<OrderDTO> optOrder = this.orderRepository.findById(33);
        Assertions.assertThat(optOrder).isPresent();
        optOrder.ifPresent(order -> {
            Assertions.assertThat(order.getId()).isEqualTo(33);
            Assertions.assertThat(this.sdf.format(order.getOrderDate())).isEqualTo("22/09/2020 22:35");
            Assertions.assertThat(order.getOrderState()).isEqualTo(OrderStateEnum.SUCCESS);
            Assertions.assertThat(order.getTotal()).isEqualTo(42.8);
            Assertions.assertThat(order.getTransactionCBId()).isEqualTo("iQH0UzRoLDdLn06");
            Assertions.assertThat(order.getProducts()).hasSize(3);
            boolean pizzaFourCheeseOrdered = order.getProducts()
                    .stream()
                    .anyMatch(p -> p.getId().equals(1) && p.getQuantityOrdered() == 3);
            boolean pizzaVegetarianOrdered = order.getProducts()
                    .stream()
                    .anyMatch(p -> p.getId().equals(2) && p.getQuantityOrdered() == 1);
            boolean pizzaPeperroniOrdered = order.getProducts()
                    .stream()
                    .anyMatch(p -> p.getId().equals(4) && p.getQuantityOrdered() == 1);
            Assertions.assertThat(pizzaFourCheeseOrdered).isTrue();
            Assertions.assertThat(pizzaVegetarianOrdered).isTrue();
            Assertions.assertThat(pizzaPeperroniOrdered).isTrue();
        });
    }// find_by_33_should_return_order_33_when_id_is_33()

    @Test
    public void find_by_id_should_return_empty_order_when_id_is_6000(){
        Optional<OrderDTO> optOrder = this.orderRepository.findById(6000);
        Assertions.assertThat(optOrder).isNotPresent();
    }// find_by_id_should_return_empty_order_when_id_is_6000()

    @Test
    public void save_should_throw_argument_missing_exception_when_order_is_null() {
        OrderDTO order = null;
        Assertions.assertThatCode(() -> this.orderRepository.save(order))
                .hasMessage("Order is null")
                .isInstanceOf(ArgumentMissingException.class);
    }// save_should_throw_argument_missing_exception_when_order_is_null()

    @Test
    public void update_should_throw_argument_missing_exception_when_order_is_null() {
        OrderDTO order = null;
        Assertions.assertThatCode(() -> this.orderRepository.update(order))
                .hasMessage("Order is null")
                .isInstanceOf(ArgumentMissingException.class);
    }// update_should_throw_argument_missing_exception_when_order_is_null()

    @Test
    public void get_total_sum_by_products_should_return_6_sums(){
        List<StatsSumOrderTotalByProductsDTO> totalSumByProducts = this.orderRepository.getTotalSumByProducts();
        Assertions.assertThat(totalSumByProducts).hasSize(6);
        boolean isOranginaStat = totalSumByProducts.stream()
                .anyMatch(o -> "Orangina".equals(o.getName()) && o.getTotal() == 11.1);
        boolean isFishPizzaStat = totalSumByProducts.stream()
                .anyMatch(o -> "Fish pizza".equals(o.getName()) && o.getTotal() >= 19.2 && o.getTotal() <= 19.3);
        boolean isPeperroniPizzaStat = totalSumByProducts.stream()
                .anyMatch(o -> "Pepperoni pizza".equals(o.getName()) && o.getTotal() == 42.8);
        boolean isVegetarianPizzaStat = totalSumByProducts.stream()
                .anyMatch(o -> "Vegetarian pizza".equals(o.getName()) && o.getTotal() == 42.8);
        boolean isCocaStat = totalSumByProducts.stream()
                .anyMatch(o -> "Coca-cola".equals(o.getName()) && o.getTotal() == 50.1);
        boolean isFourCheesePizzaStat = totalSumByProducts.stream()
                .anyMatch(o -> "Four-cheese pizza".equals(o.getName()) && o.getTotal() == 202.6);

        Assertions.assertThat(isOranginaStat).isTrue();
        Assertions.assertThat(isFishPizzaStat).isTrue();
        Assertions.assertThat(isPeperroniPizzaStat).isTrue();
        Assertions.assertThat(isVegetarianPizzaStat).isTrue();
        Assertions.assertThat(isCocaStat).isTrue();
        Assertions.assertThat(isFourCheesePizzaStat).isTrue();
    }// get_total_sum_by_products_should_return_6_sums()

}// OrderRepositoryImplUT
