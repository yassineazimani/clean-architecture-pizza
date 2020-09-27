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
package com.clean.architecture.pizza.core.admin.product;

import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.ProductException;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RemoveProductUT {

    private RemoveProduct removeProduct;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthenticationUser authenticationUser;

    @Before
    public void setUp(){
        this.removeProduct = new RemoveProduct(productRepository, authenticationUser);
    }// setUp()

    @Test
    public void remove_product_should_throw_authentication_exception_when_user_isnt_logged(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(false);
        Assertions.assertThatCode(() -> removeProduct.execute(1))
                .hasMessage("You need to login")
                .isInstanceOf(AuthenticationException.class);
    }// remove_product_should_throw_authentication_exception_when_user_isnt_logged()

    @Test
    public void remove_product_should_success_when_id_exists() throws AuthenticationException, DatabaseException, ProductException {
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Mockito.when(productRepository.existsById(1)).thenReturn(true);
        Assertions.assertThat(removeProduct.execute(1)).isTrue();
    }// remove_product_should_success_when_id_exists()

    @Test
    public void remove_product_should_fail_when_id_doesnt_exists() throws AuthenticationException, DatabaseException, ProductException {
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThat(removeProduct.execute(2)).isFalse();
    }// remove_product_should_fail_when_id_doesnt_exists()

}// RemoveCategoryUT
