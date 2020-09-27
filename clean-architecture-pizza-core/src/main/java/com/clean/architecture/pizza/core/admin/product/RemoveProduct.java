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
package com.clean.architecture.pizza.core.admin.product;

import com.clean.architecture.pizza.core.exceptions.*;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.ProductRepository;

public class RemoveProduct {

    private ProductRepository productRepository;

    private AuthenticationUser authenticationUser;

    public RemoveProduct(ProductRepository productRepository,
                         AuthenticationUser authenticationUser) {
        this.productRepository = productRepository;
        this.authenticationUser = authenticationUser;
    }// RemoveProduct()

    public boolean execute(int id) throws AuthenticationException, DatabaseException, ProductException {
        if(!this.authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        try {
            if (this.productRepository.existsById(id)) {
                this.productRepository.begin();
                this.productRepository.deleteById(id);
                this.productRepository.commit();
                return true;
            }
        }catch(TransactionException te){
            this.productRepository.rollback();
            throw new ProductException("Error technical : Impossible to remove a product");
        }
        return false;
    }// execute()

}// RemoveProduct
