package com.example.game1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private Button btnlogin;

    @FXML
    private Button btnregister;

    @FXML
    private PasswordField txfcomfirmpassword;

    @FXML
    private TextField txfemail;

    @FXML
    private PasswordField txfpassword;

    @FXML
    private TextField txfusername;

    @FXML
    private void initialize(){
        // Xử lý nút Register
        btnregister.setOnAction(event -> handleRegister());

        // Xử lý nút Login
        btnlogin.setOnAction(event -> {
            try {
                handleLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void handleRegister() {
        String username = txfusername.getText();
        String email = txfemail.getText();
        String password = txfpassword.getText();
        String confirmPassword = txfcomfirmpassword.getText();

        // Kiểm tra dữ liệu nhập vào
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Lỗi", "Tất cả các trường phải được điền vào!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert("Lỗi", "Mật khẩu xác nhận không khớp!");
            return;
        }

        // Tạo đối tượng DatabaseConnection và gọi hàm register
        DatabaseConnection conn = new DatabaseConnection();
        String resultMessage = conn.register(username, email, password, confirmPassword);

        // Hiển thị thông báo dựa trên kết quả
        if (resultMessage.equals("Đăng ký thành công!")) {
            showAlert("Thành công", resultMessage);
        } else {
            showAlert("Lỗi", resultMessage);
        }
    }


    @FXML
    private void handleLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent loginRoot = loader.load();

        // Tạo một Stage mới để hiển thị giao diện đăng ký
        Stage registerStage = new Stage();
        registerStage.setTitle("đăng nhập");
        registerStage.setScene(new Scene(loginRoot));
        registerStage.show();
        Stage currentStage = (Stage) btnlogin.getScene().getWindow();
        currentStage.close();

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();




    }



}
