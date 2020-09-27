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
package com.clean.architecture.pizza.adapters.rest.endpoints;

import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Contrôleur gérant les produits
 */
@RestController
@RequestMapping("/api")
public class ProductsController {

    /**
     * Cas d'utilisations concernant la récupération de produits
     */
    private FetchProducts fetchProductsService;

    /**
     * Constructeur
     * @param fetchProductsService
     */
    @Autowired
    public ProductsController(FetchProducts fetchProductsService) {
        this.fetchProductsService = fetchProductsService;
    }// ProductsController()

    /**
     * Récupération de la liste de produits par catégorie.
     * @return ResponseEntity
     */
    @GetMapping("/public/productsByCategory")
    public ResponseEntity<?> getAllProductsByCategory(){
        return ResponseEntity.ok(this.fetchProductsService.findAll());
    }// getAllProductsByCategory()

    /**
     * Récupération d'un produit
     * @return ResponseEntity
     */
    @GetMapping("/public/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(this.fetchProductsService.findById(id));
    }// getAllProductsByCategory()

}// ProductsController
