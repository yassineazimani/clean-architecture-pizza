/*
 * Copyright 2020 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package clean.architecture.pizza.adapters.secondaries.hibernate.config;

import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.TransactionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Map;

public abstract class AbstractRepository<T> {

    private EntityManagerFactory factory;

    protected EntityManager entityManager;

    private final static Logger LOGGER = LogManager.getLogger(AbstractRepository.class);

    public AbstractRepository() {
        Map<String, Object> customPersistenceConfiguration = CustomPropertiesConfig.getCustomPersistenceConfiguration();
        this.factory = Persistence.createEntityManagerFactory("PizzasDB", customPersistenceConfiguration);
        this.entityManager = factory.createEntityManager();
    }// AbstractRepository()

    /**
     * Sauvegarde d'une entité
     * @param entity
     * @throws DatabaseException
     */
    public void save(T entity) throws DatabaseException {
        if(entity != null){
            try {
                this.entityManager.persist(entity);
                this.entityManager.flush();
            }catch(RollbackException re){
                LOGGER.error(re.getMessage(), re);
                throw new DatabaseException("Impossible to save entity");
            }
        }
    }// save()

    /**
     * Mise à jour d'une entité
     * @param entity
     * @throws DatabaseException
     */
    public void update(T entity) throws DatabaseException {
        if(entity != null){
            try {
                this.entityManager.merge(entity);
                this.entityManager.flush();
            }catch(RollbackException re){
                LOGGER.error(re.getMessage(), re);
                throw new DatabaseException("Impossible to update entity");
            }
        }
    }// update()

    /**
     * Commence une nouvelle transaction.
     */
    public void begin(){
        if(!this.entityManager.getTransaction().isActive())
            this.entityManager.getTransaction().begin();
    }// begin()

    /**
     * Commit la transaction.
     * @throws IllegalStateException
     * @throws TransactionException Si le commit échoue
     */
    public void commit() throws TransactionException{
        try {
            this.entityManager.getTransaction().commit();
        }catch(RollbackException re){
            LOGGER.error(re.getMessage(), re);
            throw new TransactionException(re.getMessage());
        }
    }// commit()

    /**
     * Annule la dernière transaction.
     * @throws IllegalStateException
     * @throws DatabaseException si erreur au niveau de la base de données
     */
    public void rollback(){
        this.entityManager.getTransaction().rollback();
    }// rollback()

}// AbstractRepository
