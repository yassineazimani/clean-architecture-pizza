package com.clean.architecture.pizza.core.fetch;

import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Cette classe regroupe l'ensemble des cas d'utilisations
 * liées à la récupération d'un ou plusieurs produits.
 */
public class FetchProducts {

    private ProductRepository productRepository;

    public FetchProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }// FetchProducts()

    /**
     * Récupération de tous les produits de l'application.
     * Si un type de produit n'est plus disponible, la clé de la map représentant
     * la catégorie sera alors absente.
     * @return Map de {@see Product} regroupés par nom de Catégorie (Pizzas, Boissons, Desserts).
     */
    public Map<String, List<ProductDTO>> findAll() {
        List<ProductDTO> products = productRepository.findAllProducts();
        return products.stream()
                .filter(product -> product.getQuantityAvailable() > 0)
                .collect(Collectors.groupingBy(p -> p.getCategory().getName()));
    }// findAll()

    /**
     * Récupère un produit en fonction de son identifiant.
     * @param id Identifiant du produit
     * @return Optional de {@see Product}
     */
    public Optional<ProductDTO> findById(int id) {
        return productRepository.findById(id);
    }// findById()

    /**
     * Vérifie l'existence d'un produit
     * @param id Identifiant du produit
     * @return boolean
     */
    public boolean existsById(int id) {
        return productRepository.existsById(id);
    }// existsById()
}// FetchProducts
