package com.clean.architecture.pizza.adapters.primaries.core.admin;

import com.clean.architecture.pizza.core.admin.category.FetchCategory;
import com.clean.architecture.pizza.core.admin.category.PersistCategory;
import com.clean.architecture.pizza.core.admin.category.RemoveCategory;
import com.clean.architecture.pizza.core.exceptions.ArgumentMissingException;
import com.clean.architecture.pizza.core.exceptions.AuthenticationException;
import com.clean.architecture.pizza.core.exceptions.CategoryException;
import com.clean.architecture.pizza.core.exceptions.DatabaseException;
import com.clean.architecture.pizza.core.model.CategoryDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Scanner;

public class AdminCategoryTask {

    private PersistCategory persistCategory;

    private RemoveCategory removeCategory;

    private FetchCategory fetchCategory;

    private Scanner scan;

    public AdminCategoryTask(PersistCategory persistCategory, RemoveCategory removeCategory, FetchCategory fetchCategory, Scanner scan) {
        this.persistCategory = persistCategory;
        this.removeCategory = removeCategory;
        this.fetchCategory = fetchCategory;
        this.scan = scan;
    }// AdminCategoryTask()

    public boolean run(int choice){
        boolean backToMainMenu = false;
        switch (choice){
            case 1:
                createCategory();
                break;
            case 2:
                updateCategory();
                break;
            case 3:
                deleteCategory();
                break;
            case 4:
                fetchAllCategories();
                break;
            case 5:
                backToMainMenu = true;
                break;
        }
        return backToMainMenu;
    }// run()

    private void createCategory() {
        boolean hasErrors = true;
        do {
            System.out.println("Category name : ");
            String name = scan.next();
            CategoryDTO cat = new CategoryDTO(null, name);
            try {
                persistCategory.save(cat);
                hasErrors = false;
            } catch (CategoryException e) {
                e.getFieldsErrors().forEach(System.out::println);
            } catch (AuthenticationException e) {
                System.err.println("You don't have the rights to create a category");
            } catch (ArgumentMissingException | DatabaseException e) {
                System.err.println("Technical error : Impossible to create a category");
            }
        }while(hasErrors);
    }// createCategory()

    private void updateCategory() {
        System.out.println("Category ID : ");
        Integer categoryId = null;
        try{
            categoryId = scan.nextInt();
        }catch(Exception e){
            scan.next();
        }
        fetchCategory.findById(categoryId)
                .ifPresent(category -> {
                    boolean hasErrors = true;
                    do {
                        System.out.println("Category name (current = " + category.getName() + ") : ");
                        String name = scan.next();
                        CategoryDTO cat = new CategoryDTO(category.getId(), name);
                        try {
                            persistCategory.update(cat);
                            hasErrors = false;
                        } catch (CategoryException e) {
                            e.getFieldsErrors().forEach(System.out::println);
                        } catch (AuthenticationException e) {
                            System.err.println("You don't have the rights to update a category");
                        } catch (ArgumentMissingException | DatabaseException e) {
                            System.err.println("Technical error : Impossible to update a category");
                        }
                    }while(hasErrors);
                });
    }// updateCategory()

    private void deleteCategory() {
        System.out.println("Category ID : ");
        int categoryId = -1;
        try{
            categoryId = scan.nextInt();
        }catch(Exception e){
            scan.next();
        }
        try {
            this.removeCategory.execute(categoryId);
        } catch (AuthenticationException e) {
            System.err.println("You don't have the rights to update a category");
        } catch (DatabaseException e) {
            System.err.println("Technical error : Impossible to remove a category");
        } catch (CategoryException e) {
            e.getFieldsErrors().forEach(System.out::println);
        }
    }// deleteCategory()

    private void fetchAllCategories(){
        List<CategoryDTO> categories = this.fetchCategory.findAll();
        if(CollectionUtils.isNotEmpty(categories)){
            categories.forEach(category -> System.out.println(" * Category (id = " + category.getId() + ") " + category.getName()));
        }
    }// fetchAllCategories()

}// AdminCategoryTask
