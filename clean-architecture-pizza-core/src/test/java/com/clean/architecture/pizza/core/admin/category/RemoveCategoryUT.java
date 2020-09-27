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
package com.clean.architecture.pizza.core.admin.category;

import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RemoveCategoryUT {

    private RemoveCategory removeCategory;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AuthenticationUser authenticationUser;

    @Before
    public void setUp(){
        this.removeCategory = new RemoveCategory(categoryRepository, authenticationUser);
    }// setUp()

    @Test
    public void remove_category_should_throw_authentication_exception_when_user_isnt_logged() {
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(false);
        Assertions.assertThatCode(() -> removeCategory.execute(1))
                .hasMessage("You need to login")
                .isInstanceOf(AuthenticationException.class);
    }// remove_category_should_throw_authentication_exception_when_user_isnt_logged()

    @Test
    public void remove_category_should_success_when_id_exists() {
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Mockito.when(categoryRepository.existsById(1)).thenReturn(true);
        Assertions.assertThatCode(() -> removeCategory.execute(1)).doesNotThrowAnyException();
    }// remove_category_should_success_when_id_exists()

    @Test
    public void remove_category_should_fail_when_id_doesnt_exists() {
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> removeCategory.execute(1)).doesNotThrowAnyException();
    }// remove_category_should_fail_when_id_doesnt_exists()

}// RemoveCategoryUT
