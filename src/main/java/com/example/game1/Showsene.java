package com.example.game1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Showsene extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tạo cửa sổ Login
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene loginScene = new Scene(loginLoader.load());
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(loginScene);
        loginStage.show();

        // Tạo cửa sổ Register
        FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        Scene registerScene = new Scene(registerLoader.load());
        Stage registerStage = new Stage();
        registerStage.setTitle("Register");
        registerStage.setScene(registerScene);
        registerStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
