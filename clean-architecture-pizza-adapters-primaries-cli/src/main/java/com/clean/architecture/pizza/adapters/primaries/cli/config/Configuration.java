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
package com.clean.architecture.pizza.adapters.primaries.cli.config;

import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.CategoryRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.OrderRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.ProductRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.UserRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.infrastructure.coins.CoinsInfrastructureImpl;
import com.clean.architecture.pizza.core.admin.auth.FetchUser;
import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.admin.product.PersistProduct;
import com.clean.architecture.pizza.core.admin.product.RemoveProduct;
import com.clean.architecture.pizza.core.admin.stats.StatsOrders;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.order.OrderProducts;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;

/**
 * Définition des services par l'implémentation de notre choix
 * des adaptateurs des cas d'utilisation.
 */
public class Configuration {

    /**
     * Service FetchProducts
     * @return FetchProducts
     */
    public static FetchProducts fetchProducts(){
        return new FetchProducts(new ProductRepositoryImpl());
    }// fetchProducts()

    /**
     * Service OrderProducts
     * @return OrderProducts
     */
    public static OrderProducts orderProducts(){
        return new OrderProducts(new OrderRepositoryImpl(), new ProductRepositoryImpl(), new CoinsInfrastructureImpl());
    }// orderProducts()

    /**
     * Service FetchUser
     * @return FetchUser
     */
    public static FetchUser fetchUser(){
        return new FetchUser(new BCryptImpl(), new UserRepositoryImpl());
    }// fetchUser()

    /**
     * Service PersistCategory
     * @param  authenticationUser Port authenticationUser
     * @return PersistCategory
     */
    public static PersistCategory persistCategory(AuthenticationUser authenticationUser){
        return new PersistCategory(authenticationUser, new CategoryRepositoryImpl());
    }// persistCategory()

    /**
     * Service RemoveCategory
     * @param authenticationUser Port authenticationUser
     * @return RemoveCategory
     */
    public static RemoveCategory removeCategory(AuthenticationUser authenticationUser){
        return new RemoveCategory(new CategoryRepositoryImpl(), authenticationUser);
    }// removeCategory()

    /**
     * Service FetchCategory
     * @return FetchCategory
     */
    public static FetchCategory fetchCategory() {
        return new FetchCategory(new CategoryRepositoryImpl());
    }// fetchCategory()

    /**
     * Service PersistProduct
     * @param authenticationUser
     * @return PersistProduct
     */
    public static PersistProduct persistProduct(AuthenticationUser authenticationUser) {
        return new PersistProduct(new ProductRepositoryImpl(), authenticationUser);
    }// persistProduct()

    /**
     * Service RemoveProduct
     * @param authenticationUser
     * @return RemoveProduct
     */
    public static RemoveProduct removeProduct(AuthenticationUser authenticationUser) {
        return new RemoveProduct(new ProductRepositoryImpl(), authenticationUser);
    }// persistProduct()

    /**
     * Service StatsOrders
     * @param authenticationUser
     * @return StatsOrders
     */
    public static StatsOrders statsOrders(AuthenticationUser authenticationUser){
        return new StatsOrders(new OrderRepositoryImpl(), authenticationUser);
    }// statsOrders()

}// Configuration
