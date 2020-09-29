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

import com.clean.architecture.pizza.core.exceptions.*;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.CategoryRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersistCategory {

    private AuthenticationUser authenticationUser;

    private CategoryRepository categoryRepository;

    public PersistCategory(AuthenticationUser authenticationUser,
                           CategoryRepository categoryRepository) {
        this.authenticationUser = authenticationUser;
        this.categoryRepository = categoryRepository;
    }// PersistCategory()

    public void save(CategoryDTO category) throws ArgumentMissingException, CategoryException, AuthenticationException, DatabaseException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        checkBusiness(category, false);
        try{
            this.categoryRepository.begin();
            this.categoryRepository.save(category);
            this.categoryRepository.commit();
        }catch (TransactionException te){
            this.categoryRepository.rollback();
            throw new CategoryException("Error technical : Impossible to create a category");
        }
    }// save()

    public void update(CategoryDTO category) throws ArgumentMissingException, CategoryException, AuthenticationException, DatabaseException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        checkBusiness(category, true);
        try{
            this.categoryRepository.begin();
            this.categoryRepository.update(category);
            this.categoryRepository.commit();
        }catch (TransactionException te){
            this.categoryRepository.rollback();
            throw new CategoryException("Error technical : Impossible to update a category");
        }
    }// update()

    private void checkBusiness(CategoryDTO category, boolean isUpdate) throws CategoryException, ArgumentMissingException {
        if(category == null){
            throw new ArgumentMissingException("Category is null");
        } else if (isUpdate && category.getId() == null) {
            throw new CategoryException("Category ID is mandatory for update");
        } else {
            List<String> errors = new ArrayList<>();
            if(StringUtils.isEmpty(category.getName())){
                errors.add("The name is mandatory");
            }
            if(CollectionUtils.isNotEmpty(errors)){
                CategoryException ce = new CategoryException("Mandatory fields are missing");
                ce.setFieldsErrors(errors);
                throw ce;
            }
            if(this.categoryRepository.existsByName(category.getName())){
                final String error = "A category with the name " + category.getName() + " already exists";
                CategoryException ce = new CategoryException(error);
                ce.setFieldsErrors(Collections.singletonList(error));
                throw ce;
            }
        }
    }// checkBusiness()

}// PersistCategory
