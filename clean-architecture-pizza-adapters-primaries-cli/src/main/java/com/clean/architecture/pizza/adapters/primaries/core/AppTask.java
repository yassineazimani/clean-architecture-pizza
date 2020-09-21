package com.clean.architecture.pizza.adapters.primaries.core;

import com.clean.architecture.pizza.adapters.primaries.config.Configuration;
import com.clean.architecture.pizza.adapters.primaries.display.Menu;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.order.OrderProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppTask {

    private static final Logger LOGGER = LogManager.getLogger(AppTask.class);

    public static void run(){
        // Initialisation pour toute l'application console des services :
        FetchProducts fetchProducts = Configuration.fetchProducts();
        OrderProducts orderProducts = Configuration.orderProducts();
        // Application :
        Menu menu = new Menu();
        OrderTask orderTask = new OrderTask(menu, orderProducts, fetchProducts);
        PaymentTask paymentTask = new PaymentTask(orderProducts);
        try {
            while(true) {
                orderTask.takeOrder();
                paymentTask.paymentOrder();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }// run()

}// AppTask
