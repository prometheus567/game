package com.example.game1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GamePlatform extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Tạo nhân vật, nền tảng, mặt đất và quái vật
        Player player = new Player();
        Platform platform = new Platform(300, 20, 100, 450);  // Nền tảng giữa
        Platform ground = new Platform(800, 20, 0, 580); // Mặt đất ở dưới cùng
        Monster monster = new Monster(50, 50, 300, 500);  // Quái vật với kích thước và vị trí ban đầu

        // Thêm nhân vật, nền tảng, mặt đất, quái vật vào root layout
        root.getChildren().addAll(player, platform, ground, monster);

        // Cấu hình cửa sổ
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Game Platform");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Xử lý sự kiện bàn phím khi nhấn phím
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                player.setVelocityX(5);  // Di chuyển sang phải
            }
            if (event.getCode() == KeyCode.LEFT) {
                player.setVelocityX(-5);  // Di chuyển sang trái
            }
            if (event.getCode() == KeyCode.SPACE) {
                player.jump();  // Nhảy
            }
        });

        // Xử lý sự kiện khi thả phím
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                player.setVelocityX(0);  // Dừng di chuyển khi thả phím
            }
        });

        // Xử lý vòng lặp game
        GameLoop loop = new GameLoop(player, platform, ground, monster);
        loop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
