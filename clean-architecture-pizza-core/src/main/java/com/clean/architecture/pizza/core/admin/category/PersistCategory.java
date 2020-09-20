package com.clean.architecture.pizza.core.admin.category;

import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.CategoryException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.ports.AuthenticationUser;
import com.clean.architecture.pizza.core.ports.CategoryRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PersistCategory {

    private AuthenticationUser authenticationUser;

    private CategoryRepository categoryRepository;

    public PersistCategory(AuthenticationUser authenticationUser,
                           CategoryRepository categoryRepository) {
        this.authenticationUser = authenticationUser;
        this.categoryRepository = categoryRepository;
    }// PersistCategory()

    public void save(CategoryDTO category) throws ArgumentMissingException, CategoryException, AuthenticationException, DatabaseException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        checkBusiness(category, false);
        this.categoryRepository.save(category);
    }// save()

    public void update(CategoryDTO category) throws ArgumentMissingException, CategoryException, AuthenticationException, DatabaseException {
        if(!authenticationUser.isAuthenticated()){
            throw new AuthenticationException("You need to login");
        }
        checkBusiness(category, true);
        this.categoryRepository.update(category);
    }// update()

    private void checkBusiness(CategoryDTO category, boolean isUpdate) throws CategoryException, ArgumentMissingException {
        if(category == null){
            throw new ArgumentMissingException("Category is null");
        } else if (isUpdate && category.getId() == null) {
            throw new CategoryException("Category ID is mandatory for update");
        } else {
            List<String> errors = new ArrayList<>();
            if(StringUtils.isEmpty(category.getName())){
                errors.add("The name is mandatory");
            }
            if(CollectionUtils.isNotEmpty(errors)){
                CategoryException ce = new CategoryException("Mandatory fields are missing");
                ce.setFieldsErrors(errors);
                throw ce;
            }
        }
    }// checkBusiness()

}// PersistCategory
