package com.clean.architecture.pizza.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private int quantityAvailable;

    private CategoryDTO category;

}// ProductDTO
