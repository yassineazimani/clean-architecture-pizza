package com.clean.architecture.pizza.adapters.primaries.fx.customer;

import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Customer part");
        stage.setFullScreen(true);
        stage.show();
    }// start()

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }// main()

}// Application
