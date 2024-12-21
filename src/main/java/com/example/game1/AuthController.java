package com.example.game1;

import com.example.game1.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthController {
    private final DatabaseConnection dbConnection = new DatabaseConnection();

    public boolean register(String username, String password) {
        String query = "INSERT INTO player (username, password) VALUES (?, ?)"; //hêm người dùng mới vào bảng
        try (Connection conn = dbConnection.connect(); // Sử dụng lớp DatabaseConnection để kết nối MySQL.
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);//Gán giá trị username vào dấu ? đầu tiên.
            stmt.setString(2, password); // Bạn có thể hash mật khẩu ở đây
            stmt.executeUpdate();//Nếu lệnh thực thi thành công (stmt.executeUpdate()), trả về true.
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM player WHERE username = ? AND password = ?";//Kiểm tra xem có tài khoản nào khớp với thông tin đăng nhập không.
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy tài khoản
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
