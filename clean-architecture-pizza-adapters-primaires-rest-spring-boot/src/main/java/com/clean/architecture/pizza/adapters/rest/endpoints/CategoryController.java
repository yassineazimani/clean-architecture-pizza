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

import com.clean.architecture.pizza.adapters.rest.model.FormCategoryDTO;
import com.clean.architecture.pizza.adapters.rest.model.ResponseHttpDTO;
import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.CategoryException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Contrôleur gérant les catégories
 */
@RestController
@RequestMapping("/api")
public class CategoryController {

    /**
     * Cas d'utilisations concernant la récupération de catégories
     */
    private FetchCategory fetchCategoriesService;

    private PersistCategory persistCategory;

    private RemoveCategory removeCategory;

    /**
     * Constructeur
     * @param fetchCategoriesService
     * @param persistCategory
     * @param removeCategory
     */
    @Autowired
    public CategoryController(FetchCategory fetchCategoriesService,
                              PersistCategory persistCategory,
                              RemoveCategory removeCategory) {
        this.fetchCategoriesService = fetchCategoriesService;
        this.persistCategory = persistCategory;
        this.removeCategory = removeCategory;
    }// CategoryController()

    /**
     * Récupération de toutes les catégories disponibles
     * @return ResponseEntity
     */
    @GetMapping("/public/categories")
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(this.fetchCategoriesService.findAll());
    }// getAllCategories()

    /**
     * Récupération d'une catégorie
     * @param id Identifiant de la catégorie
     * @return ResponseEntity
     */
    @GetMapping("/public/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(this.fetchCategoriesService.findById(id));
    }// getCategoryById()

    @PostMapping("/admin/category")
    public ResponseEntity<?> createCategory(@RequestBody FormCategoryDTO categoryToPersist){
        try {
            this.persistCategory.save(new CategoryDTO(null, categoryToPersist.getName()));
            return ResponseEntity.ok(new ResponseHttpDTO(categoryToPersist, null));
        } catch (ArgumentMissingException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CategoryException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getFieldsErrors()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (DatabaseException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }// createCategory()

    @PutMapping("/admin/category")
    public ResponseEntity<?> updateCategory(@RequestBody FormCategoryDTO categoryToUpdate){
        try {
            this.persistCategory.update(new CategoryDTO(categoryToUpdate.getId(), categoryToUpdate.getName()));
            return ResponseEntity.ok(new ResponseHttpDTO(categoryToUpdate, null));
        } catch (ArgumentMissingException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CategoryException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getFieldsErrors()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (DatabaseException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }// updateCategory()

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id){
        try {
            this.removeCategory.execute(id);
            return ResponseEntity.ok(new ResponseHttpDTO(id, null));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (DatabaseException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CategoryException e) {
            return new ResponseEntity<>(new ResponseHttpDTO(null, e.getFieldsErrors()), HttpStatus.BAD_REQUEST);
        }
    }// deleteCategory()

}// CategoryController
