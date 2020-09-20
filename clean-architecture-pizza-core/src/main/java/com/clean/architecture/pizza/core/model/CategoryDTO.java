package com.clean.architecture.pizza.core.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class CategoryDTO {

    private Integer id;

    private String name;

}// CategoryDTO
