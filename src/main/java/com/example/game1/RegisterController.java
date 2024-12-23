package com.example.game1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private Button btnlogin;

    @FXML
    private Button btnregister;

    @FXML
    private PasswordField txfcomfirmpassword;

    @FXML
    private PasswordField txfpassword;

    @FXML
    private TextField txfusername;

    @FXML
    private void initialize(){
        // Xử lý nút Register
        btnregister.setOnAction(event -> handleRegister());

        // Xử lý nút Login
        btnlogin.setOnAction(event -> handleLogin());
    }

    private void handleRegister() {
        String username = txfusername.getText();
        String password = txfpassword.getText();
        String confirmPassword = txfcomfirmpassword.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields must be filled out!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!");
            return;
        }

        // Xử lý logic đăng ký (ví dụ: lưu vào cơ sở dữ liệu)
        showAlert("Success", "User registered successfully!");
    }
    private void handleLogin() {
        String username = txfusername.getText();
        String password = txfpassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password cannot be empty!");
            return;
        }

        // Xử lý logic đăng nhập (ví dụ: kiểm tra với cơ sở dữ liệu)
        showAlert("Success", "Login successful!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
