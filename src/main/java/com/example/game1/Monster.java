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
    private double xPosition; // Vị trí nằm ngang
    private double yPosition; // Vị trí dọc

    private boolean onGround = true; // Kiểm tra xem quái vật có ở trên mặt đất không
    private double gravity = 0.5; // Trọng lực
    private double walkSpeed = 1; // Tốc độ đi bộ của quái vật

    private Timeline moveTimeline; // Dòng thời gian di chuyển của quái vật
    private Timeline backAndForthTimeline; // Dòng thời gian cho chuyển động qua lại của quái vật

    // Người xây dựng
    public Monster(String[] walkFramesPaths, double initialX, double initialY) {
        this.sprite = new ImageView();
        this.sprite.setFitWidth(100); // Đặt kích thước quái vật
        this.sprite.setFitHeight(100);
        this.xPosition = initialX;
        this.yPosition = initialY;

        this.speedX = walkSpeed;
        this.speedY = 0;

        // Tải khung hình hoạt hình đi bộ
        Image[] walkFrames = new Image[walkFramesPaths.length];
        for (int i = 0; i < walkFramesPaths.length; i++) {
            walkFrames[i] = new Image(getClass().getResource(walkFramesPaths[i]).toExternalForm());
        }

        // Thiết lập dòng thời gian đi bộ
        moveTimeline = new Timeline();
        moveTimeline.setCycleCount(Timeline.INDEFINITE);

        // Thêm khung hình chính cho hoạt ảnh đi bộ
        for (int i = 0; i < walkFrames.length; i++) {
            final int index = i;
            moveTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i), // Thời gian cho mỗi khung hình
                    event -> sprite.setImage(walkFrames[index]) // Đặt khung đi bộ tương ứng
            ));
        }

        // Thiết lập mốc thời gian chuyển động qua lại
        backAndForthTimeline = new Timeline();
        backAndForthTimeline.setCycleCount(Timeline.INDEFINITE);
        backAndForthTimeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(2), // Thay đổi hướng mỗi 2 giây
                event -> speedX = -speedX // Hướng ngược lại
        ));

        // Chơi hoạt hình đi bộ và dòng thời gian chuyển động
        moveTimeline.play();
        backAndForthTimeline.play();
    }

    public void moveMonster() {
        // Di chuyển quái vật dọc theo X
        xPosition += speedX;

        if (!onGround) {
            speedY += gravity; // Áp dụng trọng lực nếu không ở trên mặt đất
        }

        // Di chuyển quái vật dọc theo Y
        yPosition += speedY;

        if (yPosition >= 500) { // Nếu quái vật đập xuống đất
            yPosition = 500;
            onGround = true;
            speedY = 0;
        }

        // Lật hình ảnh quái vật dựa trên hướng di chuyển
        sprite.setScaleX(speedX > 0 ? 1 : -1);

        // Đặt vị trí cho sprite
        sprite.setTranslateX(xPosition);
        sprite.setTranslateY(yPosition);
    }

    // Đặt tốc độ di chuyển ngang
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    // Nhận sprite để thêm vào cảnh
    public ImageView getSprite() {
        return sprite;
    }

    // Gọi moveMonster để thực sự di chuyển quái vật trong mỗi vòng lặp trò chơi
    public void move() {
        moveMonster();  // Di chuyển quái vật
    }
}
