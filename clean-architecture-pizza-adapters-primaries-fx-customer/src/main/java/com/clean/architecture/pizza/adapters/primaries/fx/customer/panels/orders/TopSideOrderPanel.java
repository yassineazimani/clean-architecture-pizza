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
package com.clean.architecture.pizza.adapters.primaries.fx.customer.panels.orders;

import com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.orders.TopSideOrderPanelStyle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TopSideOrderPanel extends HBox {

    private Label title;

    public TopSideOrderPanel() {
        super();
        this.title = new Label("Clean Architecture Pizza : Prise de commande");
        this.getChildren().add(this.title);
        this.setStyle(TopSideOrderPanelStyle.getStyle());
        this.title.setStyle(TopSideOrderPanelStyle.getLabelStyle());
        this.setAlignment(Pos.CENTER);
    }// LeftSideOrderPanel()

}// TopSideOrderPanel
