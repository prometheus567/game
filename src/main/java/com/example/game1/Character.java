package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Character {
    private ImageView sprite; // Hiển thị nhân vật
    private double speedX; // Tốc độ ngang
    private double speedY; // Tốc độ dọc
    private double xPosition; // Vị trí x
    private double yPosition; // Vị trí y

    private boolean onGround = true; // Kiểm tra nhân vật có đang đứng trên đất
    private double gravity = 0.4; // Lực kéo trọng lực
    private double jumpForce = -12; // Lực nhảy

    private Timeline moveTimeline; // Timeline để cập nhật di chuyển
    private Timeline walkTimeline; // Timeline hoạt ảnh đi bộ
    private Timeline jumpTimeline; // Timeline hoạt ảnh nhảy

    // Constructor
    public Character(String[] walkFramesPaths, String[] jumpFramesPaths, double initialX, double initialY) {
        this.sprite = new ImageView();
        this.sprite.setFitWidth(50); // Kích thước nhân vật
        this.sprite.setFitHeight(50);

        this.xPosition = initialX; // Vị trí ban đầu
        this.yPosition = initialY;

        this.speedX = 0;
        this.speedY = 0;

        // Tạo hoạt ảnh đi bộ
        Image[] walkFrames = new Image[walkFramesPaths.length];
        for (int i = 0; i < walkFramesPaths.length; i++) {
            walkFrames[i] = new Image(getClass().getResource(walkFramesPaths[i]).toExternalForm());
        }

        walkTimeline = new Timeline();
        walkTimeline.setCycleCount(Timeline.INDEFINITE);
        for (int i = 0; i < walkFrames.length; i++) {
            final int index = i;
            walkTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i),
                    event -> sprite.setImage(walkFrames[index])
            ));
        }

        // Tạo hoạt ảnh nhảy
        Image[] jumpFrames = new Image[jumpFramesPaths.length];
        for (int i = 0; i < jumpFramesPaths.length; i++) {
            jumpFrames[i] = new Image(getClass().getResource(jumpFramesPaths[i]).toExternalForm());
        }

        jumpTimeline = new Timeline();
        jumpTimeline.setCycleCount(1); // Chỉ phát 1 lần
        for (int i = 0; i < jumpFrames.length; i++) {
            final int index = i;
            jumpTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i),
                    event -> sprite.setImage(jumpFrames[index])
            ));
        }

        // Timeline cập nhật di chuyển
        moveTimeline = new Timeline(new KeyFrame(
                Duration.seconds(0.016), // 60 FPS
                event -> moveCharacter()
        ));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();
    }

    private void moveCharacter() {
        xPosition += speedX;

        if (!onGround) {
            speedY += gravity;
        }
        yPosition += speedY;

        if (yPosition >= 500) { // Vị trí mặt đất
            yPosition = 500;
            onGround = true;
            speedY = 0;
            walkTimeline.play(); // Quay lại hoạt ảnh đi bộ sau khi nhảy
        }

        sprite.setTranslateX(xPosition);
        sprite.setTranslateY(yPosition);
    }

    public void jump() {
        if (onGround) {
            speedY = jumpForce;
            onGround = false;

            walkTimeline.stop(); // Tạm dừng hoạt ảnh đi bộ
            jumpTimeline.playFromStart(); // Chạy hoạt ảnh nhảy
        }
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;

        if (speedX != 0) {
            if (walkTimeline.getStatus() != Timeline.Status.RUNNING && onGround) {
                walkTimeline.play();
            }

            // Lật nhân vật nếu đi trái
            sprite.setScaleX(speedX > 0 ? 1 : -1);
        } else {
            walkTimeline.stop(); // Dừng hoạt ảnh khi không di chuyển
        }
    }

    public ImageView getSprite() {
        return sprite;
    }
}