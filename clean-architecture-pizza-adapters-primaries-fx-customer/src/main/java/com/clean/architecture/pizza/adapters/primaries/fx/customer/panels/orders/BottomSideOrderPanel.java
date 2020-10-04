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

import com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.orders.BottomSideOrderPanelStyle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class BottomSideOrderPanel extends HBox {

    private Button cancelOrderBtn;

    private Button confirmOrderBtn;

    public BottomSideOrderPanel() {
        super();
        this.cancelOrderBtn = new Button("Annuler la commande");
        this.confirmOrderBtn = new Button("Valider la commande");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(50.);
        this.getChildren().addAll(this.cancelOrderBtn, this.confirmOrderBtn);
        this.setStyle(BottomSideOrderPanelStyle.getStyle());
        this.cancelOrderBtn.setStyle(BottomSideOrderPanelStyle.getCancelButtonStyle());
        this.confirmOrderBtn.setStyle(BottomSideOrderPanelStyle.getConfirmButtonStyle());
    }// BottomSideOrderPanel()

}// BottomSideOrderPanel
