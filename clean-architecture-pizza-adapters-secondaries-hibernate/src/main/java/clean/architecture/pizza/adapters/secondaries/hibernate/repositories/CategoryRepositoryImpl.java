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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryRepositoryImpl extends AbstractRepository<Category>
        implements CategoryRepository {

    private final static Logger LOGGER = LogManager.getLogger(CategoryRepositoryImpl.class);

    @Override
    public boolean existsById(int id) {
        return this.findById(id).isPresent();
    }// existsById()

    @Override
    public void deleteById(int id) {
        Query query = this.entityManager.createQuery("DELETE FROM Category c WHERE c.id = :id");
        query.setParameter("id", Integer.valueOf(id));
        query.executeUpdate();
    }// deleteById()

    @Override
    public Optional<CategoryDTO> findById(int id) {
        Category cat = this.entityManager.find(Category.class, id);
        if(cat == null){
            return Optional.empty();
        }
        this.entityManager.refresh(cat);
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

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = this.entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
        return categories.stream().map(cat -> {
            this.entityManager.refresh(cat);
            return CategoryMapper.INSTANCE.toDto(cat);
        }).collect(Collectors.toList());
    }// findAll()

}// ProductRepositoryImpl
