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
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.order.OrderProducts;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;

public class Configuration {

    public static FetchProducts fetchProducts(){
        return new FetchProducts(new ProductRepositoryImpl());
    }// fetchProducts()

    public static OrderProducts orderProducts(){
        return new OrderProducts(new OrderRepositoryImpl(), new ProductRepositoryImpl(), new CoinsInfrastructureImpl());
    }// orderProducts()

    public static FetchUser fetchUser(){
        return new FetchUser(new BCryptImpl(), new UserRepositoryImpl());
    }// fetchUser()

    public static PersistCategory persistCategory(AuthenticationUser authenticationUser){
        return new PersistCategory(authenticationUser, new CategoryRepositoryImpl());
    }// persistCategory()

    public static RemoveCategory removeCategory(AuthenticationUser authenticationUser){
        return new RemoveCategory(new CategoryRepositoryImpl(), authenticationUser);
    }// removeCategory()

    public static FetchCategory fetchCategory() {
        return new FetchCategory(new CategoryRepositoryImpl());
    }// fetchCategory()

    public static PersistProduct persistProduct(AuthenticationUser authenticationUser) {
        return new PersistProduct(new ProductRepositoryImpl(), authenticationUser);
    }// persistProduct()

    public static RemoveProduct removeProduct(AuthenticationUser authenticationUser) {
        return new RemoveProduct(new ProductRepositoryImpl(), authenticationUser);
    }// persistProduct()

}// Configuration
