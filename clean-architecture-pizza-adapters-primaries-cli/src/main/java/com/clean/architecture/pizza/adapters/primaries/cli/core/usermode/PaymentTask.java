package com.clean.architecture.pizza.adapters.primaries.cli.core.usermode;

import com.clean.architecture.pizza.adapters.primaries.cli.bank.KitPaymentCardBank;
import com.clean.architecture.pizza.core.enums.MoneyEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.OrderException;
import com.clean.architecture.pizza.core.order.OrderProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Gestion du paiement une commande passée
 * par un client.
 */
public class PaymentTask {

    private OrderProducts orderProducts;

    private final static Logger LOGGER = LogManager.getLogger(PaymentTask.class);

    public PaymentTask(OrderProducts orderProducts) {
        this.orderProducts = orderProducts;
    }// PaymentTask()

    /**
     * Paiement d'une commande
     * @throws ArgumentMissingException
     * @throws DatabaseException
     * @throws OrderException
     */
    public void paymentOrder() throws ArgumentMissingException, DatabaseException, OrderException {
        boolean keep = true;
        Scanner scan = new Scanner(System.in);
        String choice = "A";
        while(keep) {
            try {
                System.out.println("Which type of money do you want to use ?");
                System.out.println("A - Coins");
                System.out.println("B - Card");
                choice = scan.next();
                keep = !choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b");
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        if(choice.equalsIgnoreCase("a")){
            orderProducts.paymentOrder(MoneyEnum.COINS);
        }else{
            final String transactionCbId = KitPaymentCardBank.payment();
            orderProducts.paymentOrder(MoneyEnum.CB, transactionCbId);
        }
        System.out.println();
        System.out.println("Thank you ! Good meal and see you soon !");
        System.out.println();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }// paymentOrder()

}// PaymentTask
