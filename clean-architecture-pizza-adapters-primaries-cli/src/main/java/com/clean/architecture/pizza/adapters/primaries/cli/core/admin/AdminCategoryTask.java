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
package com.clean.architecture.pizza.adapters.primaries.cli.core.admin;

import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.CategoryException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Cette classe gère l'administration des catégories
 * de l'application.
 */
public class AdminCategoryTask {

    private PersistCategory persistCategory;

    private RemoveCategory removeCategory;

    private FetchCategory fetchCategory;

    private Scanner scan;

    public AdminCategoryTask(PersistCategory persistCategory, RemoveCategory removeCategory, FetchCategory fetchCategory, Scanner scan) {
        this.persistCategory = persistCategory;
        this.removeCategory = removeCategory;
        this.fetchCategory = fetchCategory;
        this.scan = scan;
    }// AdminCategoryTask()

    /**
     * Exécution du choix sélectionné par l'utilisateur
     * @param choice choix
     * @return boolean déterminant si le retour au menu principal doit être effectué
     */
    public boolean run(int choice){
        boolean backToMainMenu = false;
        switch (choice){
            case 1:
                createCategory();
                break;
            case 2:
                updateCategory();
                break;
            case 3:
                deleteCategory();
                break;
            case 4:
                fetchAllCategories();
                break;
            case 5:
                backToMainMenu = true;
                break;
        }
        return backToMainMenu;
    }// run()

    /**
     * Création d'une catégorie
     */
    private void createCategory() {
        boolean hasErrors = true;
        do {
            System.out.println("Category name : ");
            String name = scan.next();
            CategoryDTO cat = new CategoryDTO(null, name);
            try {
                persistCategory.save(cat);
                hasErrors = false;
            } catch (CategoryException e) {
                e.getFieldsErrors().forEach(System.err::println);
            } catch (AuthenticationException e) {
                System.err.println("You don't have the rights to create a category");
            } catch (ArgumentMissingException | DatabaseException e) {
                System.err.println("Technical error : Impossible to create a category");
            }
        }while(hasErrors);
    }// createCategory()

    /**
     * Mise à jour d'une catégorie
     */
    private void updateCategory() {
        System.out.println("Category ID : ");
        Integer categoryId = null;
        try{
            categoryId = scan.nextInt();
        }catch(Exception e){
            scan.next();
        }
        fetchCategory.findById(categoryId)
                .ifPresent(category -> {
                    boolean hasErrors = true;
                    do {
                        System.out.println("Category name (current = " + category.getName() + ") : ");
                        String name = scan.next();
                        CategoryDTO cat = new CategoryDTO(category.getId(), name);
                        try {
                            persistCategory.update(cat);
                            hasErrors = false;
                        } catch (CategoryException e) {
                            e.getFieldsErrors().forEach(System.err::println);
                        } catch (AuthenticationException e) {
                            System.err.println("You don't have the rights to update a category");
                        } catch (ArgumentMissingException | DatabaseException e) {
                            System.err.println("Technical error : Impossible to update a category");
                        }
                    }while(hasErrors);
                });
    }// updateCategory()

    /**
     * Suppression d'une catégorie
     */
    private void deleteCategory() {
        System.out.println("Category ID : ");
        int categoryId = -1;
        try{
            categoryId = scan.nextInt();
        }catch(Exception e){
            scan.next();
        }
        try {
            this.removeCategory.execute(categoryId);
        } catch (AuthenticationException e) {
            System.err.println("You don't have the rights to remove a category");
        } catch (DatabaseException e) {
            System.err.println("Technical error : Impossible to remove a category");
        } catch (CategoryException e) {
            e.getFieldsErrors().forEach(System.err::println);
        }
    }// deleteCategory()

    /**
     * Affichage de toutes les catégories
     */
    private void fetchAllCategories(){
        List<CategoryDTO> categories = this.fetchCategory.findAll();
        if(CollectionUtils.isNotEmpty(categories)){
            categories.forEach(category -> System.out.println(" * Category (id = " + category.getId() + ") " + category.getName()));
        }
    }// fetchAllCategories()

}// AdminCategoryTask
