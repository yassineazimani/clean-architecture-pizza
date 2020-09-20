package com.clean.architecture.pizza.adapters.primaries.console.core;

import com.clean.architecture.pizza.adapters.primaries.console.display.Menu;
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

    public void takeOrder() throws ArgumentMissingException, DatabaseException, OrderException {
        boolean finishOrder = false;
        while(!finishOrder) {
            List<ProductDTO> products = this.menu.displayMenu();
            int productId = this.choiceProduct();
            int quantity = this.choiceQuantity(productId);
            for(int i = 0; i < quantity; ++i) {
                orderProducts.addProduct(products.stream().filter(p -> p.getId().equals(productId)).findAny().get());
            }
            finishOrder = this.finishOrder();
        }
    }// takeOrder()

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

    private Integer choiceProduct(){
        boolean keep = true;
        Integer result = 1;
        Scanner scan = new Scanner(System.in);
        while(keep) {
            try {
                System.out.println("What is your choice (Give number) ? ");
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

    private Integer choiceQuantity(int productIdSelected){
        boolean keep = true;
        Integer result = 1;
        Optional<ProductDTO> optProduct = fetchProducts.findById(productIdSelected);
        Scanner scan = new Scanner(System.in);
        while(keep) {
            System.out.println("How many ?");
            try {
                result = scan.nextInt();
                if(result == 0){
                    System.err.println("You must select a positive quantity");
                }else if(optProduct.isPresent() && optProduct.get().getQuantityAvailable() < result){
                    System.err.println("Sorry, you ask more than we have...");
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
}
