package com.example.game1;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/game";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    // Phương thức kết nối
    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        return null; // Trả về null nếu không thể kết nối
    }

    // Phương thức kiểm tra kết nối
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra kết nối: " + e.getMessage());
        }
        return false; // Trả về false nếu không kiểm tra được
    }

    // Phương thức đăng nhập
    public boolean login(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy tài khoản
        } catch (SQLException e) {
            System.out.println("Lỗi khi đăng nhập: " + e.getMessage());
            return false;
        }
    }

    // Phương thức đăng ký
    public boolean register(String username, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            System.out.println("Mật khẩu xác nhận không khớp.");
            return false;
        }

        String query = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); // Thêm mã hóa mật khẩu ở đây nếu cần
            stmt.executeUpdate();
            System.out.println("Đăng ký thành công!");
            return true;
        } catch (SQLException e) {
            System.out.println("Lỗi khi đăng ký: " + e.getMessage());
            return false;
        }
    }

    // Phương thức đóng kết nối
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }
}
