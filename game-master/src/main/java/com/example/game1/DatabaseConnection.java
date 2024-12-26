package com.example.game1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/game";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public Connection connect() {
        try {
            // Tải Driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Kết nối đến database
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
