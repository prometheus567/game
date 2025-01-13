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
import javax.swing.*;
import java.awt.event.ActionEvent;
public class LoginController {
    @FXML
    private Button btnlogin;
    @FXML
    private TextField txfemail;
    @FXML
    private Button btnregistor;
    @FXML
    private PasswordField txfyourpassword;
    @FXML
    private TextField txfyourusername;
    /*  @FXML
      private void handleLogin(ActionEvent event) {
          String username = txfyourusername.getText();
          String password = txfyourpassword.getText();
          if ((authController.login(username, password))) { // kiểm tra thông tin trong cơ sở dữ liệu.
              showAlert("Success", "Registration successful!", Alert.AlertType.INFORMATION);
          } else {
              showAlert("Error", "Registration failed. Username might already exist.", Alert.AlertType.ERROR);
          }
      }*/
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait(); // Sử dụng Alert của JavaFX để tạo các hộp thoại thông báo cho người dùng.
    }
    @FXML
    private void handleRegister() {
        // Logic xử lý đăng ký
        try {
            // Load giao diện đăng ký từ file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent registerRoot = loader.load();
            // Tạo một Stage mới để hiển thị giao diện đăng ký
            Stage registerStage = new Stage();
            registerStage.setTitle("Đăng ký");
            registerStage.setScene(new Scene(registerRoot));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage currentStage = (Stage) btnregistor.getScene().getWindow();
        currentStage.close();
    }
    public void handleLogin(javafx.event.ActionEvent actionEvent) {
        String email = txfemail.getText();
        String password = txfyourpassword.getText();
        DatabaseConnection conn = new DatabaseConnection();
        if (conn.login(email, password)) { // Kiểm tra thông tin đăng nhập
            showAlert("Thành công", "Đăng nhập thành công!", Alert.AlertType.INFORMATION);
            // Hiển thị màn hình game (giả sử có phương thức `showGameScreen()`)
            // showGameScreen();
        } else {
            showAlert("Lỗi", "Đăng nhập không thành công. Vui lòng thử lại.", Alert.AlertType.ERROR);
            // Xóa thông tin đăng nhập để nhập lại
            txfemail.clear();
            txfyourpassword.clear();
        }
    }
}