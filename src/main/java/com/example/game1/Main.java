package com.example.game1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
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

        // Tạo đối tượng Character với các frame đi bộ và tốc độ
        Character character = new Character(walkFramesPaths, 2, 2);

        // Lấy sprite từ character để thêm vào scene
        ImageView characterSprite = character.getSprite();

        // Tạo layout và thêm sprite vào đó
        StackPane root = new StackPane();
        root.getChildren().add(characterSprite);

        // Tạo scene và gán vào stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Character Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Khởi động ứng dụng JavaFX
    }
}