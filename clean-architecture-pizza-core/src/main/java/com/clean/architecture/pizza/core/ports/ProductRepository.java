package com.clean.architecture.pizza.core.ports;

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.ProductDTO;

import java.util.List;
import java.util.Optional;

/**
 * Gestion du stockage des produits
 */
public interface ProductRepository extends ManagementTransaction{

    /**
     * Récupération de tous les produits
     * @return Tous les produits
     */
    List<ProductDTO> findAllProducts();

    /**
     * Récupération d'un produit
     * @param id Identifiant du produit
     * @return Optional
     */
    Optional<ProductDTO> findById(int id);

    /**
     * Vérifie l'existence d'un produit
     * @param id Identifiant du produit
     * @return boolean
     */
    boolean existsById(int id);

    /**
     * Suppression d'un produit
     * @param id Identifiant du produit
     */
    void deleteById(int id) throws DatabaseException;

    /**
     * Sauvegarde d'un produit
     * @param product Produit
     */
    void save(ProductDTO product) throws DatabaseException, ArgumentMissingException;

    /**
     * Mise à jour d'un produit
     * @param product Produit
     */
    void update(ProductDTO product) throws DatabaseException, ArgumentMissingException;
}// ProductRepository
