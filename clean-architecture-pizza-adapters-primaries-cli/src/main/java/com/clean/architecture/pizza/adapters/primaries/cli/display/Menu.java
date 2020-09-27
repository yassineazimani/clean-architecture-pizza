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
package com.clean.architecture.pizza.adapters.primaries.cli.display;

import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.ProductRepositoryImpl;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.model.ProductDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Gestion de l'affichage des différents menus
 * de l'application.
 */
public class Menu {

    private FetchProducts fetchProducts;

    public Menu() {
        this.fetchProducts = new FetchProducts(new ProductRepositoryImpl());
    }// Menu()

    /**
     * Affichage du menu principal pour un client
     * @return Produits affichés
     */
    public List<ProductDTO> displayMenu(){
        Map<String, List<ProductDTO>> productsByCategory = this.fetchProducts.findAll();
        List<ProductDTO> allProducts = new ArrayList<>();
        if(productsByCategory != null){
            System.out.println("########################### MENU ########################");
            productsByCategory.forEach((category, products) -> {
                System.out.println();
                System.out.println(StringUtils.capitalize(category) + " : ");
                System.out.println();
                products.sort(Comparator.comparing(ProductDTO::getId));
                products.forEach(product -> System.out.println(product.getId() + " " + product.getName() + " " + product.getPrice() + "$"));
                allProducts.addAll(products);
            });
            System.out.println();
        }
        return allProducts;
    }// displayMenu()

    /**
     * Affichage du menu principal pour l'administrateur
     */
    public void displayAdminMenu(){
        System.out.println("########################### MENU ########################");
        System.out.println();
        System.out.println("1. Manage categories");
        System.out.println("2. Manage products");
        System.out.println("3. Statistics consultation");
        System.out.println("4. Quit");
    }// displayAdminMenu()

    /**
     * Affichage du menu de la gestion des catégories pour l'administrateur
     */
    public void displayAdminMenuCategory(){
        System.out.println("########################### MANAGE CATEGORIES ########################");
        System.out.println();
        System.out.println("1. Create a category");
        System.out.println("2. Update a category");
        System.out.println("3. Delete a product");
        System.out.println("4. Display all categories");
        System.out.println("5. Back to main menu");
    }// displayAdminMenuCategory()

    /**
     * Affichage du menu de la gestion des produits pour l'administrateur
     */
    public void displayAdminMenuProduct(){
        System.out.println("########################### MANAGE PRODUCTS ########################");
        System.out.println();
        System.out.println("1. Create a product");
        System.out.println("2. Update a product");
        System.out.println("3. Delete a product");
        System.out.println("4. Display all products");
        System.out.println("5. Back to main menu");
    }// displayAdminMenuProduct()

    /**
     * Affichage du menu de la gestion des statistiques pour l'administrateur
     */
    public void displayAdminMenuStats(){
        System.out.println("########################### STATISTICS ########################");
        System.out.println("1. Get all total sum of products selled");
        System.out.println("2. Back to main menu");
        System.out.println();
    }// displayAdminMenuStats()

}// Menu
