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
package com.clean.architecture.pizza.core.ports;

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.OrderDTO;
import com.clean.architecture.pizza.core.model.StatsSumOrderTotalByProductsDTO;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends ManagementTransaction{

    Optional<OrderDTO> findById(int id);

    OrderDTO save(OrderDTO order) throws DatabaseException, ArgumentMissingException;

    OrderDTO update(OrderDTO order) throws DatabaseException, ArgumentMissingException;

    /**
     * Récupère les sommes de toutes les commandes en fonction des
     * produits les composant. Attention : Cette fonction ne retourne
     * pas les produits les plus vendus en fonction de leurs sommes !
     * @return
     * @throws AuthenticationException
     */
    List<StatsSumOrderTotalByProductsDTO> getTotalSumByProducts();

}// OrderRepository
