package com.clean.architecture.pizza.adapters.primaries.spring.config;

import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.ProductRepositoryImpl;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public FetchProducts fetchProductsService(){
        return new FetchProducts(new ProductRepositoryImpl());
    }// fetchProductsService()

}// Configuration
