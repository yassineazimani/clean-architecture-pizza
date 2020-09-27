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

import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.ports.CategoryRepository;

import java.util.List;
import java.util.Optional;

public class FetchCategory {

    private CategoryRepository categoryRepository;

    public FetchCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }// FetchCategory()

    public Optional<CategoryDTO> findById(int id){
        return this.categoryRepository.findById(id);
    }// findById()

    public List<CategoryDTO> findAll(){
        return this.categoryRepository.findAll();
    }// findAll()

}// FetchCategory
