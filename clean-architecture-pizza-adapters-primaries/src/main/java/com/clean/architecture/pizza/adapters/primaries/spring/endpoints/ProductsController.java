package com.clean.architecture.pizza.adapters.primaries.spring.endpoints;

import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

    private FetchProducts fetchProductsService;

    @Autowired
    public ProductsController(FetchProducts fetchProductsService) {
        this.fetchProductsService = fetchProductsService;
    }// ProductsController()

    @GetMapping("/productsByCategory")
    public ResponseEntity<?> getAllProductsByCategory(){
        return ResponseEntity.ok(this.fetchProductsService.findAll());
    }// getAllProductsByCategory()

}// ProductsController
