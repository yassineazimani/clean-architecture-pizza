package com.clean.architecture.pizza.adapters.primaries.environment;

import com.clean.architecture.pizza.adapters.primaries.console.config.Configuration;
import com.clean.architecture.pizza.adapters.primaries.console.core.OrderTask;
import com.clean.architecture.pizza.adapters.primaries.console.core.PaymentTask;
import com.clean.architecture.pizza.adapters.primaries.console.display.Menu;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.order.OrderProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class Environment {

    private static final Logger LOGGER = LogManager.getLogger(Environment.class);

    /**
     * KO, doesn't work
     * @deprecated
     * @param env
     * @param args
     */
    public static void run(EnvironmentEnum env, String[] args){
        switch (env){
            case CONSOLE:
                runConsoleApp();
                break;
            default:
                runSpringApp(args);
                break;
        }
    }// run()

    public static void runConsoleApp(){
        // Initialisation pour toute l'application console des services :
        FetchProducts fetchProducts = Configuration.fetchProducts();
        OrderProducts orderProducts = Configuration.orderProducts();
        // Application :
        Menu menu = new Menu();
        OrderTask orderTask = new OrderTask(menu, orderProducts, fetchProducts);
        PaymentTask paymentTask = new PaymentTask(orderProducts);
        try {
            orderTask.takeOrder();
            paymentTask.paymentOrder();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }// runConsoleApp()

    /**
     * KO, doesn't work
     * @param args
     */
    private static void runSpringApp(String[] args){
        SpringApplication.run(Environment.class, args);
    }

}// Environment
