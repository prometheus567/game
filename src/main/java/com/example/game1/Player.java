package com.example.game1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private double speedX = 0;
    private double speedY = 0;
    private boolean isJumping = false;
    private ImageView sprite;

    private static final double GRAVITY = 0.5; // Gia tốc trọng lực
    private static final double JUMP_STRENGTH = -12; // Lực nhảy

    public Player(String[] walkFramesPaths, String[] jumpFramesPaths, String[] idleFramesPaths,
                  String[] runFramesPaths, String[][] attackFramesPaths, double startX, double startY) {
        // Tạo hình ảnh nhân vật ban đầu (Idle)
        sprite = new ImageView(new Image("file:src/main/resources/character/idle/frame1.png"));
        sprite.setTranslateX(startX); // Chỉnh lại từ setX
        sprite.setTranslateY(startY); // Chỉnh lại từ setY
    }

    public ImageView getSprite() {
        return sprite;
    }

    public double getTranslateX() {
        return sprite.getTranslateX();
    }

    public double getTranslateY() {
        return sprite.getTranslateY();
    }

    public void setTranslateX(double x) {
        sprite.setTranslateX(x);
    }

    public void setTranslateY(double y) {
        sprite.setTranslateY(y);
    }

    public void setSpeedX(double speed) {
        this.speedX = speed;
    }

    public void move() {
        sprite.setTranslateX(sprite.getTranslateX() + speedX);

        if (!isJumping) {
            speedY += GRAVITY; // Tăng trọng lực nếu đang rơi
        }
        sprite.setTranslateY(sprite.getTranslateY() + speedY);
    }

    public void jump() {
        if (!isJumping) {
            speedY += GRAVITY;
        }
        sprite.setTranslateY(sprite.getTranslateY() + speedY);

    }

    public void land() {
        isJumping = false; // Nhân vật không rơi nữa
        speedY = 0; // Dừng trọng lực
        sprite.setImage(new Image("file:src/main/resources/character/idle/frame1.png")); // Trở lại hoạt ảnh idle
    }


    public void setFalling(boolean isFalling) {
        isJumping = isFalling;
        if (!isFalling) {
            land(); // Gọi logic hạ cánh tự động
        }
    }
    public boolean checkCollision(Platform platform) {
        return sprite.getBoundsInParent().intersects(platform.getBoundsInParent());
    }


}


