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
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends ManagementTransaction{

    /**
     * Vérifie l'existence d'une catégorie
     * @param name Nom de la catégorie
     * @return boolean
     */
    boolean existsByName(String name);

    /**
     * Vérifie l'existence d'une catégorie
     * @param id Identifiant de la catégorie
     * @return boolean
     */
    boolean existsById(int id);

    /**
     * Suppression d'une catégorie
     * @param id Identifiant de la catégorie
     */
    void deleteById(int id) throws DatabaseException;

    /**
     * Récupération d'une catégorie
     * @param id Identifiant de la catégorie
     * @return Optional {@see CategoryDTO}
     */
    Optional<CategoryDTO> findById(int id);

    /**
     * Sauvegarde d'une catégorie
     * @param category Catégorie
     */
    void save(CategoryDTO category) throws DatabaseException, ArgumentMissingException;

    /**
     * Mise à jour d'une catégorie
     * @param category Catégorie
     */
    void update(CategoryDTO category) throws DatabaseException, ArgumentMissingException;

    /**
     * Récupération de toutes les catégories
     * @return List de catégories {@see CategoryDTO}
     */
    List<CategoryDTO> findAll();

}// CategoryRepository
