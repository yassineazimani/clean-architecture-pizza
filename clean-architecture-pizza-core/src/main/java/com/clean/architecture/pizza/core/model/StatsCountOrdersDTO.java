package com.clean.architecture.pizza.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsCountOrdersDTO {

    private long total;

    private int month;

    private int year;

}// StatsCountOrdersDTO
