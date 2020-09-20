package com.clean.architecture.pizza.core.enums;

public enum OrderStateEnum {
    PENDING,
    SUCCESS,
    CANCELLED;

    public static OrderStateEnum valueOf(int id){
        switch(id){
            case 1:
                return OrderStateEnum.PENDING;
            case 2:
                return OrderStateEnum.SUCCESS;
            case 3:
                return OrderStateEnum.CANCELLED;
            default:
                return OrderStateEnum.PENDING;
        }
    }// valueOf()
}// OrderStateEnum
