package com.example.game1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HealthBar {
         private ImageView sprite; // Hiển thị nhân vật
         private ImageView blackBar;
         private ImageView greenBar;

    public HealthBar(String backgroundPath, String healthPath, double x, double y) {
        // Tạo ImageView cho thanh nền
        blackBar = new ImageView(new Image(getClass().getResource(backgroundPath).toExternalForm()));
        blackBar.setLayoutX(x); // Định vị thanh nền
        blackBar.setLayoutY(y);

        this.sprite = new ImageView();
        this.sprite.setFitWidth(10); // Kích thước thanh máu
        this.sprite.setFitHeight(10);

        // Tạo ImageView cho thanh máu
        greenBar = new ImageView(new Image(getClass().getResource(healthPath).toExternalForm()));
        greenBar.setLayoutX(x); // Định vị trùng với thanh nền
        greenBar.setLayoutY(y);
    }

    // Phương thức giảm máu
    public void updateHealth(double percentage) {
        if (percentage < 0) percentage = 0; // Không cho phép giảm dưới 0%
        if (percentage > 1) percentage = 1; // Không cho phép vượt quá 100%

        greenBar.setFitWidth(blackBar.getImage().getWidth() * percentage); // Giảm chiều dài dựa trên % máu
    }

    // Thêm cả hai thanh vào giao diện
    public void addToPane(Pane root) {
        root.getChildren().addAll(blackBar, greenBar);
    }
}
