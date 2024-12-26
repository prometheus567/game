package com.example.game1;

import java.sql.Connection;

public class testdtb {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.connect();

        // Kiểm tra kết nối
        if (dbConnection.isConnected()) {
            System.out.println("Kết nối thành công. Bây giờ bạn có thể làm việc với cơ sở dữ liệu.");
        } else {
            System.out.println("Kết nối thất bại. Vui lòng kiểm tra lại thông tin cấu hình.");
        }

        // Đóng kết nối khi xong
        //dbConnection.disconnect();
    }
}

