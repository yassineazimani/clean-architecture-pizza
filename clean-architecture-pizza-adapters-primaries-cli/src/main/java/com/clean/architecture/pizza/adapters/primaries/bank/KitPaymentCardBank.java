package com.clean.architecture.pizza.adapters.primaries.bank;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Scanner;

/**
 * Classe simulant le kit fournit par une banque
 * afin de réaliser le paiement par carte bancaire.
 */
public class KitPaymentCardBank {

    /**
     * Simulation "Iframe" de la solution bancaire.
     * @return n° de transaction bancaire
     */
    public static String payment(){
        Scanner scan = new Scanner(System.in);
        System.out.println("N°Card : ");
        scan.next();
        System.out.println("PIN : ");
        scan.next();
        return RandomStringUtils.random(15, true, true);
    }// payment()

}// KitPaymentCardBank
