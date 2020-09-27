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
import com.clean.architecture.pizza.core.exceptions.CategoryException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.TransactionException;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.CategoryRepository;

public class RemoveCategory {

    private CategoryRepository categoryRepository;

    private AuthenticationUser authenticationUser;

    public RemoveCategory(CategoryRepository categoryRepository,
                          AuthenticationUser authenticationUser) {
        this.categoryRepository = categoryRepository;
        this.authenticationUser = authenticationUser;
    }// RemoveCategory()

    public void execute(int id) throws AuthenticationException, DatabaseException, CategoryException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        try{
            this.categoryRepository.begin();
            if(this.categoryRepository.existsById(id)){
                this.categoryRepository.deleteById(id);
            }
            this.categoryRepository.commit();
        }catch (TransactionException te){
            this.categoryRepository.rollback();
            throw new CategoryException("Error technical : Impossible to remove a category");
        }
    }// execute()

}// RemoveCategory
