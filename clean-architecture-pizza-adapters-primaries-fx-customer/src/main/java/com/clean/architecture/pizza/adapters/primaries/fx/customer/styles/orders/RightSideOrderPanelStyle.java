package com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.orders;

public class RightSideOrderPanelStyle {

    public static String getStyle(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-padding: 30px 50px;");
        sb.append("-fx-border-style: hidden hidden hidden solid;");
        sb.append("-fx-border-width: 1px;");
        sb.append("-fx-border-color: grey;");
        return sb.toString();
    }// getStyle()

    public static String getTitleStyle(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-text-fill: #bf0e0e;");
        sb.append("-fx-padding: 10px 25px;");
        sb.append("-fx-margin-right: 10px;");
        sb.append("-fx-font-weight: bold;");
        sb.append("-fx-font-size: 14px;");
        sb.append("-fx-text-align: center;");
        sb.append("-fx-cursor: hand;");
        return sb.toString();
    }// getTitleStyle()

}// RightSideOrderPanelStyle
