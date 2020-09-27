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

import com.clean.architecture.pizza.core.admin.product.PersistProduct;
import com.clean.architecture.pizza.core.admin.product.RemoveProduct;
import com.clean.architecture.pizza.core.exceptions.*;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.model.ProductDTO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Cette classe gère l'administration des produits
 * de l'application.
 */
public class AdminProductTask {

    private PersistProduct persistProduct;

    private RemoveProduct removeProduct;

    private FetchProducts fetchProducts;

    private Scanner scan;

    public AdminProductTask(PersistProduct persistProduct, RemoveProduct removeProduct, FetchProducts fetchProducts, Scanner scan) {
        this.persistProduct = persistProduct;
        this.removeProduct = removeProduct;
        this.fetchProducts = fetchProducts;
        this.scan = scan;
    }// AdminCategoryTask()

    /**
     * Exécution du choix sélectionné par l'utilisateur
     * @param choice choix
     * @return boolean déterminant si le retour au menu principal doit être effectué
     */
    public boolean run(int choice) {
        boolean backToMainMenu = false;
        switch (choice) {
            case 1:
                createProduct();
                break;
            case 2:
                updateProduct();
                break;
            case 3:
                deleteProduct();
                break;
            case 4:
                fetchAllProducts();
                break;
            case 5:
                backToMainMenu = true;
                break;
        }
        return backToMainMenu;
    }// run()

    /**
     * Création d'un produit
     */
    private void createProduct() {
        boolean hasErrors = true;
        do {
            try {
                System.out.println("Product name : ");
                String name = scan.next();
                System.out.println("Product description : ");
                String description = scan.next();
                System.out.println("Product price : ");
                double price = scan.nextDouble();
                System.out.println("Product quantity available : ");
                int quantityAvailable = scan.nextInt();
                System.out.println("Category ID : ");
                int categoryId = scan.nextInt();
                ProductDTO productDto = new ProductDTO(null,
                        name, description, price,
                        quantityAvailable, new CategoryDTO(categoryId, null), 0);
                persistProduct.save(productDto);
                hasErrors = false;
            } catch (ProductException e) {
                e.getFieldsErrors().forEach(System.out::println);
            } catch (AuthenticationException e) {
                System.err.println("You don't have the rights to create a product");
            } catch (ArgumentMissingException | DatabaseException e) {
                System.err.println("Technical error : Impossible to create a product");
            } catch (InputMismatchException | NumberFormatException nfe) {
                System.err.println("A numeric format is expected");
                scan.next();
            }
        } while (hasErrors);
    }// createProduct()

    /**
     * Mise à jour d'un produit
     */
    private void updateProduct() {
        System.out.println("Product ID : ");
        Integer productId = null;
        try {
            productId = scan.nextInt();
        } catch (Exception e) {
            scan.next();
        }
        fetchProducts.findById(productId)
                .ifPresent(product -> {
                    boolean hasErrors = true;
                    do {
                        try {
                            System.out.println("Product name (current = " + product.getName() + ") : ");
                            String name = scan.next();
                            System.out.println("Product description (current = " + product.getDescription() + ") : ");
                            String description = scan.next();
                            System.out.println("Product price (current = " + product.getPrice() + ") : ");
                            double price = scan.nextDouble();
                            System.out.println("Product quantity available (current = " + product.getQuantityAvailable() + ") : ");
                            int quantityAvailable = scan.nextInt();
                            System.out.println("Category ID (current = " + product.getCategory().getId() + " : ");
                            int categoryId = scan.nextInt();
                            ProductDTO productDto = new ProductDTO(product.getId(),
                                    name, description, price,
                                    quantityAvailable, new CategoryDTO(categoryId, null), 0);
                            persistProduct.update(productDto);
                            hasErrors = false;
                        } catch (ProductException e) {
                            e.getFieldsErrors().forEach(System.out::println);
                        } catch (AuthenticationException e) {
                            System.err.println("You don't have the rights to update a product");
                        } catch (ArgumentMissingException | DatabaseException e) {
                            System.err.println("Technical error : Impossible to update a product");
                        } catch (InputMismatchException | NumberFormatException nfe) {
                            System.err.println("A numeric format is expected");
                            scan.next();
                        }
                    } while (hasErrors);
                });
    }// updateProduct()

    /**
     * Suppression d'un produit
     */
    private void deleteProduct() {
        System.out.println("Product ID : ");
        int productId = -1;
        try {
            productId = scan.nextInt();
        } catch (Exception e) {
            scan.next();
        }
        try {
            this.removeProduct.execute(productId);
        } catch (AuthenticationException e) {
            System.err.println("You don't have the rights to remove a product");
        } catch (DatabaseException e) {
            System.err.println("Technical error : Impossible to remove a product");
        } catch (ProductException e) {
            e.getFieldsErrors().forEach(System.out::println);
        }
    }// deleteProduct()

    /**
     * Affichage de tous les produits par catégorie
     */
    private void fetchAllProducts() {
        Map<String, List<ProductDTO>> productsByCategory = this.fetchProducts.findAll(false);
        if (productsByCategory != null && !productsByCategory.isEmpty()) {
            productsByCategory.forEach((category, products) -> {
                System.out.println("*********** " + category + " *********** ");
                System.out.println();
                products.forEach(product -> System.out.println(" * " + product.getName() + " (id = " + product.getId() + ") "));
                System.out.println();
            });
        }
    }// fetchAllProducts()

}// AdminProductTask
