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
import clean.architecture.pizza.adapters.secondaries.hibernate.entities.Product;
import clean.architecture.pizza.adapters.secondaries.hibernate.mappers.ProductMapper;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductRepositoryImpl extends AbstractRepository<Product>
        implements ProductRepository {

    private final static Logger LOGGER = LogManager.getLogger(ProductRepositoryImpl.class);

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products =
                this.entityManager
                        .createQuery("SELECT p FROM Product p JOIN p.category c", Product.class)
                        .getResultList();
        return products.stream()
                .map(product -> {
                    this.entityManager.refresh(product);
                    return ProductMapper.INSTANCE.toDto(product);
                }).collect(Collectors.toList());
    }// findAllProducts()

    @Override
    public Optional<ProductDTO> findById(int id) {
        Product product = this.entityManager.find(Product.class, id);
        if(product == null){
            return Optional.empty();
        }
        try {
            this.entityManager.refresh(product);
            return Optional.of(ProductMapper.INSTANCE.toDto(product));
        }catch(EntityNotFoundException e){
            return Optional.empty();
        }
    }// findById()

    @Override
    public boolean existsByName(String name) {
        Query query = this.entityManager.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class);
        query.setParameter("name", name);
        try{
            Product cat = (Product) query.getSingleResult();
            return cat != null;
        }
        catch(NoResultException e){
            return false;
        }
    }// existsByName()

    @Override
    public boolean existsById(int id) {
        return this.findById(id).isPresent();
    }// existsById()

    @Override
    public void deleteById(int id) throws DatabaseException {
        try {
            Query query = this.entityManager.createQuery("DELETE FROM Product p WHERE p.id = :id");
            query.setParameter("id", Integer.valueOf(id));
            query.executeUpdate();
        }catch(RollbackException re){
            LOGGER.error(re.getMessage(), re);
            throw new DatabaseException("Impossible to delete product with id " + id);
        }
    }// deleteById()

    @Override
    public void save(ProductDTO product) throws DatabaseException, ArgumentMissingException {
        if(product == null){
            throw new ArgumentMissingException("Product is null");
        }
        super.save(ProductMapper.INSTANCE.toEntity(product));
    }// save()

    @Override
    public void update(ProductDTO product) throws DatabaseException, ArgumentMissingException {
        if(product == null){
            throw new ArgumentMissingException("Product is null");
        }
        Product p = this.entityManager.find(Product.class, product.getId());
        p.setDescription(product.getDescription());
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setQuantityAvailable(product.getQuantityAvailable());
        Category c = this.entityManager.find(Category.class, product.getCategory().getId());
        c.setName(product.getCategory().getName());
        p.setCategory(c);
        super.update(p);
    }// update()

}// ProductRepositoryImpl
