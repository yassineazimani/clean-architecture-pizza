package com.clean.architecture.pizza.core.admin.product;

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.ProductException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.CategoryRepository;
import com.clean.architecture.pizza.core.ports.ProductRepository;
import com.clean.architecture.pizza.core.stub.ProductsStub;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PersistProductUT {

    private PersistProduct persistProduct;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AuthenticationUser authenticationUser;

    @Before
    public void setUp(){
        this.persistProduct = new PersistProduct(productRepository, categoryRepository, authenticationUser);
    }// setUp()

    @Test
    public void save_product_should_throw_authentication_exception_when_user_isnt_logged(){
        Assertions.assertThatCode(() -> persistProduct.save(ProductsStub.getPizza4Fromages(5)))
                .hasMessage("You need to login")
                .isInstanceOf(AuthenticationException.class);
    }// save_product_should_throw_authentication_exception_when_user_isnt_logged()

    @Test
    public void save_product_should_throw_product_exception_when_product_is_null(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistProduct.save(null))
                .hasMessage("Product is null")
                .isInstanceOf(ArgumentMissingException.class);
    }// save_product_should_throw_product_exception_when_product_is_null()

    @Test
    public void save_product_should_throw_product_exception_when_fields_mandatory_are_missing(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistProduct.save(new ProductDTO()))
                .hasMessage("Mandatory fields are missing")
                .isInstanceOf(ProductException.class);
    }// save_product_should_throw_product_exception_when_fields_mandatory_are_missing()

    @Test
    public void save_product_should_success_when_user_is_logged_and_fields_are_given(){
        Mockito.when(categoryRepository.findById(1))
                .thenReturn(Optional.of(new CategoryDTO(1, "Pizzas")));
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        ProductDTO pizza = ProductsStub.getPizza4Fromages(5);
        pizza.setId(null);
        Assertions.assertThatCode(() -> persistProduct.save(pizza))
                .doesNotThrowAnyException();
    }// save_product_should_success_when_user_is_logged_and_fields_are_given()

    @Test
    public void update_product_should_throw_authentication_exception_when_user_isnt_logged(){
        Assertions.assertThatCode(() -> persistProduct.update(ProductsStub.getPizza4Fromages(5)))
                .hasMessage("You need to login")
                .isInstanceOf(AuthenticationException.class);
    }// update_product_should_throw_authentication_exception_when_user_isnt_logged()

    @Test
    public void update_product_should_throw_product_exception_when_product_is_null(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Assertions.assertThatCode(() -> persistProduct.update(null))
                .hasMessage("Product is null")
                .isInstanceOf(ArgumentMissingException.class);
    }// update_product_should_throw_product_exception_when_product_is_null()

    @Test
    public void update_product_should_throw_product_exception_when_fields_mandatory_are_missing(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        ProductDTO product = new ProductDTO();
        product.setId(1);
        Assertions.assertThatCode(() -> persistProduct.update(product))
                .hasMessage("Mandatory fields are missing")
                .isInstanceOf(ProductException.class);
    }// update_product_should_throw_product_exception_when_fields_mandatory_are_missing()

    @Test
    public void update_product_should_throw_product_exception_when_id_is_null(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        ProductDTO product = ProductsStub.getPizza4Fromages(5);
        product.setId(null);
        Assertions.assertThatCode(() -> persistProduct.update(product))
                .hasMessage("Product ID is mandatory for update")
                .isInstanceOf(ProductException.class);
    }// update_product_should_throw_product_exception_when_id_is_null()

    @Test
    public void update_product_should_success_when_user_is_logged_and_fields_are_given(){
        Mockito.when(authenticationUser.isAuthenticated()).thenReturn(true);
        Mockito.when(categoryRepository.findById(1))
                .thenReturn(Optional.of(new CategoryDTO(1, "Pizzas")));
        Mockito.when(productRepository.findById(1))
                .thenReturn(Optional.of(ProductsStub.getPizza4Fromages(5)));
        Assertions.assertThatCode(() -> persistProduct.update(ProductsStub.getPizza4Fromages(5)))
                .doesNotThrowAnyException();
    }// update_product_should_success_when_user_is_logged_and_fields_are_given()

}// PersistCategoryUT
