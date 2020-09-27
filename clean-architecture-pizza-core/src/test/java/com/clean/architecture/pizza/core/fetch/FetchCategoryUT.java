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
package com.clean.architecture.pizza.core.fetch;

import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.ports.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FetchCategoryUT {

    @Mock
    private CategoryRepository categoryRepository;

    private FetchCategory fetchCategory;

    private CategoryDTO categoryStub;

    @Before
    public void setUp(){
        this.categoryStub = new CategoryDTO(1, "pizzas");
        this.fetchCategory = new FetchCategory(categoryRepository);
    }// setUp()

    @Test
    public void find_by_id_with_id_equals_to_1_should_return_category_pizzas(){
        Mockito.when(categoryRepository.findById(1))
                .thenReturn(Optional.of(categoryStub));
        Optional<CategoryDTO> optCat = fetchCategory.findById(1);
        Assertions.assertThat(optCat).isPresent();
        optCat.ifPresent(cat -> {
            Assertions.assertThat(cat.getId()).isEqualTo(1);
            Assertions.assertThat(cat.getName()).isEqualTo("pizzas");
        });
    }// find_by_id_with_id_equals_to_1_should_return_category_pizzas()

    @Test
    public void find_by_id_with_id_equals_to_2_should_return_empty_optional(){
        Mockito.when(categoryRepository.findById(2))
                .thenReturn(Optional.empty());
        Optional<CategoryDTO> optCat = fetchCategory.findById(2);
        Assertions.assertThat(optCat).isNotPresent();
    }// find_by_id_with_id_equals_to_2_should_return_empty_optional()

    @Test
    public void find_all_should_return_list_with_category_pizzas(){
        Mockito.when(categoryRepository.findAll())
                .thenReturn(Collections.singletonList(categoryStub));
        List<CategoryDTO> categories = fetchCategory.findAll();
        Assertions.assertThat(categories).isNotEmpty();
        Assertions.assertThat(categories).hasSize(1);
        Assertions.assertThat(categories.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(categories.get(0).getName()).isEqualTo("pizzas");
    }// find_all_should_return_list_with_category_pizzas()

}// FetchCategoryUT
