package com.example.game1;

import javafx.scene.shape.Rectangle;

public class Monster extends Rectangle {
    private double velocityX = 2; // Tốc độ di chuyển của quái vật

    public Monster(double width, double height, double x, double y) {
        super(width, height);  // Kích thước của quái vật
        setStyle("-fx-fill: red;"); // Màu sắc của quái vật
        setTranslateX(x);  // Vị trí ban đầu của quái vật
        setTranslateY(y);
    }

    // Phương thức di chuyển quái vật (di chuyển qua trái hoặc phải)
    public void move() {
        setTranslateX(getTranslateX() + velocityX);
    }

    // Phương thức kiểm tra va chạm với nhân vật
    public boolean isCollidingWith(Player player) {
        return getBoundsInParent().intersects(player.getBoundsInParent());
    }

    public void changeDirectionIfEdge(double maxWidth) {
        if (getTranslateX() <= 0 || getTranslateX() >= maxWidth) {
            velocityX = -velocityX;  // Đổi hướng nếu gặp bờ
        }
    }
}
