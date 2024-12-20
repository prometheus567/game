package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Character {
    private ImageView sprite;
    private double speedX; // Tốc độ di chuyển theo chiều ngang
    private double speedY; // Tốc độ di chuyển theo chiều dọc
    private double xPosition; // Vị trí x của nhân vật
    private double yPosition; // Vị trí y của nhân vật

    // Constructor của lớp Character
    public Character(String[] walkFramesPaths, double speedX, double speedY) {
        this.sprite = new ImageView();
        this.sprite.setFitWidth(50); // Kích thước của sprite (nếu cần)
        this.sprite.setFitHeight(50); // Kích thước của sprite (nếu cần)
        this.xPosition = 0; // Vị trí bắt đầu của nhân vật
        this.yPosition = 0; // Vị trí y bắt đầu
        this.speedX = speedX; // Tốc độ di chuyển theo chiều ngang
        this.speedY = speedY; // Tốc độ di chuyển theo chiều dọc

        // Khởi tạo mảng walkFrames từ các đường dẫn đã truyền vào
        Image[] walkFrames = new Image[walkFramesPaths.length];
        for (int i = 0; i < walkFramesPaths.length; i++) {
            // Sử dụng getClass().getResource() để lấy đường dẫn chính xác tới tài nguyên
            walkFrames[i] = new Image(getClass().getResource(walkFramesPaths[i]).toExternalForm());
        }

        // Tạo animation với Timeline
        Timeline walkAnimation = new Timeline();
        walkAnimation.setCycleCount(Timeline.INDEFINITE); // Lặp lại vô hạn

        // Thêm các KeyFrame vào Timeline để thay đổi hình ảnh
        for (int i = 0; i < walkFrames.length; i++) {
            final int index = i; // Cần final để sử dụng trong biểu thức lambda
            walkAnimation.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * (i + 1)), // Thời gian cho mỗi frame (điều chỉnh tốc độ frame rate)
                    event -> sprite.setImage(walkFrames[index]) // Thay đổi hình ảnh
            ));
        }

        // Thêm KeyFrame để di chuyển nhân vật
        walkAnimation.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0.1), // Di chuyển mỗi 0.1 giây
                event -> moveCharacter() // Di chuyển nhân vật
        ));

        // Bắt đầu animation
        walkAnimation.play();
    }

    // Hàm di chuyển nhân vật
    private void moveCharacter() {
        xPosition += speedX; // Cập nhật vị trí x của nhân vật theo tốc độ
        yPosition += speedY; // Cập nhật vị trí y của nhân vật theo tốc độ
        sprite.setX(xPosition); // Cập nhật vị trí của ImageView trên màn hình
        sprite.setY(yPosition); // Cập nhật vị trí y của ImageView trên màn hình
    }

    // Hàm trả về sprite để thêm vào scene
    public ImageView getSprite() {
        return sprite;
    }
}