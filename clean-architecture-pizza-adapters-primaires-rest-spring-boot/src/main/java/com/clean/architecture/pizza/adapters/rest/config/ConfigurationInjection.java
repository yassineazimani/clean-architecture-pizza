package com.clean.architecture.pizza.adapters.rest.config;

import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.ProductRepositoryImpl;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Définition des beans par l'implémentation de notre choix
 * des adaptateurs des cas d'utilisation.
 */
@Configuration
public class ConfigurationInjection {

    /**
     * Bean FetchProducts
     * @return FetchProducts
     */
    @Bean
    public FetchProducts fetchProductsService(){
        return new FetchProducts(new ProductRepositoryImpl());
    }// fetchProductsService()

}// ConfigurationInjection

