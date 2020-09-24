package com.clean.architecture.pizza.core.admin.category;

import com.clean.architecture.pizza.core.model.CategoryDTO;
import com.clean.architecture.pizza.core.ports.CategoryRepository;

import java.util.List;
import java.util.Optional;

public class FetchCategory {

    private CategoryRepository categoryRepository;

    public FetchCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }// FetchCategory()

    public Optional<CategoryDTO> findById(int id){
        return this.categoryRepository.findById(id);
    }// findById()

    public List<CategoryDTO> findAll(){
        return this.categoryRepository.findAll();
    }// findAll()

}// FetchCategory
