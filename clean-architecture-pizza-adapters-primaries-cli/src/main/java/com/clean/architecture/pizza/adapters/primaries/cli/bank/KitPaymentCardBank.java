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
package com.clean.architecture.pizza.adapters.primaries.cli.bank;

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
