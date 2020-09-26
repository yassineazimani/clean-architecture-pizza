package com.clean.architecture.pizza.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsSumOrderTotalByProductsDTO {

    private double total;

    private String name;

}// StatsCountOrdersDTO
