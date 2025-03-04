package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Monster {
    private ImageView sprite; // Sprite cho quái vật
    private double speedX; // Tốc độ ngang
    private double speedY; // Tốc độ dọc
    private double xPosition; // Vị trí ngang
    private double yPosition; // Vị trí dọc

    private boolean onGround = true; // Kiểm tra xem quái vật có trên mặt đất không
    private double gravity = 5; // Trọng lực
    private double walkSpeed = 0.5; // Tốc độ đi bộ

    private Timeline moveTimeline; // Dòng thời gian cho hoạt hình đi bộ
    private Timeline backAndForthTimeline; // Dòng thời gian di chuyển qua lại
    private Timeline attackTimeline; // Dòng thời gian cho hoạt hình tấn công

    private Image[] attackFrames; // Các khung hình tấn công

    public Monster(String[] walkFramesPaths, String[] attackFramesPaths, double initialX, double initialY) {
        this.sprite = new ImageView();
        this.sprite.setFitWidth(100);
        this.sprite.setFitHeight(100);
        this.xPosition = initialX;
        this.yPosition = initialY;

        this.speedX = walkSpeed;
        this.speedY = 0;

        // Tải khung hình đi bộ
        Image[] walkFrames = new Image[walkFramesPaths.length];
        for (int i = 0; i < walkFramesPaths.length; i++) {
            walkFrames[i] = new Image(getClass().getResource(walkFramesPaths[i]).toExternalForm());
        }

        // Tải khung hình tấn công
        attackFrames = new Image[attackFramesPaths.length];
        for (int i = 0; i < attackFramesPaths.length; i++) {
            attackFrames[i] = new Image(getClass().getResource(attackFramesPaths[i]).toExternalForm());
        }

        // Thiết lập dòng thời gian cho hoạt hình đi bộ
        moveTimeline = new Timeline();
        moveTimeline.setCycleCount(Timeline.INDEFINITE);

        for (int i = 0; i < walkFrames.length; i++) {
            final int index = i;
            moveTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i),
                    event -> sprite.setImage(walkFrames[index])
            ));
        }

        // Thiết lập dòng thời gian di chuyển qua lại
        backAndForthTimeline = new Timeline();
        backAndForthTimeline.setCycleCount(Timeline.INDEFINITE);
        backAndForthTimeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(2),
                event -> speedX = -speedX // Đổi hướng sau mỗi 2 giây
        ));

        // Thiết lập dòng thời gian cho hoạt hình tấn công
        attackTimeline = new Timeline();
        attackTimeline.setCycleCount(Timeline.INDEFINITE);

        for (int i = 0; i < attackFrames.length; i++) {
            final int index = i;
            attackTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i),
                    event -> sprite.setImage(attackFrames[index])
            ));
        }

        // Chạy hoạt hình và di chuyển
        moveTimeline.play();
        backAndForthTimeline.play();
    }

    public void moveMonster(double characterX, double characterY) {
        double distance = Math.sqrt(Math.pow(characterX - xPosition, 2) + Math.pow(characterY - yPosition, 2));

        if (distance < 50) { // Nếu quái vật đến gần nhân vật
            speedX = 0; // Dừng di chuyển
            moveTimeline.stop();
            backAndForthTimeline.stop();
            attackTimeline.play(); // Chuyển sang trạng thái tấn công
        } else {
            speedX = walkSpeed; // Tiếp tục đi bộ
            attackTimeline.stop(); // Dừng tấn công
            moveTimeline.play();
            backAndForthTimeline.play();
        }

        // Di chuyển quái vật
        xPosition += speedX;

        if (!onGround) {
            speedY += gravity; // Áp dụng trọng lực nếu không ở trên mặt đất
        }

        yPosition += speedY;

        if (yPosition >= 500) { // Nếu quái vật xuống đất
            yPosition = 500;
            onGround = true;
            speedY = 0;
        }

        // Lật hình ảnh dựa trên hướng đi
        sprite.setScaleX(speedX > 0 ? 1 : -1);

        // Đặt vị trí cho sprite
        sprite.setTranslateX(xPosition);
        sprite.setTranslateY(yPosition);
    }

    // Đặt tốc độ ngang
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    // Nhận sprite để thêm vào cảnh
    public ImageView getSprite() {
        return sprite;
    }

    // Gọi hàm di chuyển
    public void move(double characterX, double characterY) {
        moveMonster(characterX, characterY);
    }

    public void setTarget(double targetX, double targetY) {
        // Tính toán hướng đến mục tiêu
        double deltaX = targetX - xPosition;
        double deltaY = targetY - yPosition;

        // Đặt tốc độ theo hướng mục tiêu
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY); // Tính khoảng cách tới mục tiêu

        if (distance > 1) { // Tránh di chuyển không cần thiết khi đã đến gần mục tiêu
            speedX = (deltaX / distance) * walkSpeed; // Normalized hướng X
            speedY = (deltaY / distance) * walkSpeed; // Normalized hướng Y (nếu cần)
        } else {
            speedX = 0;
            speedY = 0;
        }
    }
}
