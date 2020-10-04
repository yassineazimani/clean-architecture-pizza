package com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.orders;

public class BottomSideOrderPanelStyle {

    public static String getStyle(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-background-color: #000000;");
        sb.append("-fx-padding: 30px 0;");
        return sb.toString();
    }// getStyle()

    public static String getCancelButtonStyle(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-text-fill: #000000;");
        sb.append("-fx-padding: 10px 25px;");
        sb.append("-fx-margin-right: 10px;");
        sb.append("-fx-font-weight: bold;");
        sb.append("-fx-font-size: 14px;");
        sb.append("-fx-text-align: center;");
        sb.append("-fx-background-color: #ffffff;");
        sb.append("-fx-cursor: hand;");
        return sb.toString();
    }// getCancelButtonStyle()

    public static String getConfirmButtonStyle(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-text-fill: #ffffff;");
        sb.append("-fx-padding: 10px 25px;");
        sb.append("-fx-font-weight: bold;");
        sb.append("-fx-font-size: 14px;");
        sb.append("-fx-text-align: center;");
        sb.append("-fx-background-color: #bf0e0e;");
        sb.append("-fx-cursor: hand;");
        return sb.toString();
    }// getConfirmButtonStyle()

}// BottomSideOrderPanelStyle
