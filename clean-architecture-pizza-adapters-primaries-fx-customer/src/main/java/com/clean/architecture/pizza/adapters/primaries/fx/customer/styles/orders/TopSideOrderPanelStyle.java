package com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.orders;

public class TopSideOrderPanelStyle {

    public static String getStyle(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-background-color: #bf0e0e;");
        return sb.toString();
    }// getStyle()

    public static String getLabelStyle(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-text-fill: #ffffff;");
        sb.append("-fx-padding: 30px 0px;");
        sb.append("-fx-font-weight: bold;");
        sb.append("-fx-font-size: 22px;");
        sb.append("-fx-text-align: center;");
        return sb.toString();
    }// getLabelStyle()

}// TopSideOrderPanelStyle
