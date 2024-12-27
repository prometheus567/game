package com.example.game1;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameOverEffect {

    private Pane root; // Pane chính của game
    private Character character; // Nhân vật chính
    private Scene scene;
    private AnimationTimer gameLoop;

    // Constructor
    public GameOverEffect(Pane root, Character character,Scene scene,AnimationTimer gameLoop) {
        this.root = root;
        this.character = character;
        this.scene = scene;
        this.gameLoop = gameLoop;
    }

    // Phương thức kích hoạt hiệu ứng Game Over
    public void triggerGameOver() {
        // Hiển thị trực tiếp hình ảnh Game Over
        ImageView gameOverImage = new ImageView(new Image(getClass().getResource("/deadscreen/gameover.png").toExternalForm()));
        gameOverImage.setFitWidth(400);
        gameOverImage.setFitHeight(300);
        gameOverImage.setTranslateX(200); // Canh giữa màn hình (hoặc chỉnh lại nếu cần)
        gameOverImage.setTranslateY(150);

        root.getChildren().add(gameOverImage); // Thêm vào Pane chính

        // Dừng game loop
        scene.setOnKeyPressed(null); // Vô hiệu hóa phím điều khiển
        gameLoop.stop(); // Dừng game loop
    }

    // Phương thức hiển thị ảnh Game Over
    private void showGameOverImage() {
        ImageView gameOverImage = new ImageView(new Image(getClass().getResource("/deadscreen/gameover.png").toExternalForm()));
        gameOverImage.setFitWidth(400);
        gameOverImage.setFitHeight(300);
        gameOverImage.setTranslateX(200);
        gameOverImage.setTranslateY(150);

        root.getChildren().add(gameOverImage);
    }
}