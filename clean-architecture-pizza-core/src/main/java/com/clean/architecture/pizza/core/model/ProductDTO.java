package com.clean.architecture.pizza.core.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ProductDTO {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private int quantityAvailable;

    private CategoryDTO category;

    private int quantityOrdered;

}// ProductDTO
