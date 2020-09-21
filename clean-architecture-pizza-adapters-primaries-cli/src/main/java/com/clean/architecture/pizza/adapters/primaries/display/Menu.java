package com.clean.architecture.pizza.adapters.primaries.display;

import clean.architecture.pizza.adapters.secondaries.hibernate.repositories.ProductRepositoryImpl;
import com.clean.architecture.pizza.core.fetch.FetchProducts;
import com.clean.architecture.pizza.core.model.ProductDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Menu {

    private FetchProducts fetchProducts;

    public Menu() {
        this.fetchProducts = new FetchProducts(new ProductRepositoryImpl());
    }// Menu()

    public List<ProductDTO> displayMenu(){
        Map<String, List<ProductDTO>> productsByCategory = this.fetchProducts.findAll();
        List<ProductDTO> allProducts = new ArrayList<>();
        if(productsByCategory != null){
            System.out.println("########################### MENU ########################");
            productsByCategory.forEach((category, products) -> {
                System.out.println();
                System.out.println(StringUtils.capitalize(category) + " : ");
                System.out.println();
                products.sort(Comparator.comparing(ProductDTO::getId));
                products.forEach(product -> System.out.println(product.getId() + " " + product.getName() + " " + product.getPrice() + "$"));
                allProducts.addAll(products);
            });
            System.out.println();
        }
        return allProducts;
    }// displayMenu()

}// Menu
