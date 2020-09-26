package com.clean.architecture.pizza.core.admin.product;

import com.clean.architecture.pizza.core.exceptions.*;
import com.clean.architecture.pizza.core.model.ProductDTO;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.ProductRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PersistProduct {

    private ProductRepository productRepository;

    private AuthenticationUser authenticationUser;

    public PersistProduct(ProductRepository productRepository,
                          AuthenticationUser authenticationUser) {
        this.productRepository = productRepository;
        this.authenticationUser = authenticationUser;
    }// PersistProduct()

    public void save(ProductDTO product) throws ArgumentMissingException, ProductException, AuthenticationException, DatabaseException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        checkBusiness(product, false);
        try{
            this.productRepository.begin();
            this.productRepository.save(product);
            this.productRepository.commit();
        } catch (TransactionException e) {
            this.productRepository.rollback();
            throw new ProductException("Error technical : Impossible to create a product");
        }
    }// save()

    public void update(ProductDTO product) throws ArgumentMissingException, ProductException, AuthenticationException, DatabaseException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        checkBusiness(product, true);
        try{
            this.productRepository.begin();
            this.productRepository.update(product);
            this.productRepository.commit();
        } catch (TransactionException e) {
            this.productRepository.rollback();
            throw new ProductException("Error technical : Impossible to update a product");
        }
    }// update()

    private void checkBusiness(ProductDTO product, boolean isUpdate) throws ProductException, ArgumentMissingException {
        if(product == null){
            throw new ArgumentMissingException("Product is null");
        } else if (isUpdate && product.getId() == null) {
            throw new ProductException("Product ID is mandatory for update");
        } else {
            List<String> errors = new ArrayList<>();
            if(product.getPrice() == null){
                errors.add("The price is mandatory");
            } else if(product.getPrice() < 0.){
                errors.add("The price must be positive");
            }
            if(product.getCategory() == null){
                errors.add("The category is mandatory");
            } else if (product.getCategory().getId() == null){
                errors.add("The category ID is mandatory");
            }
            if(product.getQuantityAvailable() < 0){
                errors.add("The quantity available is mandatory");
            }
            if(StringUtils.isEmpty(product.getDescription())){
                errors.add("The description is mandatory");
            }
            if(StringUtils.isEmpty(product.getName())){
                errors.add("The name is mandatory");
            }
            if(CollectionUtils.isNotEmpty(errors)){
                ProductException pe = new ProductException("Mandatory fields are missing");
                pe.setFieldsErrors(errors);
                throw pe;
            }
        }
    }// checkBusiness()

}// PersistProduct
