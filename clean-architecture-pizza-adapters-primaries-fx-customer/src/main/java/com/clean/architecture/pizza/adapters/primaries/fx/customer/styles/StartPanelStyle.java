/*
 * Copyright 2020 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clean.architecture.pizza.adapters.primaries.fx.customer.styles;

public class StartPanelStyle {

    public static String getStyleBackground(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-background-image: url('https://images.unsplash.com/photo-1571407970349-bc81e7e96d47?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=925&q=80');");
        sb.append("-fx-background-size: cover;");
        return sb.toString();
    }// getStyleBackground()

    public static String getStyleStartButton(){
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-background-color: red;");
        sb.append("-fx-text-fill: #fff;");
        sb.append("-fx-font-weight: bold;");
        sb.append("-fx-padding: 15px 30px;");
        sb.append("-fx-font-size: 15px;");
        sb.append("-fx-cursor: hand;");
        return sb.toString();
    }// getStyleStartButton()

}// StartPanelStyle
