package com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.orders;

public class LeftSideOrderPanelStyle {

    public static String getStyle(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-background-color: #FFC90E;");
        return sb.toString();
    }// getStyle()

    public static String getLabelStyle(boolean isCurrent){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-text-fill: #ffffff;");
        sb.append("-fx-padding: 30px 10px;");
        sb.append("-fx-font-weight: bold;");
        sb.append("-fx-font-size: 14px;");
        sb.append("-fx-text-align: center;");
        if(isCurrent){
            sb.append("-fx-underline: true;");
        }
        return sb.toString();
    }// getLabelStyle()

}// LeftSideOrderPanelStyle
