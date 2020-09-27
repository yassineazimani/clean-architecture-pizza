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

import com.clean.architecture.pizza.adapters.primaries.cli.display.Menu;
import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.admin.product.PersistProduct;
import com.clean.architecture.pizza.core.admin.product.RemoveProduct;
import com.clean.architecture.pizza.core.admin.stats.StatsOrders;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Cette classe gère l'administration des données
 * de l'application.
 */
public class AdminTask {

    private Menu menu;

    private Scanner scan;

    private AdminCategoryTask adminCategoryTask;

    private AdminProductTask adminProductTask;

    private AdminStatsTask adminStatsTask;

    private final static Logger LOGGER = LogManager.getLogger(AdminTask.class);

    public AdminTask(Menu menu,
                     PersistCategory persistCategory,
                     RemoveCategory removeCategory,
                     FetchCategory fetchCategory,
                     PersistProduct persistProduct,
                     RemoveProduct removeProduct,
                     FetchProducts fetchProducts,
                     StatsOrders statsOrders) {
        this.menu = menu;
        this.scan = new Scanner(System.in);
        this.adminCategoryTask = new AdminCategoryTask(persistCategory, removeCategory, fetchCategory, scan);
        this.adminProductTask = new AdminProductTask(persistProduct, removeProduct, fetchProducts, scan);
        this.adminStatsTask = new AdminStatsTask(statsOrders);
    }// AdminTask()

    /**
     * Démarrage du mode administration
     */
    public void main(){
        boolean quit = false;
        while(!quit){
            menu.displayAdminMenu();
            int choice = inputChoice();
            quit = manageChoiceFromMainMenu(choice);
        }
    }// main()

    /**
     * Saisie clavier du choix de l'utilisateur
     * @return choix saisi
     */
    private int inputChoice(){
        int result = 1;
        boolean keep = true;
        while(keep) {
            try {
                System.out.println("Your choice ? ");
                return scan.nextInt();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                scan.next();
            }
        }
        return result;
    }// inputChoice()

    /**
     * Exécution du choix sélectionné par l'utilisateur
     * @param choice Choix
     * @return boolean déterminant si l'application doit se terminer
     */
    private boolean manageChoiceFromMainMenu(int choice){
        if(choice == 1){
            loopAdminMenuCategory();
        }else if(choice == 2){
            loopAdminMenuProduct();
        }else if(choice == 3){
            loopAdminMenuStats();
        }else if(choice == 4){
            return true;
        }
        return false;
    }// manageChoiceFromMainMenu()

    /**
     * Ecran administration des catégories
     */
    private void loopAdminMenuCategory(){
        boolean keep = true;
        do{
            menu.displayAdminMenuCategory();
            int choiceMenuCategory = inputChoice();
            keep = !this.adminCategoryTask.run(choiceMenuCategory);
        } while(keep);
    }// loopAdminMenuCategory()

    /**
     * Ecran administration des produits
     */
    private void loopAdminMenuProduct(){
        boolean keep = true;
        do{
            menu.displayAdminMenuProduct();
            int choiceMenuProduct = inputChoice();
            keep = !this.adminProductTask.run(choiceMenuProduct);
        }while(keep);
    }// loopAdminMenuProduct()

    /**
     * Ecran administration des statistiques
     */
    private void loopAdminMenuStats(){
        boolean keep = true;
        do{
            menu.displayAdminMenuStats();
            int choiceMenuStats = inputChoice();
            keep = !this.adminStatsTask.run(choiceMenuStats);
        }while(keep);
    }// loopAdminMenuStats()

}// AdminTask
