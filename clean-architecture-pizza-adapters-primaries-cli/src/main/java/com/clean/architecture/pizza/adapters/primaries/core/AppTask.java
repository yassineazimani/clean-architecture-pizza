package com.clean.architecture.pizza.adapters.primaries.core;

import com.clean.architecture.pizza.adapters.primaries.config.Configuration;
import com.clean.architecture.pizza.adapters.primaries.core.session.Session;
import com.clean.architecture.pizza.adapters.primaries.core.admin.AdminTask;
import com.clean.architecture.pizza.adapters.primaries.core.usermode.AuthTask;
import com.clean.architecture.pizza.adapters.primaries.core.usermode.OrderTask;
import com.clean.architecture.pizza.adapters.primaries.core.usermode.PaymentTask;
import com.clean.architecture.pizza.adapters.primaries.display.Menu;
import com.clean.architecture.pizza.core.admin.auth.FetchUser;
import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.order.OrderProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppTask {

    private static final Session session = new Session();

    private static final Logger LOGGER = LogManager.getLogger(AppTask.class);

    public static void run(String[] args){
        // Initialisation pour toute l'application console des services :
        FetchProducts fetchProducts = Configuration.fetchProducts();
        OrderProducts orderProducts = Configuration.orderProducts();
        PersistCategory persistCategory = Configuration.persistCategory(session);
        RemoveCategory removeCategory = Configuration.removeCategory(session);
        FetchCategory fetchCategory = Configuration.fetchCategory();
        final Menu menu = new Menu();
        FetchUser fetchUser = Configuration.fetchUser();
        // Application :
        if(args.length == 0) {
            runUserMode(menu, fetchProducts, orderProducts);
        }else{
            runAdminMode(args, menu, fetchUser, persistCategory, removeCategory, fetchCategory);
        }
    }// run()

    private static void runUserMode(Menu menu, FetchProducts fetchProducts, OrderProducts orderProducts){
        OrderTask orderTask = new OrderTask(menu, orderProducts, fetchProducts);
        PaymentTask paymentTask = new PaymentTask(orderProducts);
        try {
            while (true) {
                orderTask.takeOrder();
                paymentTask.paymentOrder();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }// runUserMode()

    private static void runAdminMode(String[] args,
                                     Menu menu,
                                     FetchUser fetchUser,
                                     PersistCategory persistCategory,
                                     RemoveCategory removeCategory,
                                     FetchCategory fetchCategory){
        try {
            int userId = Integer.valueOf(args[0]);
            AuthTask authTask = new AuthTask(fetchUser);
            authTask.login(userId, args[1])
                    .ifPresent(user -> session.setUserLogged(user));
            if(session.isAuthenticated()){
                AdminTask adminTask = new AdminTask(menu, persistCategory, removeCategory, fetchCategory);
                adminTask.main();
            }
        }catch(NumberFormatException e){
            LOGGER.error(e.getMessage());
            System.err.println("Args : userId (Integer)   password (String)");
        }
    }// runAdmin()

}// AppTask
