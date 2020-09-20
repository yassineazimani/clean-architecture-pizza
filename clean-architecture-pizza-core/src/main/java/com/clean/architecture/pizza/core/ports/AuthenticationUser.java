package com.clean.architecture.pizza.core.ports;

/**
 * Pour obtenir les statistiques ou faire de la gestion de produits,
 * on se contentera juste de savoir si l'utilisateur est connecté ou
 * non (il n'y a pas de rôles attribués à l'utilisateur, il est automatiquement
 * administrateur).
 */
public interface AuthenticationUser {

    boolean isAuthenticated();

}// AuthenticationUser()
