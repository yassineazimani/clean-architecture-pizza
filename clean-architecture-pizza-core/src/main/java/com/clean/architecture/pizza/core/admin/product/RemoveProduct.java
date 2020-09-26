package com.clean.architecture.pizza.core.admin.product;

import com.clean.architecture.pizza.core.exceptions.*;
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

    public boolean execute(int id) throws AuthenticationException, DatabaseException, ProductException {
        if(!this.authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        try {
            if (this.productRepository.existsById(id)) {
                this.productRepository.begin();
                this.productRepository.deleteById(id);
                this.productRepository.commit();
                return true;
            }
        }catch(TransactionException te){
            this.productRepository.rollback();
            throw new ProductException("Error technical : Impossible to remove a product");
        }
        return false;
    }// execute()

}// RemoveProduct
