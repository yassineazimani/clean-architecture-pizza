package com.clean.architecture.pizza.core.admin.category;

import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.CategoryException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.exceptions.TransactionException;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.CategoryRepository;

public class RemoveCategory {

    private CategoryRepository categoryRepository;

    private AuthenticationUser authenticationUser;

    public RemoveCategory(CategoryRepository categoryRepository,
                          AuthenticationUser authenticationUser) {
        this.categoryRepository = categoryRepository;
        this.authenticationUser = authenticationUser;
    }// RemoveCategory()

    public void execute(int id) throws AuthenticationException, DatabaseException, CategoryException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        try{
            this.categoryRepository.begin();
            if(this.categoryRepository.existsById(id)){
                this.categoryRepository.deleteById(id);
            }
            this.categoryRepository.commit();
        }catch (TransactionException te){
            this.categoryRepository.rollback();
            throw new CategoryException("Error technical : Impossible to create a category");
        }
    }// execute()

}// RemoveCategory
