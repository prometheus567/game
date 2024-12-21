package com.example.game1;

import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
    private double velocityX = 0;  // Vận tốc theo trục X
    private double velocityY = 0;  // Vận tốc theo trục Y
    private static final double GRAVITY = 0.5;  // Trọng lực

    public Player() {
        super(40, 60);  // Kích thước của nhân vật
        setStyle("-fx-fill: blue;");
        setTranslateX(200);
        setTranslateY(500);  // Vị trí ban đầu của nhân vật
    }

    public void move() {
        setTranslateX(getTranslateX() + velocityX);  // Di chuyển nhân vật theo trục X
        setTranslateY(getTranslateY() + velocityY);  // Di chuyển nhân vật theo trục Y
    }

    // Phương thức thiết lập vận tốc theo trục X
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    // Phương thức thiết lập vận tốc theo trục Y
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    // Phương thức áp dụng trọng lực
    public void applyGravity() {
        if (velocityY < 20) {
            velocityY += GRAVITY;  // Nếu chưa đạt vận tốc rơi tối đa, tăng trọng lực
        }
    }

    // Phương thức nhảy
    public void jump() {
        if (velocityY == 0) {  // Nếu đang đứng trên nền tảng, cho phép nhảy
            velocityY = -15;  // Tạo lực nhảy
        }
    }

    // Kiểm tra nếu nhân vật đang chạm đất
    public boolean isGrounded(Platform platform) {
        return getBoundsInParent().intersects(platform.getBoundsInParent());
    }
}
