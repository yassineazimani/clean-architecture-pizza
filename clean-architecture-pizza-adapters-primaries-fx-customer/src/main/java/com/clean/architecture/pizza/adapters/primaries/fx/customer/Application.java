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
package com.clean.architecture.pizza.adapters.primaries.fx.customer;

import com.clean.architecture.pizza.adapters.primaries.fx.customer.enums.PanelEnum;
import com.clean.architecture.pizza.adapters.primaries.fx.customer.scenes.SwitchScene;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    private Scene scene;

    private SwitchScene switchScene;

    private void initSwitchScene(){
        this.scene = new Scene(new VBox());
        this.switchScene = SwitchScene.getInstance(this.scene);
        this.switchScene.enablePanel(PanelEnum.START_PANEL);
    }// initSwitchScene()

    private void setWidthAndHeightFromScreen(Stage stage){
        if(stage != null){
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());
            stage.setWidth(primaryScreenBounds.getWidth());
            stage.setHeight(primaryScreenBounds.getHeight());
        }
    }// setWidthAndHeightFromScreen()

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Customer part");
        this.setWidthAndHeightFromScreen(stage);
        stage.setFullScreen(true);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        this.initSwitchScene();
        stage.setScene(scene);
        stage.show();
    }// start()

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }// main()

}// Application
