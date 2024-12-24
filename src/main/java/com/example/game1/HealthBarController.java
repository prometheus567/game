package com.example.game1;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class HealthBarController {
    @FXML
    private ImageView greenbar;

    @FXML
    private ImageView shieldbar;

    private void initialize() {
        System.out.println("Health bar controller initialized!");

        // Kiểm tra vị trí các thành phần
        greenbar.setLayoutX(greenbar.getLayoutX());
        greenbar.setLayoutY(greenbar.getLayoutY());
        shieldbar.setLayoutX(shieldbar.getLayoutX());
        shieldbar.setLayoutY(shieldbar.getLayoutY());

        // Đặt kích thước nếu cần
        greenbar.setFitWidth(greenbar.getFitWidth());
        shieldbar.setFitWidth(shieldbar.getFitWidth());
    }
}


