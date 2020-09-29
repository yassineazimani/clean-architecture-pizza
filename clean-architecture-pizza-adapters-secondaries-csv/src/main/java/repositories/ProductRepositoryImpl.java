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
import com.clean.architecture.pizza.core.exceptions.TransactionException;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<ProductDTO> findAllProducts() {
        return null;
    }// findAllProducts()

    @Override
    public Optional<ProductDTO> findById(int id) {
        return Optional.empty();
    }// findById()

    @Override
    public boolean existsByName(String name) {
        return false;
    }// existsByName()

    @Override
    public boolean existsById(int id) {
        return false;
    }// existsById()

    @Override
    public void deleteById(int id) throws DatabaseException {

    }// deleteById()

    @Override
    public void save(ProductDTO product) throws DatabaseException, ArgumentMissingException {

    }// save()

    @Override
    public void update(ProductDTO product) throws DatabaseException, ArgumentMissingException {

    }// update()

    @Override
    public void begin() {}

    @Override
    public void commit() throws TransactionException {}

    @Override
    public void rollback() {}

}// ProductRepositoryImpl
