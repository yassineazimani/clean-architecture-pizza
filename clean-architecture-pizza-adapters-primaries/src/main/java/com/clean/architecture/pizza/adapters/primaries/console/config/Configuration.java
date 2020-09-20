package com.clean.architecture.pizza.adapters.primaries.console.config;

import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.OrderRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.ProductRepositoryImpl;
import clean.architecture.pizza.adapters.secondaries.infrastructure.coins.CoinsInfrastructureImpl;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.order.OrderProducts;

public class Configuration {

    public static FetchProducts fetchProducts(){
        return new FetchProducts(new ProductRepositoryImpl());
    }// fetchProducts()

    public static OrderProducts orderProducts(){
        return new OrderProducts(new OrderRepositoryImpl(), new ProductRepositoryImpl(), new CoinsInfrastructureImpl());
    }// orderProducts()

}// Configuration
