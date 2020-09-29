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
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.StatsSumOrderTotalByProductsDTO;
import com.clean.architecture.pizza.core.ports.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public Optional<OrderDTO> findById(int id) {
        return Optional.empty();
    }// findById()

    @Override
    public OrderDTO save(OrderDTO order) throws DatabaseException, ArgumentMissingException {
        return null;
    }// save()

    @Override
    public OrderDTO update(OrderDTO order) throws DatabaseException, ArgumentMissingException {
        return null;
    }// update()

    @Override
    public List<StatsSumOrderTotalByProductsDTO> getTotalSumByProducts() {
        return null;
    }// getTotalSumByProducts()

    @Override
    public void begin() {}

    @Override
    public void commit() throws TransactionException {}

    @Override
    public void rollback() {}

}// OrderRepositoryImpl
