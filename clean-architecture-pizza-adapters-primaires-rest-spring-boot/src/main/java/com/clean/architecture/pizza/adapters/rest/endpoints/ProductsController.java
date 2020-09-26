package com.clean.architecture.pizza.adapters.rest.endpoints;

import com.clean.architecture.pizza.core.fetch.FetchProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Contrôleur gérant les produits
 */
@RestController
public class ProductsController {

    /**
     * Cas d'utilisations concernant la récupération de produits
     */
    private FetchProducts fetchProductsService;

    /**
     * Constructeur
     * @param fetchProductsService
     */
    @Autowired
    public ProductsController(FetchProducts fetchProductsService) {
        this.fetchProductsService = fetchProductsService;
    }// ProductsController()

    /**
     * Récupération de la liste de produits par catégorie.
     * @return ResponseEntity
     */
    @GetMapping("/productsByCategory")
    public ResponseEntity<?> getAllProductsByCategory(){
        return ResponseEntity.ok(this.fetchProductsService.findAll());
    }// getAllProductsByCategory()

}// ProductsController
