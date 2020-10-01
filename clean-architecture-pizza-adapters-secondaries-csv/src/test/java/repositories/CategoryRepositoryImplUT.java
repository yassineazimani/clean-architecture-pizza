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
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class CategoryRepositoryImplUT {

    private CategoryRepositoryImpl categoryRepository;

    @Before
    public void setUp(){
        categoryRepository = new CategoryRepositoryImpl();
    }// setUp()

    @Test
    public void find_by_id_should_return_category_pizzas_when_id_is_1(){
        Optional<CategoryDTO> optCategory = categoryRepository.findById(1);
        Assertions.assertThat(optCategory).isPresent();
        optCategory.ifPresent(categoryDTO -> {
            Assertions.assertThat(categoryDTO.getId()).isEqualTo(1);
            Assertions.assertThat(categoryDTO.getName()).isEqualTo("pizzas");
        });
    }// find_by_id_should_return_category_pizzas_when_id_is_1()

    @Test
    public void find_by_id_should_empty_optional_when_id_is_20(){
        Optional<CategoryDTO> optCategory = categoryRepository.findById(20);
        Assertions.assertThat(optCategory).isNotPresent();
    }// find_by_id_should_empty_optional_when_id_is_20()

    @Test
    public void find_all_should_return_pizzas_drinks_desserts_categories(){
        List<CategoryDTO> categories = categoryRepository.findAll();
        Assertions.assertThat(categories).isNotEmpty();
        Assertions.assertThat(categories).hasSize(3);
        boolean isPizzasPresent = categories
                .stream()
                .anyMatch(category -> category.getId() == 1 && category.getName().equals("pizzas"));
        boolean isDrinksPresent = categories
                .stream()
                .anyMatch(category -> category.getId() == 2 && category.getName().equals("drinks"));
        boolean isDessertsPresent = categories
                .stream()
                .anyMatch(category -> category.getId() == 3 && category.getName().equals("desserts"));
        Assertions.assertThat(isPizzasPresent).isTrue();
        Assertions.assertThat(isDrinksPresent).isTrue();
        Assertions.assertThat(isDessertsPresent).isTrue();
    }// find_all_should_return_pizzas_drinks_desserts_categories()

    @Test
    public void exists_by_id_should_return_true_when_id_is_1(){
        Assertions.assertThat(this.categoryRepository.existsById(1))
                .isTrue();
    }// exists_by_id_should_return_true_when_id_is_1()

    @Test
    public void exists_by_id_should_return_false_when_id_is_10(){
        Assertions.assertThat(this.categoryRepository.existsById(10))
                .isFalse();
    }// exists_by_id_should_return_false_when_id_is_10()

    @Test
    public void exists_by_name_should_return_true_when_name_is_pizzas(){
        Assertions.assertThat(this.categoryRepository.existsByName("pizzas"))
                .isTrue();
    }// exists_by_name_should_return_true_when_name_is_pizzas()

    @Test
    public void exists_by_name_should_return_false_when_id_is_toto(){
        Assertions.assertThat(this.categoryRepository.existsByName("toto"))
                .isFalse();
    }// exists_by_name_should_return_false_when_id_is_toto()

    @Test
    public void save_should_throw_argument_missing_exception_when_category_is_null(){
        Assertions.assertThatCode(() -> this.categoryRepository.save(null))
                .isInstanceOf(ArgumentMissingException.class)
                .hasMessage("Category is null");
    }// save_should_throw_argument_missing_exception_when_category_is_null()

    @Test
    public void update_should_throw_argument_missing_exception_when_category_is_null(){
        Assertions.assertThatCode(() -> this.categoryRepository.update(null))
                .isInstanceOf(ArgumentMissingException.class)
                .hasMessage("Category is null");
    }// update_should_throw_argument_missing_exception_when_category_is_null()

    @Test
    public void crud_should_success_for_category_salads() throws DatabaseException, ArgumentMissingException {
        this.categoryRepository.save(new CategoryDTO(null, "salads"));
        Assertions.assertThat(this.categoryRepository.existsByName("salads"))
                .isTrue();
        this.categoryRepository.update(new CategoryDTO(4, "Salads"));
        Assertions.assertThat(this.categoryRepository.existsByName("salads"))
                .isFalse();
        Assertions.assertThat(this.categoryRepository.existsByName("Salads"))
                .isTrue();
        Assertions.assertThat(this.categoryRepository.existsById(4))
                .isTrue();
        this.categoryRepository.deleteById(4);
        Assertions.assertThat(this.categoryRepository.existsById(4))
                .isFalse();
    }// crud_should_success_for_category_salads()

}// CategoryRepositoryImplUT
