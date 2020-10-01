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
package com.clean.architecture.pizza.adapters.rest.config;

import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.CategoryRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.OrderRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.ProductRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.UserRepositoryImpl;
import com.clean.architecture.pizza.adapters.rest.services.AuthenticationUserImpl;
import com.clean.architecture.pizza.adapters.rest.utils.PasswordEncoderImpl;
import com.clean.architecture.pizza.core.admin.auth.FetchUser;
import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.admin.product.PersistProduct;
import com.clean.architecture.pizza.core.admin.product.RemoveProduct;
import com.clean.architecture.pizza.core.admin.stats.StatsOrders;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Définition des beans par l'implémentation de notre choix
 * des adaptateurs des cas d'utilisation.
 */
@Configuration
public class ConfigurationInjection {

    @Bean
    public AuthenticationUserImpl authenticationUserImpl(){
        return new AuthenticationUserImpl();
    }// authenticationUserImpl()

    /**
     * Bean FetchProducts
     * @return FetchProducts
     */
    @Bean
    public FetchProducts fetchProductsService(){
        return new FetchProducts(new ProductRepositoryImpl());
    }// fetchProductsService()

    /**
     * Bean FetchCategories
     * @return FetchCategories
     */
    @Bean
    public FetchCategory fetchCategoriesService(){
        return new FetchCategory(new CategoryRepositoryImpl());
    }// fetchCategoriesService()

    @Bean
    public FetchUser fetchUser(){
        return new FetchUser(new PasswordEncoderImpl(new BCryptPasswordEncoder()), new UserRepositoryImpl());
    }// fetchUser()

    @Bean
    public PersistCategory persistCategory(){
        return new PersistCategory(authenticationUserImpl(), new CategoryRepositoryImpl());
    }// persistCategory()

    @Bean
    public RemoveCategory removeCategory(){
        return new RemoveCategory(new CategoryRepositoryImpl(), authenticationUserImpl());
    }// removeCategory()

    @Bean
    public PersistProduct persistProduct(){
        return new PersistProduct(new ProductRepositoryImpl(), authenticationUserImpl());
    }// persistProduct()

    @Bean
    public RemoveProduct removeProduct(){
        return new RemoveProduct(new ProductRepositoryImpl(), authenticationUserImpl());
    }// removeProduct()

    @Bean
    public StatsOrders statsOrders(){
        return new StatsOrders(new OrderRepositoryImpl(), authenticationUserImpl());
    }// statsorders()

}// ConfigurationInjection

