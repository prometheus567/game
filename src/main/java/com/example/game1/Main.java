package com.example.game1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Đường dẫn đến các frame đi bộ của nhân vật

        String[] jumpFramesPaths = {
                "/character/jump/frame1.png",
                "/character/jump/frame2.png",
                "/character/jump/frame3.png",
                "/character/jump/frame4.png",
                "/character/jump/frame5.png",
                "/character/jump/frame6.png",
                "/character/jump/frame7.png",
                "/character/jump/frame8.png",
                "/character/jump/frame9.png",
                "/character/jump/frame10.png",
                "/character/jump/frame11.png"
        };

        String[] walkFramesPaths = {
                "/character/walk/frame1.png",
                "/character/walk/frame2.png",
                "/character/walk/frame3.png",
                "/character/walk/frame4.png",
                "/character/walk/frame5.png",
                "/character/walk/frame6.png",
                "/character/walk/frame7.png",
                "/character/walk/frame8.png"
        };

        // Tạo đối tượng Character
        Character character = new Character(walkFramesPaths, jumpFramesPaths, 100, 500); // Vị trí bắt đầu (100, 500)

        // Lấy sprite từ character
        Pane root = new Pane();
        root.getChildren().add(character.getSprite());

        // Tạo scene và gán vào stage
        Scene scene = new Scene(root, 800, 600); // Nền trắng mặc định
        primaryStage.setTitle("2D Platformer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Điều khiển nhân vật bằng bàn phím
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT: // Đi trái
                    character.setSpeedX(-2);
                    break;
                case RIGHT: // Đi phải
                    character.setSpeedX(2);
                    break;
                case SPACE: // Nhảy
                    character.jump();
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                case RIGHT:
                    character.setSpeedX(0); // Dừng di chuyển khi thả phím
                    break;
            }
        });
    }

    public static void main(String[] args) {
        launch(args); // Khởi động ứng dụng JavaFX
    }
}