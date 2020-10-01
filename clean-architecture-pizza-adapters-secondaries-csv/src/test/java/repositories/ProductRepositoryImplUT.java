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
package repositories;

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class ProductRepositoryImplUT {

    private ProductRepositoryImpl productRepository;

    @Before
    public void setUp(){
        productRepository = new ProductRepositoryImpl();
    }// setUp()

    @Test
    public void find_by_id_should_return_pizza_four_cheese_when_id_is_1(){
        Optional<ProductDTO> optProduct = productRepository.findById(1);
        Assertions.assertThat(optProduct).isPresent();
        optProduct.ifPresent(productDTO -> {
            Assertions.assertThat(productDTO.getId()).isEqualTo(1);
            Assertions.assertThat(productDTO.getName()).isEqualTo("Four-cheese pizza");
            Assertions.assertThat(productDTO.getDescription()).isEqualTo("Pizza with four cheeses");
            Assertions.assertThat(productDTO.getPrice()).isEqualTo(8.9);
            Assertions.assertThat(productDTO.getQuantityAvailable()).isEqualTo(5);
            Assertions.assertThat(productDTO.getQuantityOrdered()).isEqualTo(0);
            Assertions.assertThat(productDTO.getCategory()).isNotNull();
            Assertions.assertThat(productDTO.getCategory().getId()).isEqualTo(1);
            Assertions.assertThat(productDTO.getCategory().getName()).isEqualTo("pizzas");
        });
    }// find_by_id_should_return_pizza_four_cheese_when_id_is_1()

    @Test
    public void find_by_id_should_empty_optional_when_id_is_20(){
        Optional<ProductDTO> optProduct = productRepository.findById(20);
        Assertions.assertThat(optProduct).isNotPresent();
    }// find_by_id_should_empty_optional_when_id_is_20()

    @Test
    public void find_all_should_return_7_products(){
        List<ProductDTO> products = productRepository.findAllProducts();
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products).hasSize(7);
        boolean isFourCheesePizzaPresent = products
                .stream()
                .anyMatch(product -> product.getId() == 1
                        && "Four-cheese pizza".equals(product.getName())
                        && "Pizza with four cheeses".equals(product.getDescription())
                        && product.getPrice().equals(8.9)
                        && product.getQuantityAvailable() == 5
                        && product.getCategory() != null
                        && product.getCategory().getId() == 1
                        && "pizzas".equals(product.getCategory().getName())
                );
        boolean isVegetarianPizzaPresent = products
                .stream()
                .anyMatch(product -> product.getId() == 2
                        && "Vegetarian pizza".equals(product.getName())
                        && "Pizza with mushrooms,tomatoes,peppers,carrot,onion".equals(product.getDescription())
                        && product.getPrice().equals(7.9)
                        && product.getQuantityAvailable() == 7
                        && product.getCategory() != null
                        && product.getCategory().getId() == 1
                        && "pizzas".equals(product.getCategory().getName())
                );
        boolean isFishPizzaPresent = products
                .stream()
                .anyMatch(product -> product.getId() == 3
                        && "Fish pizza".equals(product.getName())
                        && "Pizza with salmon".equals(product.getDescription())
                        && product.getPrice().equals(8.2)
                        && product.getQuantityAvailable() == 15
                        && product.getCategory() != null
                        && product.getCategory().getId() == 1
                        && "pizzas".equals(product.getCategory().getName())
                );
        boolean isPepperoniPizzaPresent = products
                .stream()
                .anyMatch(product -> product.getId() == 4
                        && "Pepperoni pizza".equals(product.getName())
                        && "Delicious Pepperoni pizza".equals(product.getDescription())
                        && product.getPrice().equals(8.2)
                        && product.getQuantityAvailable() == 1
                        && product.getCategory() != null
                        && product.getCategory().getId() == 1
                        && "pizzas".equals(product.getCategory().getName())
                );
        boolean isOranginaPresent = products
                .stream()
                .anyMatch(product -> product.getId() == 5
                        && "Orangina".equals(product.getName())
                        && "Orange drink with gas".equals(product.getDescription())
                        && product.getPrice().equals(2.9)
                        && product.getQuantityAvailable() == 0
                        && product.getCategory() != null
                        && product.getCategory().getId() == 2
                        && "drinks".equals(product.getCategory().getName())
                );
        boolean isCocaColaPresent = products
                .stream()
                .anyMatch(product -> product.getId() == 6
                        && "Coca-cola".equals(product.getName())
                        && "The famous Coca-cola".equals(product.getDescription())
                        && product.getPrice().equals(2.9)
                        && product.getQuantityAvailable() == 56
                        && product.getCategory() != null
                        && product.getCategory().getId() == 2
                        && "drinks".equals(product.getCategory().getName())
                );
        boolean isLemonPiePresent = products
                .stream()
                .anyMatch(product -> product.getId() == 7
                        && "Lemon pie".equals(product.getName())
                        && "Lemon pie".equals(product.getDescription())
                        && product.getPrice().equals(4.9)
                        && product.getQuantityAvailable() == 29
                        && product.getCategory() != null
                        && product.getCategory().getId() == 3
                        && "desserts".equals(product.getCategory().getName())
                );
        Assertions.assertThat(isFourCheesePizzaPresent).isTrue();
        Assertions.assertThat(isVegetarianPizzaPresent).isTrue();
        Assertions.assertThat(isFishPizzaPresent).isTrue();
        Assertions.assertThat(isPepperoniPizzaPresent).isTrue();
        Assertions.assertThat(isOranginaPresent).isTrue();
        Assertions.assertThat(isCocaColaPresent).isTrue();
        Assertions.assertThat(isLemonPiePresent).isTrue();
    }// find_all_should_return_7_products()

    @Test
    public void exists_by_id_should_return_true_when_id_is_1(){
        Assertions.assertThat(this.productRepository.existsById(1))
                .isTrue();
    }// exists_by_id_should_return_true_when_id_is_1()

    @Test
    public void exists_by_id_should_return_false_when_id_is_10(){
        Assertions.assertThat(this.productRepository.existsById(10))
                .isFalse();
    }// exists_by_id_should_return_false_when_id_is_10()

    @Test
    public void exists_by_name_should_return_true_when_name_is_orangina(){
        Assertions.assertThat(this.productRepository.existsByName("Orangina"))
                .isTrue();
    }// exists_by_name_should_return_true_when_name_is_orangina()

    @Test
    public void exists_by_name_should_return_false_when_id_is_toto(){
        Assertions.assertThat(this.productRepository.existsByName("toto"))
                .isFalse();
    }// exists_by_name_should_return_false_when_id_is_toto()

    @Test
    public void save_should_throw_argument_missing_exception_when_product_is_null(){
        Assertions.assertThatCode(() -> this.productRepository.save(null))
                .isInstanceOf(ArgumentMissingException.class)
                .hasMessage("Product is null");
    }// save_should_throw_argument_missing_exception_when_product_is_null()

    @Test
    public void update_should_throw_argument_missing_exception_when_product_is_null(){
        Assertions.assertThatCode(() -> this.productRepository.update(null))
                .isInstanceOf(ArgumentMissingException.class)
                .hasMessage("Product is null");
    }// update_should_throw_argument_missing_exception_when_product_is_null()

    @Test
    public void crud_should_success_for_category_salads() throws DatabaseException, ArgumentMissingException {
        ProductDTO productToSave = new ProductDTO(null, "Surprise", "Chief pizza", 13.5, 10, new CategoryDTO(1, "pizzas"), 0);
        ProductDTO productToUpdate = new ProductDTO(8, "Surprise Pizza", "Chief pizza", 13.5, 10, new CategoryDTO(1, "pizzas"), 0);
        this.productRepository.save(productToSave);
        Assertions.assertThat(this.productRepository.existsByName("Surprise"))
                .isTrue();
        this.productRepository.update(productToUpdate);
        Assertions.assertThat(this.productRepository.existsByName("Surprise"))
                .isFalse();
        Assertions.assertThat(this.productRepository.existsByName("Surprise Pizza"))
                .isTrue();
        Assertions.assertThat(this.productRepository.existsById(8))
                .isTrue();
        this.productRepository.deleteById(8);
        Assertions.assertThat(this.productRepository.existsById(8))
                .isFalse();
    }// crud_should_success_for_category_salads()

}// ProductRepositoryImplUT
