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

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.TransactionException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class CategoryRepositoryImplUT {

    private static CategoryRepositoryImpl categoryRepository;

    @BeforeClass
    public static void setUp(){
        categoryRepository = new CategoryRepositoryImpl();
    }// setUp()

    @Test
    public void find_by_id_should_return_category_pizzas_when_id_is_1(){
        try {
            this.categoryRepository.begin();
            Optional<CategoryDTO> optCategory = categoryRepository.findById(1);
            this.categoryRepository.commit();
            Assertions.assertThat(optCategory).isPresent();
            optCategory.ifPresent(categoryDTO -> {
                Assertions.assertThat(categoryDTO.getId()).isEqualTo(1);
                Assertions.assertThat(categoryDTO.getName()).isEqualTo("pizzas");
            });
        } catch (TransactionException e) {
            this.categoryRepository.rollback();
        }
    }// find_by_id_should_return_category_pizzas_when_id_is_1()

    @Test
    public void find_by_id_should_empty_optional_when_id_is_20(){
        try {
            this.categoryRepository.begin();
            Optional<CategoryDTO> optCategory = categoryRepository.findById(20);
            Assertions.assertThat(optCategory).isNotPresent();
            this.categoryRepository.commit();
        } catch (TransactionException e) {
            this.categoryRepository.rollback();
        }
    }// find_by_id_should_empty_optional_when_id_is_20()

    @Test
    public void find_all_should_return_pizzas_drinks_desserts_categories(){
        try {
            this.categoryRepository.begin();
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
            this.categoryRepository.commit();
        }catch(TransactionException te){
            this.categoryRepository.rollback();
            te.printStackTrace();
        }
    }// find_all_should_return_pizzas_drinks_desserts_categories()

    @Test
    public void exists_by_id_should_return_true_when_id_is_1(){
        try {
            this.categoryRepository.begin();
            Assertions.assertThat(this.categoryRepository.existsById(1))
                    .isTrue();
            this.categoryRepository.commit();
        }catch(TransactionException te){
            this.categoryRepository.rollback();
            te.printStackTrace();
        }
    }// exists_by_id_should_return_true_when_id_is_1()

    @Test
    public void exists_by_id_should_return_false_when_id_is_10(){
        try {
            this.categoryRepository.begin();
            Assertions.assertThat(this.categoryRepository.existsById(10))
                    .isFalse();
            this.categoryRepository.commit();
        }catch(TransactionException te){
            this.categoryRepository.rollback();
            te.printStackTrace();
        }
    }// exists_by_id_should_return_false_when_id_is_10()

    @Test
    public void exists_by_name_should_return_true_when_name_is_pizzas(){
        try {
            this.categoryRepository.begin();
            Assertions.assertThat(this.categoryRepository.existsByName("pizzas"))
                .isTrue();
            this.categoryRepository.commit();
        }catch(TransactionException te){
            this.categoryRepository.rollback();
            te.printStackTrace();
        }
    }// exists_by_name_should_return_true_when_name_is_pizzas()

    @Test
    public void exists_by_name_should_return_false_when_id_is_toto(){
        try {
            this.categoryRepository.begin();
            Assertions.assertThat(this.categoryRepository.existsByName("toto"))
                    .isFalse();
            this.categoryRepository.commit();
        } catch (TransactionException e) {
            this.categoryRepository.rollback();
            e.printStackTrace();
        }
    }// exists_by_name_should_return_false_when_id_is_toto()

    @Test
    public void save_should_throw_argument_missing_exception_when_category_is_null(){
        CategoryDTO cat = null;
        Assertions.assertThatCode(() -> this.categoryRepository.save(cat))
                .isInstanceOf(ArgumentMissingException.class)
                .hasMessage("Category is null");
    }// save_should_throw_argument_missing_exception_when_category_is_null()

    @Test
    public void update_should_throw_argument_missing_exception_when_category_is_null(){
        CategoryDTO cat = null;
        Assertions.assertThatCode(() -> this.categoryRepository.update(cat))
                .isInstanceOf(ArgumentMissingException.class)
                .hasMessage("Category is null");
    }// update_should_throw_argument_missing_exception_when_category_is_null()

    @Test
    public void crud_should_success_for_category_salads() throws DatabaseException, ArgumentMissingException {
        try {
            this.categoryRepository.begin();
            this.categoryRepository.save(new CategoryDTO(null, "salads"));
            this.categoryRepository.commit();
        } catch (TransactionException e) {
            this.categoryRepository.rollback();
        }
        Assertions.assertThat(this.categoryRepository.existsByName("salads"))
                .isTrue();

        Optional<CategoryDTO> optCat = this.categoryRepository.findAll()
                .stream()
                .filter(cat -> "salads".equals(cat.getName()))
                .findFirst();
        Assertions.assertThat(optCat).isPresent();
        CategoryDTO categoryDTO = optCat.get();
        try {
            this.categoryRepository.begin();
            this.categoryRepository.update(new CategoryDTO(categoryDTO.getId(), "Salads"));
            this.categoryRepository.commit();
        }catch(TransactionException e){
            this.categoryRepository.rollback();
        }
        Assertions.assertThat(this.categoryRepository.existsByName("salads"))
                .isFalse();
        Assertions.assertThat(this.categoryRepository.existsByName("Salads"))
                .isTrue();
        Assertions.assertThat(this.categoryRepository.existsById(categoryDTO.getId()))
                .isTrue();

        try {
            this.categoryRepository.begin();
            this.categoryRepository.deleteById(categoryDTO.getId());
            this.categoryRepository.commit();
        }catch(TransactionException e){
            this.categoryRepository.rollback();
        }
        Assertions.assertThat(this.categoryRepository.existsById(categoryDTO.getId()))
                    .isFalse();
    }// crud_should_success_for_category_salads()

}// CategoryRepositoryImplUT
