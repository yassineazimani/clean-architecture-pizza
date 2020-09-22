package com.clean.architecture.pizza.core.ports;

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.CategoryDTO;

import java.util.Optional;

public interface CategoryRepository extends ManagementTransaction{

    /**
     * Vérifie l'existence d'une catégorie
     * @param id Identifiant de la catégorie
     * @return boolean
     */
    boolean existsById(int id);

    /**
     * Suppression d'une catégorie
     * @param id Identifiant de la catégorie
     */
    void deleteById(int id) throws DatabaseException;

    /**
     * Récupération d'une catégorie
     * @param id Identifiant de la catégorie
     * @return Optional {@see CategoryDTO}
     */
    Optional<CategoryDTO> findById(int id);

    /**
     * Sauvegarde d'une catégorie
     * @param category Catégorie
     */
    void save(CategoryDTO category) throws DatabaseException, ArgumentMissingException;

    /**
     * Mise à jour d'une catégorie
     * @param category Catégorie
     */
    void update(CategoryDTO category) throws DatabaseException, ArgumentMissingException;

}// CategoryRepository
