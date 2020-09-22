package clean.architecture.pizza.adapters.secondaries.hibernate.repositories;

import clean.architecture.pizza.adapters.secondaries.hibernate.config.AbstractRepository;
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.Category;
import clean.architecture.pizza.adapters.secondaries.hibernate.mappers.CategoryMapper;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.ports.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.Optional;

public class CategoryRepositoryImpl extends AbstractRepository<Category>
        implements CategoryRepository {

    private final static Logger LOGGER = LogManager.getLogger(CategoryRepositoryImpl.class);

    @Override
    public boolean existsById(int id) {
        return this.findById(id).isPresent();
    }// existsById()

    @Override
    public void deleteById(int id) throws DatabaseException {
        try {
            this.entityManager.getTransaction().begin();
            Query query = this.entityManager.createQuery("DELETE FROM Category c WHERE c.id = :id");
            query.setParameter("id", Integer.valueOf(id));
            query.executeUpdate();
            this.entityManager.getTransaction().commit();
        }catch(RollbackException re){
            LOGGER.error(re.getMessage(), re);
            throw new DatabaseException("Impossible to delete category with id " + id);
        }
    }// deleteById()

    @Override
    public Optional<CategoryDTO> findById(int id) {
        Category cat = this.entityManager.find(Category.class, id);
        if(cat == null){
            return Optional.empty();
        }
        return Optional.of(CategoryMapper.INSTANCE.toDto(cat));
    }// findById()

    @Override
    public void save(CategoryDTO category) throws DatabaseException, ArgumentMissingException {
        if(category == null){
            throw new ArgumentMissingException("Category is null");
        }
        super.save(CategoryMapper.INSTANCE.toEntity(category));
    }// save()

    @Override
    public void update(CategoryDTO category) throws DatabaseException, ArgumentMissingException {
        if(category == null){
            throw new ArgumentMissingException("Category is null");
        }
        Category c = this.entityManager.find(Category.class, category.getId());
        c.setName(category.getName());
        super.update(c);
    }// update()

}// ProductRepositoryImpl