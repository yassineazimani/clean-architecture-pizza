package com.clean.architecture.pizza.core.admin.category;

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.CategoryException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PersistCategoryUT {

    private PersistCategory persistCategory;

    @Mock
    private AuthenticationUser authenticationUser;

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp(){
        this.persistCategory = new PersistCategory(authenticationUser, categoryRepository);
    }// setUp()

    @Test
    public void save_category_should_throw_authentication_exception_when_user_isnt_logged(){
        Assertions.assertThatCode(() -> persistCategory.save(new CategoryDTO(1, "Pizzas")))
                .hasMessage("You need to login")
                .isInstanceOf(AuthenticationException.class);
    }// save_category_should_throw_authentication_exception_when_user_isnt_logged()

    @Test
    public void save_category_should_throw_category_exception_when_category_is_null(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistCategory.save(null))
                .hasMessage("Category is null")
                .isInstanceOf(ArgumentMissingException.class);
    }// save_category_should_throw_category_exception_when_category_is_null()

    @Test
    public void save_category_should_throw_category_exception_when_fields_mandatory_are_missing(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistCategory.save(new CategoryDTO()))
                .hasMessage("Mandatory fields are missing")
                .isInstanceOf(CategoryException.class);
    }// save_category_should_throw_category_exception_when_fields_mandatory_are_missing()

    @Test
    public void save_category_should_success_when_user_is_logged_and_fields_are_given(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistCategory.save(new CategoryDTO(null, "Pizzas")))
                .doesNotThrowAnyException();
    }// save_category_should_success_when_user_is_logged_and_fields_are_given()

    @Test
    public void update_category_should_throw_authentication_exception_when_user_isnt_logged(){
        Assertions.assertThatCode(() -> persistCategory.update(new CategoryDTO(1, "Pizzas")))
                .hasMessage("You need to login")
                .isInstanceOf(AuthenticationException.class);
    }// update_category_should_throw_authentication_exception_when_user_isnt_logged()

    @Test
    public void update_category_should_throw_category_exception_when_category_is_null(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistCategory.update(null))
                .hasMessage("Category is null")
                .isInstanceOf(ArgumentMissingException.class);
    }// update_category_should_throw_category_exception_when_category_is_null()

    @Test
    public void update_category_should_throw_category_exception_when_fields_mandatory_are_missing(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistCategory.update(new CategoryDTO(1, null)))
                .hasMessage("Mandatory fields are missing")
                .isInstanceOf(CategoryException.class);
        Assertions.assertThatCode(() -> persistCategory.update(new CategoryDTO(1, "")))
                .hasMessage("Mandatory fields are missing")
                .isInstanceOf(CategoryException.class);
    }// update_category_should_throw_category_exception_when_fields_mandatory_are_missing()

    @Test
    public void update_category_should_throw_category_exception_when_id_is_null(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistCategory.update(new CategoryDTO(null, "Pizzas")))
                .hasMessage("Category ID is mandatory for update")
                .isInstanceOf(CategoryException.class);
    }// update_category_should_throw_category_exception_when_id_is_null()

    @Test
    public void update_category_should_success_when_user_is_logged_and_fields_are_given(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistCategory.update(new CategoryDTO(1, "Pizzas")))
                .doesNotThrowAnyException();
    }// update_category_should_success_when_user_is_logged_and_fields_are_given()

}// PersistCategoryUT
