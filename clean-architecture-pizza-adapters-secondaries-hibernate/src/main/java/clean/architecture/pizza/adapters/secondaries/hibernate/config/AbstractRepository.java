package clean.architecture.pizza.adapters.secondaries.hibernate.config;

import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
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

    public void save(T entity) throws DatabaseException {
        if(entity != null){
            try {
                this.entityManager.getTransaction().begin();
                this.entityManager.persist(entity);
                this.entityManager.flush();
                this.entityManager.getTransaction().commit();
            }catch(RollbackException re){
                LOGGER.error(re.getMessage(), re);
                throw new DatabaseException("Impossible to save entity");
            }
        }
    }// save()

    public void update(T entity) throws DatabaseException {
        if(entity != null){
            try {
                this.entityManager.getTransaction().begin();
                this.entityManager.merge(entity);
                this.entityManager.flush();
                this.entityManager.getTransaction().commit();
            }catch(RollbackException re){
                LOGGER.error(re.getMessage(), re);
                throw new DatabaseException("Impossible to update entity");
            }
        }
    }// update()

}// AbstractRepository
