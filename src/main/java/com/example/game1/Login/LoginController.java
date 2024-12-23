package com.example.game1.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField txfyourusername;

    @FXML
    private PasswordField txfyourpassword;

    /**
     * Xử lý sự kiện khi nhấn nút "Start".
     */
    @FXML
    public void handleLogin(ActionEvent event) {
        String username = txfyourusername.getText();
        String password = txfyourpassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Please fill in both username and password.");
            return;
        }

        if (validateLogin(username, password)) {
            showAlert("Success", "Login successful!");
            // TODO: Chuyển sang màn hình chính của game
        } else {
            showAlert("Error", "Invalid username or password.");
        }
    }

    /**
     * Kiểm tra thông tin đăng nhập với database.
     *
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return true nếu thông tin đúng, ngược lại false
     */
    private boolean validateLogin(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/your_database_name";
        String dbUser = "root"; // Username của MySQL
        String dbPassword = ""; // Password của MySQL (để trống nếu không có)

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy tài khoản
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Unable to connect to the database. Please check your connection.");
        }
        return false;
    }

    /**
     * Hiển thị thông báo cho người dùng.
     *
     * @param title Tiêu đề thông báo
     * @param message Nội dung thông báo
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}