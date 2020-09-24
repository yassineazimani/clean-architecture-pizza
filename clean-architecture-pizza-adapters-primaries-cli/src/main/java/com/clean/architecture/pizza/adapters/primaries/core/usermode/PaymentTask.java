package com.clean.architecture.pizza.adapters.primaries.core.usermode;

import com.clean.architecture.pizza.adapters.primaries.bank.KitPaymentCardBank;
import com.clean.architecture.pizza.core.enums.MoneyEnum;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.OrderException;
import com.clean.architecture.pizza.core.order.OrderProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class PaymentTask {

    private OrderProducts orderProducts;

    private final static Logger LOGGER = LogManager.getLogger(PaymentTask.class);

    public PaymentTask(OrderProducts orderProducts) {
        this.orderProducts = orderProducts;
    }// PaymentTask()

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
        System.out.println("Thank you ! Good meal and see you soon !");
    }// paymentOrder()

}// PaymentTask
