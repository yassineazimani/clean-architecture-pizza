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

import com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.orders.CategoriesOrderPanelStyle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ChoiceOrderPanel extends BorderPane {

    private TopSideOrderPanel topSideOrderPanel;

    private LeftSideOrderPanel leftSidePanel;

    private RightSideOrderPanel rightSidePanel;

    private BottomSideOrderPanel bottomSidePanel;

    private CategoriesOrderPanelStyle categoriesOrderPanelStyle;

    public ChoiceOrderPanel() {
        super();
        this.topSideOrderPanel = new TopSideOrderPanel();
        this.leftSidePanel = new LeftSideOrderPanel();
        this.rightSidePanel = new RightSideOrderPanel();
        this.bottomSidePanel = new BottomSideOrderPanel();
        this.categoriesOrderPanelStyle = new CategoriesOrderPanelStyle();
        this.setTop(this.topSideOrderPanel);
        this.setLeft(this.leftSidePanel);
        this.setCenter(this.categoriesOrderPanelStyle);
        this.setRight(this.rightSidePanel);
        this.setBottom(new VBox(this.bottomSidePanel));
    }// ChoiceOrderPanel()

}// ChoiceOrderPanel
