package com.example.game1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginController {
    @FXML
    private Button btnlogin;

    @FXML
    private Button btnregistor;

    @FXML
    private PasswordField txfyourpassword;
    private final AuthController authController = new AuthController();


    @FXML
    private TextField txfyourusername;

    @FXML
    private void handleLogin(ActionEvent event){
        String username = txfyourusername.getText();
        String password = txfyourpassword.getText();



        if ((authController.login(username,password))){ // kiểm tra thông tin trong cơ sở dữ liệu.
            showAlert("Success", "Registration successful!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Registration failed. Username might already exist.", Alert.AlertType.ERROR);


        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait(); // Sử dụng Alert của JavaFX để tạo các hộp thoại thông báo cho người dùng.

    }
}
