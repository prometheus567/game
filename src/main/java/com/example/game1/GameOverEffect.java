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
        if (gameLoop != null) {
            gameLoop.stop(); // Dừng game loop
            gameLoop = null; // Ngăn chặn kích hoạt lại
        }

        scene.setOnKeyPressed(null); // Tắt điều khiển bàn phím

        ImageView gameOverImage = new ImageView(
                new Image(getClass().getResource("/deadscreen/gameover.jpg").toExternalForm())
        );
        gameOverImage.setFitWidth(400);
        gameOverImage.setFitHeight(300);
        gameOverImage.setTranslateX(200);
        gameOverImage.setTranslateY(150);

        root.getChildren().add(gameOverImage); // Hiển thị ảnh Game Over
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