package com.clean.architecture.pizza.core.admin.product;

import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.ProductRepository;

public class RemoveProduct {

    private ProductRepository productRepository;

    private AuthenticationUser authenticationUser;

    public RemoveProduct(ProductRepository productRepository,
                         AuthenticationUser authenticationUser) {
        this.productRepository = productRepository;
        this.authenticationUser = authenticationUser;
    }// RemoveProduct()

    public boolean execute(int id) throws AuthenticationException, DatabaseException {
        if(!this.authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        if(this.productRepository.existsById(id)){
            this.productRepository.deleteById(id);
            return true;
        }
        return false;
    }// execute()

}// RemoveProduct
