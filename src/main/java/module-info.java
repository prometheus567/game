module com.example.game1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.game1 to javafx.fxml;
    exports com.example.game1;
    exports com.example.game1.Login;
    opens com.example.game1.Login to javafx.fxml;
}