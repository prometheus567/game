package com.example.game1;

import javafx.scene.shape.Rectangle;

public class Platform extends Rectangle {
    public Platform(double width, double height, double x, double y) {
        super(width, height);
        setStyle("-fx-fill: green;");
        setTranslateX(x);
        setTranslateY(y);
    }

    // Kiểm tra va chạm giữa nhân vật và nền tảng
    public boolean isCollidingWith(Player player) {
        return getBoundsInParent().intersects(player.getBoundsInParent());
    }
}
