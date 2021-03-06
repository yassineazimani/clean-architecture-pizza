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
package com.clean.architecture.pizza.adapters.primaries.fx.customer.panels;

import com.clean.architecture.pizza.adapters.primaries.fx.customer.enums.PanelEnum;
import com.clean.architecture.pizza.adapters.primaries.fx.customer.scenes.SwitchScene;
import com.clean.architecture.pizza.adapters.primaries.fx.customer.styles.StartPanelStyle;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class StartPanel extends StackPane {

    private Button startButton;

    public StartPanel() {
        super();
        this.setStyle(StartPanelStyle.getStyleBackground());
        this.startButton = new Button("Pour commencer, appuyez ici");
        this.startButton.setStyle(StartPanelStyle.getStyleStartButton());
        this.startButton.setOnMouseClicked((e) -> {
            SwitchScene.getInstance(null)
                        .enablePanel(PanelEnum.CHOICE_ORDER_PANEL);
        });
        super.getChildren().add(this.startButton);
    }// StartPanel()

}// StartPanel
