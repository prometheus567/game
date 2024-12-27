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
    public String register(String username, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Mật khẩu xác nhận không khớp.";
        }
        String checkusernameQuery = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection conn = this.connect();
             PreparedStatement checkname = conn.prepareStatement(checkusernameQuery)) {

            checkname.setString(1, username);
            ResultSet rs = checkname.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) { // Kiểm tra nếu số lượng lớn hơn 0
                return "Tên đã tồn tại.";
            }
        } catch (SQLException e) {
            return "Lỗi khi kiểm tra tên: " + e.getMessage();
        }



        // Kiểm tra email đã tồn tại
        String checkEmailQuery = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (Connection conn = this.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkEmailQuery)) {

            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) { // Email đã tồn tại
                return "Email đã được sử dụng. Vui lòng chọn email khác.";
            }
        } catch (SQLException e) {
            return "Lỗi khi kiểm tra email: " + e.getMessage();
        }

        // Thêm người dùng mới nếu email chưa tồn tại
        String insertQuery = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            insertStmt.setString(1, username);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password); // Nên mã hóa mật khẩu trước khi lưu
            insertStmt.executeUpdate();
            return "Đăng ký thành công!";
        } catch (SQLException e) {
            return "Lỗi khi đăng ký: " + e.getMessage();
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
