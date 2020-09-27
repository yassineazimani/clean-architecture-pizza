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
package com.clean.architecture.pizza.adapters.primaries.cli.core.usermode;

import com.clean.architecture.pizza.adapters.primaries.cli.display.Menu;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.OrderException;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.order.OrderProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Gestion des commandes passées par
 * un client.
 */
public class OrderTask {

    private Menu menu;

    private OrderProducts orderProducts;

    private FetchProducts fetchProducts;

    private final static Logger LOGGER = LogManager.getLogger(OrderTask.class);

    public OrderTask(Menu menu,
                     OrderProducts orderProducts,
                     FetchProducts fetchProducts) {
        this.menu = menu;
        this.orderProducts = orderProducts;
        this.fetchProducts = fetchProducts;
    }// OrderTask()

    /**
     * Sélection d'une commande par un client.
     */
    public void takeOrder() {
        boolean finishOrder = false;
        while(!finishOrder) {
            List<ProductDTO> products = this.menu.displayMenu();
            int productId = this.choiceProduct();
            int quantity = this.choiceQuantity(productId);
            try {
                Optional<ProductDTO> optProductOrdered = products.stream()
                        .filter(p -> p.getId().equals(productId))
                        .findAny();
                if(optProductOrdered.isPresent()){
                    ProductDTO productOrdered = optProductOrdered.get();
                    productOrdered.setQuantityOrdered(quantity);
                    orderProducts.addProduct(productOrdered);
                }
            } catch (OrderException orderException){
                System.err.println(orderException.getMessage());
            } catch (DatabaseException | ArgumentMissingException e){
                System.err.println("Technical error : please contact the customer service");
            }
            finishOrder = this.finishOrder();
        }
    }// takeOrder()

    /**
     * Termine la commande d'un client.
     * @return boolean
     */
    private boolean finishOrder(){
        boolean keep = true;
        boolean result = false;
        Scanner scan = new Scanner(System.in);
        while(keep) {
            try {
                System.out.println("Do you finish your order ? (O/N)");
                String choice = scan.next();
                result = choice.equalsIgnoreCase("o");
                keep = false;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return result;
    }// finishOrder()

    /**
     * Sélection d'un produit par un client
     * @return identifiant du produit sélectionné
     */
    private Integer choiceProduct(){
        boolean keep = true;
        int result = 1;
        Scanner scan = new Scanner(System.in);
        while(keep) {
            try {
                System.out.print("What is your choice (Give number) ? ");
                System.out.println();
                result = scan.nextInt();
                if(fetchProducts.existsById(result)) {
                    keep = false;
                }else{
                    System.out.println("Unknown choice");
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                scan.next();
            }
        }
        return result;
    }// choiceProduct()

    /**
     * Sélection de la quantité du produit sélectionné
     * par un client.
     * @param productIdSelected Identifiant du produit sélectionné
     * @return quantité saisie
     */
    private Integer choiceQuantity(int productIdSelected){
        boolean keep = true;
        int result = 1;
        Optional<ProductDTO> optProduct = fetchProducts.findById(productIdSelected);
        Scanner scan = new Scanner(System.in);
        while(keep) {
            System.out.print("How many ?");
            try {
                result = scan.nextInt();
                if(result == 0){
                    System.err.println("You must select a positive quantity");
                }else{
                    keep = false;
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                scan.next();
            }
        }
        return result;
    }// choiceProduct()
}// OrderTask
