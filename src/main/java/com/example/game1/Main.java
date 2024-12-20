package com.example.game1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Đường dẫn đến các frame đứng yên của nhân vật
        String[] runFramesPaths = {
                "/character/run/frame1.png",
                "/character/run/frame2.png",
                "/character/run/frame3.png",
                "/character/run/frame4.png",
                "/character/run/frame5.png",
                "/character/run/frame6.png",
                "/character/run/frame7.png",
                "/character/run/frame8.png"
        };

        // Đường dẫn đến các frame đứng yên của nhân vật
        String[] idleFramesPaths = {
                "/character/idle/frame1.png",
                "/character/idle/frame2.png",
                "/character/idle/frame3.png",
                "/character/idle/frame4.png",
                "/character/idle/frame5.png",
                "/character/idle/frame6.png"
        };

        // Đường dẫn đến các frame nhảy của nhân vật
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

        // Đường dẫn đến các frame đi bộ của nhân vật
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
        Character character = new Character(walkFramesPaths, jumpFramesPaths, idleFramesPaths,runFramesPaths, 100, 500);// Vị trí bắt đầu (100, 500)

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
                case LEFT:
                    if (event.isShiftDown()) {
                        character.setSpeedX(-5); // Chạy trái
                    } else {
                        character.setSpeedX(-2); // Đi bộ trái
                    }
                    break;
                case RIGHT:
                    if (event.isShiftDown()) {
                        character.setSpeedX(5); // Chạy phải
                    } else {
                        character.setSpeedX(2); // Đi bộ phải
                    }
                    break;
                case SPACE:
                    character.jump(); // Nhảy
                    break;
                case SHIFT:
                    if (character.getSpeedX() > 0) {
                        character.setSpeedX(5); // Chạy phải
                    } else if (character.getSpeedX() < 0) {
                        character.setSpeedX(-5); // Chạy trái
                    }
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    if (!event.isShiftDown()) {
                        character.setSpeedX(0); // Dừng đi trái
                    }
                    break;
                case RIGHT:
                    if (!event.isShiftDown()) {
                        character.setSpeedX(0); // Dừng đi phải
                    }
                    break;
                case SHIFT:
                    if (character.getSpeedX() > 0) {
                        character.setSpeedX(2); // Quay lại đi bộ phải
                    } else if (character.getSpeedX() < 0) {
                        character.setSpeedX(-2); // Quay lại đi bộ trái
                    }
                    break;
            }
        });
    }

    public static void main(String[] args) {
        launch(args); // Khởi động ứng dụng JavaFX
    }
}