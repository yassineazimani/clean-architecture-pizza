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

import com.clean.architecture.pizza.adapters.rest.model.FormProductDTO;
import com.clean.architecture.pizza.adapters.rest.model.ResponseHttpDTO;
import com.clean.architecture.pizza.core.admin.product.PersistProduct;
import com.clean.architecture.pizza.core.admin.product.RemoveProduct;
import com.clean.architecture.pizza.core.exceptions.*;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * Cas d'utilisations concernant la persistance de produits
     */
    private PersistProduct persistProduct;

    /**
     * Cas d'utilisations concernant la suppression de produits
     */
    private RemoveProduct removeProduct;

    /**
     * Constructeur
     * @param fetchProductsService
     */
    @Autowired
    public ProductsController(FetchProducts fetchProductsService,
                              PersistProduct persistProduct,
                              RemoveProduct removeProduct) {
        this.fetchProductsService = fetchProductsService;
        this.persistProduct = persistProduct;
        this.removeProduct = removeProduct;
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

    /**
     * Création d'un produit
     * @param productToPersist Produit à créer
     * @return ResponseEntity
     */
    @PostMapping("/admin/product")
    public ResponseEntity<?> createProduct(@RequestBody FormProductDTO productToPersist){
        try {
            ProductDTO product = new ProductDTO(null,
                    productToPersist.getName(),
                    productToPersist.getDescription(),
                    productToPersist.getPrice(),
                    productToPersist.getQuantityAvailable(),
                    new CategoryDTO(productToPersist.getCategoryId(), null), 0);
            this.persistProduct.save(product);
            return ResponseEntity.ok(new ResponseHttpDTO(product, null));
        } catch (ArgumentMissingException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getFieldsErrors()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (DatabaseException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }// createProduct()

    /**
     * Mise à jour d'un produit
     * @param productToUpdate Produit à mettre à jour
     * @return ResponseEntity
     */
    @PutMapping("/admin/product")
    public ResponseEntity<?> updateProduct(@RequestBody FormProductDTO productToUpdate){
        try {
            ProductDTO product = new ProductDTO(productToUpdate.getId(),
                    productToUpdate.getName(),
                    productToUpdate.getDescription(),
                    productToUpdate.getPrice(),
                    productToUpdate.getQuantityAvailable(),
                    new CategoryDTO(productToUpdate.getCategoryId(), null), 0);
            this.persistProduct.update(product);
            return ResponseEntity.ok(new ResponseHttpDTO(productToUpdate, null));
        } catch (ArgumentMissingException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getFieldsErrors()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (DatabaseException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }// updateProduct()

    /**
     * Suppression d'un produit
     * @param id Identifiant d'un produit à supprimer
     * @return ResponseEntity
     */
    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id){
        try {
            this.removeProduct.execute(id);
            return ResponseEntity.ok(new ResponseHttpDTO(id, null));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (DatabaseException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getFieldsErrors()), HttpStatus.BAD_REQUEST);
        }
    }// deleteProduct()

}// ProductsController
