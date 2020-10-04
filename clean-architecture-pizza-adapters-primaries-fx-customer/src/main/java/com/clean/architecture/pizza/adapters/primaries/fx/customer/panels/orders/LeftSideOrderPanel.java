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

import com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.orders.LeftSideOrderPanelStyle;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LeftSideOrderPanel extends VBox {

    private Label labelOrder;

    private Label labelConfirmation;

    private Label labelPayment;

    public LeftSideOrderPanel() {
        super();
        final String labelStyle = LeftSideOrderPanelStyle.getLabelStyle(false);
        this.labelOrder = new Label("Prise de commande");
        this.labelConfirmation = new Label("Confirmation commande");
        this.labelPayment = new Label("Paiement commande");
        this.getChildren().addAll(this.labelOrder, this.labelConfirmation, this.labelPayment);
        this.setStyle(LeftSideOrderPanelStyle.getStyle());
        this.labelOrder.setStyle(LeftSideOrderPanelStyle.getLabelStyle(true));
        this.labelConfirmation.setStyle(labelStyle);
        this.labelPayment.setStyle(labelStyle);
    }// LeftSideOrderPanel()

}// LeftSideOrderPanel
