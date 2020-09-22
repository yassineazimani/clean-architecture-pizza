package com.clean.architecture.pizza.core.ports;

import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.TransactionException;

public interface ManagementTransaction {

    /**
     * Commence une nouvelle transaction.
     */
    void begin();

    /**
     * Commit la transaction.
     * @throws IllegalStateException
     * @throws TransactionException Si le commit échoue
     */
    void commit() throws TransactionException;

    /**
     * Annule la dernière transaction.
     * @throws IllegalStateException
     * @throws DatabaseException si erreur au niveau de la base de données
     */
    void rollback();

}// ManagementTransaction
