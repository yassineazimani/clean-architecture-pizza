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

import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Cette classe regroupe l'ensemble des cas d'utilisations
 * liées à la récupération d'un ou plusieurs produits.
 */
public class FetchProducts {

    private ProductRepository productRepository;

    public FetchProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }// FetchProducts()

    /**
     * Récupération de tous les produits de l'application.
     * Si un type de produit n'est plus disponible, la clé de la map représentant
     * la catégorie sera alors absente si le paramètre removeUnavailableProducts est à true.
     * @param removeUnavailableProducts Remove unavailable products.
     * @return Map de {@see Product} regroupés par nom de Catégorie (Pizzas, Boissons, Desserts).
     */
    public Map<String, List<ProductDTO>> findAll(boolean removeUnavailableProducts) {
        List<ProductDTO> products = productRepository.findAllProducts();
        if(removeUnavailableProducts){
            return products.stream()
                    .filter(product -> product.getQuantityAvailable() > 0)
                    .collect(Collectors.groupingBy(p -> p.getCategory().getName()));
        }
        return products.stream()
                .collect(Collectors.groupingBy(p -> p.getCategory().getName()));
    }// findAll()

    public Map<String, List<ProductDTO>> findAll() {
        return this.findAll(true);
    }// findAll()

    /**
     * Récupère un produit en fonction de son identifiant.
     * @param id Identifiant du produit
     * @return Optional de {@see Product}
     */
    public Optional<ProductDTO> findById(int id) {
        return productRepository.findById(id);
    }// findById()

    /**
     * Vérifie l'existence d'un produit
     * @param id Identifiant du produit
     * @return boolean
     */
    public boolean existsById(int id) {
        return productRepository.existsById(id);
    }// existsById()
}// FetchProducts
