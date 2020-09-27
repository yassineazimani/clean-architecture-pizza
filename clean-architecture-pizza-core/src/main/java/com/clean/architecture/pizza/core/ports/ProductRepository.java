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
import com.clean.architecture.pizza.core.model.ProductDTO;

import java.util.List;
import java.util.Optional;

/**
 * Gestion du stockage des produits
 */
public interface ProductRepository extends ManagementTransaction{

    /**
     * Récupération de tous les produits
     * @return Tous les produits
     */
    List<ProductDTO> findAllProducts();

    /**
     * Récupération d'un produit
     * @param id Identifiant du produit
     * @return Optional
     */
    Optional<ProductDTO> findById(int id);

    /**
     * Vérifie l'existence d'un produit
     * @param id Identifiant du produit
     * @return boolean
     */
    boolean existsById(int id);

    /**
     * Suppression d'un produit
     * @param id Identifiant du produit
     */
    void deleteById(int id) throws DatabaseException;

    /**
     * Sauvegarde d'un produit
     * @param product Produit
     */
    void save(ProductDTO product) throws DatabaseException, ArgumentMissingException;

    /**
     * Mise à jour d'un produit
     * @param product Produit
     */
    void update(ProductDTO product) throws DatabaseException, ArgumentMissingException;
}// ProductRepository
