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
package com.clean.architecture.pizza.core.order;

import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.order.OrderProducts;
import com.clean.architecture.pizza.core.enums.MoneyEnum;
import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.OrderException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.CoinsInfrastructure;
import com.clean.architecture.pizza.core.ports.OrderRepository;
import com.clean.architecture.pizza.core.ports.ProductRepository;
import com.clean.architecture.pizza.core.stub.OrdersStub;
import com.clean.architecture.pizza.core.stub.ProductsStub;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class OrderProductsUT {

    private OrderProducts orderProducts;

    @Mock
    private CoinsInfrastructure coinsInfrastructure;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    private final String TRANSACTION_CB_ID = "cb_xoardf2423434ofkmarzavtrabo1";

    @Before
    public void setUp(){
        this.orderProducts = new OrderProducts(orderRepository, productRepository, coinsInfrastructure);
    }// setUp()

    @Test
    public void get_e_commerce_cart_should_be_empty_when_no_products_added(){
        Set<ProductDTO> eCommerceCart = orderProducts.getECommerceCart();
        Assertions.assertThat(eCommerceCart).isEmpty();
    }// get_e_commerce_cart_should_be_empty_when_no_products_added()

    @Test
    public void add_product_in_ecommerce_cart_without_product_should_throw_argument_missing_exception(){
        Assertions.assertThatCode(() -> orderProducts.addProduct(null))
                .hasMessage("Product argument is null")
                .isInstanceOf(ArgumentMissingException.class);
    }// add_product_in_ecommerce_cart_without_product_should_throw_argument_missing_exception()


    @Test
    public void add_product_should_throw_order_exception_when_quantity_asked_gt_quantity_available(){
        ProductDTO pizzaFromDB = ProductsStub.getPizza4Fromages(1);
        ProductDTO pizzaWanted = ProductsStub.getPizza4Fromages(1);
        pizzaWanted.setQuantityOrdered(2);
        Mockito.when(this.productRepository.findById(pizzaWanted.getId()))
                .thenReturn(Optional.of(pizzaFromDB));
        Assertions.assertThatCode(() -> orderProducts.addProduct(pizzaWanted))
                .hasMessage("The quantity asked is greater than the quantity available")
                .isInstanceOf(OrderException.class);
    }// add_product_should_throw_order_exception_when_quantity_asked_gt_quantity_available()

    @Test
    public void add_product_in_ecommerce_cart_with_product_should_success(){
        ProductDTO pizza = ProductsStub.getPizza4Fromages(3);
        pizza.setQuantityOrdered(1);
        Mockito.when(productRepository.findById(pizza.getId())).thenReturn(Optional.of(pizza));
        Assertions.assertThatCode(() -> orderProducts.addProduct(pizza))
                .doesNotThrowAnyException();
        Set<ProductDTO> eCommerceCart = orderProducts.getECommerceCart();
        Assertions.assertThat(eCommerceCart).isNotEmpty();
        Assertions.assertThat(eCommerceCart).hasSize(1);
        ProductDTO productInECart = eCommerceCart.iterator().next();
        Assertions.assertThat(productInECart.getId()).isEqualTo(1);
        Assertions.assertThat(productInECart.getName()).isEqualTo("Pizza 4 fromages");
        Assertions.assertThat(productInECart.getPrice()).isEqualTo(8.90);
        Assertions.assertThat(productInECart.getDescription()).isEqualTo("Pizza incontournable de toute pizzeria qui se respecte ! Son comté, son bleu, sa chèvre et son emmental lui donnent un goût exquis !");
        Assertions.assertThat(productInECart.getQuantityAvailable()).isGreaterThan(0);
        Assertions.assertThat(productInECart.getCategory().getId()).isEqualTo(1);
        Assertions.assertThat(productInECart.getCategory().getName()).isEqualTo("Pizzas");
    }// add_product_in_ecommerce_cart_should_success()

    @Test
    public void get_total_should_return_0_when_ecommerce_cart_is_empty() throws OrderException, ArgumentMissingException {
        double total = orderProducts.getTotal();
        Assertions.assertThat(total).isEqualTo(0.0);
    }// get_total_should_return_0_when_ecommerce_cart_is_empty()

    @Test
    public void get_total_should_return_20_7_when_2_pizza_4_fromages_and_orangina_are_ordered() throws OrderException, ArgumentMissingException, DatabaseException {
        ProductDTO pizza = ProductsStub.getPizza4Fromages(3);
        pizza.setQuantityOrdered(2);
        ProductDTO orangina = ProductsStub.getOrangina(2);
        orangina.setQuantityOrdered(1);
        OrderDTO order = OrdersStub.getSingleProductOrder(true, OrderStateEnum.PENDING);
        Mockito.when(orderRepository.save(Mockito.any(OrderDTO.class))).thenReturn(order);
        Mockito.when(productRepository.findById(orangina.getId())).thenReturn(Optional.of(orangina));
        Mockito.when(productRepository.findById(pizza.getId())).thenReturn(Optional.of(pizza));
        orderProducts.addProduct(pizza);
        orderProducts.addProduct(orangina);
        double total = orderProducts.getTotal();
        Assertions.assertThat(total).isEqualTo(20.7);
    }// get_total_should_return_20_7_when_2_pizza_4_fromages_and_orangina_are_ordered()

    @Test
    public void remove_product_from_ecommerce_cart_with_id_product_to_remove_should_success(){
        ProductDTO pizza = ProductsStub.getPizza4Fromages(3);
        pizza.setQuantityOrdered(1);
        Mockito.when(productRepository.findById(pizza.getId())).thenReturn(Optional.of(pizza));
        Assertions.assertThatCode(() -> orderProducts.addProduct(pizza))
                .doesNotThrowAnyException();
        Assertions.assertThatCode(() -> orderProducts.removeProduct(1))
                .doesNotThrowAnyException();
        Set<ProductDTO> eCommerceCart = orderProducts.getECommerceCart();
        Assertions.assertThat(eCommerceCart).isEmpty();
    }// remove_product_from_ecommerce_cart_with_id_product_to_remove_should_success()

    @Test
    public void remove_product_from_ecommerce_cart_with_unknown_id_product_to_remove_should_throw_order_exception(){
        Assertions.assertThatCode(() -> orderProducts.removeProduct(10))
                .hasMessage("Product with id 10 doesn't exist")
                .isInstanceOf(OrderException.class);
    }// remove_product_from_ecommerce_cart_with_unknown_id_product_to_remove_should_throw_order_exception()

    @Test
    public void clear_ecommerce_cart_should_clear_ecommerce_cart_when_it_s_not_empty() throws ArgumentMissingException, OrderException, DatabaseException {
        ProductDTO pizza = ProductsStub.getPizza4Fromages(3);
        pizza.setQuantityOrdered(1);
        Mockito.when(productRepository.findById(pizza.getId())).thenReturn(Optional.of(pizza));
        orderProducts.addProduct(pizza);
        orderProducts.clearECommerceCart();
        Assertions.assertThat(orderProducts.getECommerceCart()).isEmpty();
    }// clear_ecommerce_cart_should_clear_ecommerce_cart_when_it_s_not_empty()

    @Test
    public void payment_order_should_throw_order_exception_when_ecommerce_cart_is_empty(){
        Assertions.assertThatCode(() -> orderProducts.paymentOrder(MoneyEnum.COINS))
                .isInstanceOf(OrderException.class)
                .hasMessage("ECommerce cart is empty");
    }// payment_order_with_coins_should_throw_order_exception_when_ecommerce_cart_is_empty()

    @Test
    public void payment_order_should_throw_order_exception_when_no_mean_payment_given() throws ArgumentMissingException, OrderException, DatabaseException {
        ProductDTO pizza = ProductsStub.getPizza4Fromages(3);
        pizza.setQuantityOrdered(1);
        Mockito.when(productRepository.findById(pizza.getId())).thenReturn(Optional.of(pizza));
        orderProducts.addProduct(pizza);
        Assertions.assertThatCode(() -> orderProducts.paymentOrder(null))
                .hasMessage("MoneyEnum is null")
                .isInstanceOf(OrderException.class);
        orderProducts.clearECommerceCart();
    }// payment_order_should_throw_order_exception_when_no_mean_payment_given()

    @Test
    public void payment_order_with_coins_should_success_and_reduce_quantity_available_when_ecommerce_cart_is_not_empty() throws ArgumentMissingException, OrderException, DatabaseException {
        OrderDTO order = OrdersStub.getSingleProductOrder(true, OrderStateEnum.PENDING);
        ProductDTO pizza = ProductsStub.getPizza4Fromages(3);
        pizza.setQuantityOrdered(1);
        Mockito.when(orderRepository.save(Mockito.any(OrderDTO.class)))
                .thenReturn(order);
        Mockito.when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));
        Mockito.when(productRepository.findById(pizza.getId()))
                .thenReturn(Optional.of(pizza));
        orderProducts.addProduct(pizza);
        Assertions.assertThatCode(() -> orderProducts.paymentOrder(MoneyEnum.COINS))
                .doesNotThrowAnyException();
        Optional<ProductDTO> optPizzaFromBD = productRepository.findById(pizza.getId());
        Assertions.assertThat(optPizzaFromBD).isPresent();
        optPizzaFromBD.ifPresent(p -> Assertions.assertThat(p.getQuantityAvailable()).isEqualTo(2));
    }// payment_order_with_coins_should_success_and_reduce_quantity_available_when_ecommerce_cart_is_not_empty()

    @Test
    public void payment_order_with_cb_should_throw_order_exception_when_transaction_cb_id_is_null_or_empty() throws ArgumentMissingException, OrderException, DatabaseException {
        OrderDTO order = OrdersStub.getSingleProductOrder(true, OrderStateEnum.PENDING);
        ProductDTO pizza = ProductsStub.getPizza4Fromages(3);
        pizza.setQuantityOrdered(1);
        Mockito.when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(Mockito.any(OrderDTO.class)))
                .thenReturn(order);
        Mockito.when(productRepository.findById(pizza.getId())).thenReturn(Optional.of(pizza));
        orderProducts.addProduct(pizza);
        Assertions.assertThatCode(() -> orderProducts.paymentOrder(MoneyEnum.CB))
                .hasMessage("Impossible to finalize order for CB payment")
                .isInstanceOf(OrderException.class);
        orderProducts.clearECommerceCart();
    }// payment_order_with_cb_should_success_and_reduce_quantity_available_when_ecommerce_cart_is_not_empty()

    @Test
    public void payment_order_with_cb_should_success_and_reduce_quantity_available_when_ecommerce_cart_is_not_empty() throws ArgumentMissingException, OrderException, DatabaseException {
        OrderDTO order = OrdersStub.getSingleProductOrder(true, OrderStateEnum.PENDING);
        ProductDTO pizza = ProductsStub.getPizza4Fromages(3);
        pizza.setQuantityOrdered(1);
        Mockito.when(orderRepository.save(Mockito.any(OrderDTO.class)))
                .thenReturn(order);
        Mockito.when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));
        Mockito.when(productRepository.findById(pizza.getId()))
                .thenReturn(Optional.of(pizza));
        orderProducts.addProduct(pizza);
        Assertions.assertThatCode(() -> orderProducts.paymentOrder(MoneyEnum.CB, TRANSACTION_CB_ID))
                .doesNotThrowAnyException();
        Optional<ProductDTO> optPizzaFromBD = productRepository.findById(pizza.getId());
        Assertions.assertThat(optPizzaFromBD).isPresent();
        optPizzaFromBD.ifPresent(p -> Assertions.assertThat(p.getQuantityAvailable()).isEqualTo(2));
    }// payment_order_with_cb_should_success_and_reduce_quantity_available_when_ecommerce_cart_is_not_empty()

    @Test
    public void cancel_order_should_success_when_ecommerce_cart_is_empty() throws DatabaseException, ArgumentMissingException {
        OrderDTO order = OrdersStub.getSingleProductOrder(true, OrderStateEnum.CANCELLED);
        Mockito.when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));
        OrderDTO orderCancelled = OrdersStub.getSingleProductOrder(true, OrderStateEnum.CANCELLED);
        Mockito.when(orderRepository.save(Mockito.any(OrderDTO.class)))
                .thenReturn(orderCancelled);
        Assertions.assertThatCode(() -> orderProducts.cancelOrder())
                .doesNotThrowAnyException();
        Assertions.assertThat(orderProducts.getECommerceCart()).isEmpty();
        Optional<OrderDTO> optOrder = orderRepository.findById(1);
        Assertions.assertThat(optOrder).isPresent();
        optOrder.ifPresent(o -> {
            Assertions.assertThat(order.getProducts()).hasSize(1);
            Assertions.assertThat(order.getOrderState()).isEqualTo(OrderStateEnum.CANCELLED);
        });
    }// cancel_order_should_success_when_ecommerce_cart_is_empty()

    @Test
    public void cancel_order_should_success_when_ecommerce_cart_is_not_empty() throws DatabaseException, ArgumentMissingException {
        OrderDTO order = OrdersStub.getSingleProductOrder(true, OrderStateEnum.CANCELLED);
        Mockito.when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));
        OrderDTO orderCancelled = OrdersStub.getSingleProductOrder(true, OrderStateEnum.CANCELLED);
        Mockito.when(orderRepository.save(Mockito.any(OrderDTO.class)))
                .thenReturn(orderCancelled);
        Assertions.assertThatCode(() -> orderProducts.cancelOrder())
                .doesNotThrowAnyException();
        Assertions.assertThat(orderProducts.getECommerceCart()).isEmpty();
        Optional<OrderDTO> optOrder = orderRepository.findById(1);
        Assertions.assertThat(optOrder).isPresent();
        optOrder.ifPresent(o -> {
            Assertions.assertThat(order.getProducts()).hasSize(1);
            Assertions.assertThat(order.getOrderState()).isEqualTo(OrderStateEnum.CANCELLED);
        });
    }// cancel_order_should_success_when_ecommerce_cart_is_not_empty()

}// CommandProductsUT
