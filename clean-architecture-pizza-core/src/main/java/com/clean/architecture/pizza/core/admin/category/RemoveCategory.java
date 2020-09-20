package com.clean.architecture.pizza.core.admin.category;

import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
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

    public boolean execute(int id) throws AuthenticationException, DatabaseException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        if(this.categoryRepository.existsById(id)){
            this.categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }// execute()

}// RemoveCategory
