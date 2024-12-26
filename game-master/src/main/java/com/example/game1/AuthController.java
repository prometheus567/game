package com.example.game1;

import com.example.game1.DatabaseConnection;

import java.sql.*;

public class AuthController {
    private final DatabaseConnection dbConnection = new DatabaseConnection();

    public boolean register(String username, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            System.out.println("Mật khẩu xác nhận không khớp.");
            return false; // Nếu mật khẩu không trùng, thoát và không thực hiện thêm
        }

        String query = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)"; // Câu lệnh SQL đúng
        try (Connection conn = dbConnection.connect(); // Kết nối cơ sở dữ liệu
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username); // Gán giá trị
            stmt.setString(2, email);
            stmt.setString(3, password); // Mật khẩu có thể mã hóa trước khi lưu vào cơ sở dữ liệu
            stmt.executeUpdate(); // Thực thi câu lệnh chèn
            return true; // Thành công
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console nếu có vấn đề
            return false; // Thất bại
        }
    }


    public boolean login(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ? AND password = ? ";//Kiểm tra xem có tài khoản nào khớp với thông tin đăng nhập không.
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString( 2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy tài khoản
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
