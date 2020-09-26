package com.clean.architecture.pizza.adapters.primaries.cli.core.admin;

import com.clean.architecture.pizza.adapters.primaries.cli.display.Menu;
import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.admin.product.PersistProduct;
import com.clean.architecture.pizza.core.admin.product.RemoveProduct;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class AdminTask {

    private Menu menu;

    private Scanner scan;

    private AdminCategoryTask adminCategoryTask;

    private AdminProductTask adminProductTask;

    private final static Logger LOGGER = LogManager.getLogger(AdminTask.class);

    public AdminTask(Menu menu,
                     PersistCategory persistCategory,
                     RemoveCategory removeCategory,
                     FetchCategory fetchCategory,
                     PersistProduct persistProduct,
                     RemoveProduct removeProduct,
                     FetchProducts fetchProducts) {
        this.menu = menu;
        this.scan = new Scanner(System.in);
        this.adminCategoryTask = new AdminCategoryTask(persistCategory, removeCategory, fetchCategory, scan);
        this.adminProductTask = new AdminProductTask(persistProduct, removeProduct, fetchProducts, scan);
    }// AdminTask()

    public void main(){
        boolean quit = false;
        while(!quit){
            menu.displayAdminMenu();
            int choice = inputChoice();
            quit = manageChoiceFromMainMenu(choice);
        }
    }// main()

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

    private boolean manageChoiceFromMainMenu(int choice){
        if(choice == 1){
            loopAdminMenuCategory();
        }else if(choice == 2){
            loopAdminMenuProduct();
        }else if(choice == 3){
            menu.displayAdminMenuStats();
            int choiceMenuStats = inputChoice();
        }else if(choice == 4){
            return true;
        }
        return false;
    }// manageChoiceFromMainMenu()

    private void loopAdminMenuCategory(){
        boolean keep = true;
        do{
            menu.displayAdminMenuCategory();
            int choiceMenuCategory = inputChoice();
            keep = !this.adminCategoryTask.run(choiceMenuCategory);
        } while(keep);
    }// loopAdminMenuCategory()

    private void loopAdminMenuProduct(){
        boolean keep = true;
        do{
            menu.displayAdminMenuProduct();
            int choiceMenuProduct = inputChoice();
            keep = !this.adminProductTask.run(choiceMenuProduct);
        }while(keep);
    }// loopAdminMenuProduct()

}// AdminTask
