package com.clean.architecture.pizza.adapters.primaries.cli.core;

import com.clean.architecture.pizza.adapters.primaries.cli.config.Configuration;
import com.clean.architecture.pizza.adapters.primaries.cli.core.usermode.PaymentTask;
import com.clean.architecture.pizza.adapters.primaries.cli.core.session.Session;
import com.clean.architecture.pizza.adapters.primaries.cli.core.admin.AdminTask;
import com.clean.architecture.pizza.adapters.primaries.cli.core.usermode.AuthTask;
import com.clean.architecture.pizza.adapters.primaries.cli.core.usermode.OrderTask;
import com.clean.architecture.pizza.adapters.primaries.cli.display.Menu;
import com.clean.architecture.pizza.core.admin.auth.FetchUser;
import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.admin.product.PersistProduct;
import com.clean.architecture.pizza.core.admin.product.RemoveProduct;
import com.clean.architecture.pizza.core.admin.stats.StatsOrders;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.order.OrderProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Coeur de l'application
 */
public class AppTask {

    private static final Session session = new Session();
    private static final FetchProducts fetchProducts = Configuration.fetchProducts();
    private static final PersistProduct persistProduct = Configuration.persistProduct(session);
    private static final RemoveProduct removeProduct = Configuration.removeProduct(session);
    private static final OrderProducts orderProducts = Configuration.orderProducts();
    private static final PersistCategory persistCategory = Configuration.persistCategory(session);
    private static final RemoveCategory removeCategory = Configuration.removeCategory(session);
    private static final FetchCategory fetchCategory = Configuration.fetchCategory();
    private static final StatsOrders statsOrders = Configuration.statsOrders(session);
    private static final Menu menu = new Menu();
    private static final FetchUser fetchUser = Configuration.fetchUser();
    private static final Logger LOGGER = LogManager.getLogger(AppTask.class);

    /**
     * Exécution de l'application
     * @param args arguments
     */
    public static void run(String[] args){
        if(args.length == 0) {
            runUserMode(menu, fetchProducts, orderProducts);
        }else{
            runAdminMode(args,
                    menu,
                    fetchUser,
                    persistCategory,
                    removeCategory,
                    fetchCategory,
                    persistProduct,
                    removeProduct,
                    fetchProducts,
                    statsOrders);
        }
    }// run()

    /**
     * Application mode client
     * @param menu
     * @param fetchProducts
     * @param orderProducts
     */
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

    /**
     * Application mode administrateur
     * @param args
     * @param menu
     * @param fetchUser
     * @param persistCategory
     * @param removeCategory
     * @param fetchCategory
     * @param persistProduct
     * @param removeProduct
     * @param fetchProducts
     * @param statsOrders
     */
    private static void runAdminMode(String[] args,
                                     Menu menu,
                                     FetchUser fetchUser,
                                     PersistCategory persistCategory,
                                     RemoveCategory removeCategory,
                                     FetchCategory fetchCategory,
                                     PersistProduct persistProduct,
                                     RemoveProduct removeProduct,
                                     FetchProducts fetchProducts,
                                     StatsOrders statsOrders){
        try {
            int userId = Integer.valueOf(args[0]);
            AuthTask authTask = new AuthTask(fetchUser);
            authTask.login(userId, args[1])
                    .ifPresent(user -> session.setUserLogged(user));
            if(session.isAuthenticated()){
                AdminTask adminTask = new AdminTask(menu,
                        persistCategory,
                        removeCategory,
                        fetchCategory,
                        persistProduct,
                        removeProduct,
                        fetchProducts,
                        statsOrders);
                adminTask.main();
            }else{
                System.err.println("Wrong credentials");
            }
        }catch(NumberFormatException e){
            LOGGER.error(e.getMessage());
            System.err.println("Args : userId (Integer)   password (String)");
        }
    }// runAdmin()

}// AppTask
