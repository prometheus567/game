package com.example.game1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;





public class DatabaseConnection {
    public Connection connect() {
        String url = "jdbc:mysql://localhost:3306/game";
        String user = "your_username";
        String password = "your_password";

        try {
            return DriverManager.getConnection(url, user, password); // Kết nối đến cơ sở dữ liệu MySQL. Bạn cần thay thế:
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}


