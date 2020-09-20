package com.clean.architecture.pizza.core.model;

import com.clean.architecture.pizza.core.enums.OrderStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private Integer id;

    private Date orderDate;

    private OrderStateEnum orderState;

    private double total;

    private List<ProductDTO> products;

    private String transactionCBId;

    public OrderDTO(){
        this.orderState = OrderStateEnum.PENDING;
        this.orderDate = new Date();
        this.total = 0.;
        this.products = new ArrayList<>();
    }// OrderDTO()

}// OrderDTO
