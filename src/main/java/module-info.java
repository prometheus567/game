module com.example.game1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;  // Bao gồm thư viện Java SQL nếu cần
    requires java.desktop; // Thư viện Java Desktop

    opens com.example.game1 to javafx.fxml;
    exports com.example.game1;
<<<<<<< HEAD
    exports com.example.game1.Login;
    opens com.example.game1.Login to javafx.fxml;
}
=======
}
>>>>>>> 5ccab4a77d03f9a527e53bb24dab9aa3b4f15b8c
