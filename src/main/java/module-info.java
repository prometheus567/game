module com.example.game1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;  // Bao gồm thư viện Java SQL nếu cần
    requires java.desktop;
    requires gdx; // Thư viện Java Desktop

    opens com.example.game1 to javafx.fxml;
    exports com.example.game1;
    exports com.example.game1.Login;
    opens com.example.game1.Login to javafx.fxml;
}