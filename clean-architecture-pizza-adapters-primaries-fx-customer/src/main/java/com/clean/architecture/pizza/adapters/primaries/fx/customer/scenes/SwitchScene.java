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
package com.clean.architecture.pizza.adapters.primaries.fx.customer.scenes;

import com.clean.architecture.pizza.adapters.primaries.fx.customer.enums.PanelEnum;
import com.clean.architecture.pizza.adapters.primaries.fx.customer.panels.StartPanel;
import com.clean.architecture.pizza.adapters.primaries.fx.customer.panels.orders.ChoiceOrderPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class SwitchScene {

    private Map<PanelEnum, Pane> panelsAvailable;

    private Scene mainScene;

    private static SwitchScene instance;

    private SwitchScene(){}// SwitchScene()

    private SwitchScene(Scene mainScene) {
        this.mainScene = mainScene;
        this.panelsAvailable = new HashMap<>();
        this.panelsAvailable.put(PanelEnum.START_PANEL, new StartPanel());
        this.panelsAvailable.put(PanelEnum.CHOICE_ORDER_PANEL, new ChoiceOrderPanel());
    }// SwitchScene()

    public void enablePanel(PanelEnum pe){
        this.mainScene.setRoot(this.panelsAvailable.get(pe));
    }// enablePanel()

    public static SwitchScene getInstance(Scene mainScene){
        if(instance == null){
            instance = new SwitchScene(mainScene);
        }
        return instance;
    }// getInstance()

}// SwitchScene
