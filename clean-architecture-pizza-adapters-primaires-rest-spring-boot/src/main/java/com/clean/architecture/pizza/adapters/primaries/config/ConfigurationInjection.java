package com.clean.architecture.pizza.adapters.primaries.config;

import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.ProductRepositoryImpl;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationInjection {

    @Bean
    public FetchProducts fetchProductsService(){
        return new FetchProducts(new ProductRepositoryImpl());
    }// fetchProductsService()

}// ConfigurationInjection

