package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
<<<<<<< HEAD
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
=======
>>>>>>> 5ccab4a77d03f9a527e53bb24dab9aa3b4f15b8c
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends javafx.application.Application {

    private Character character;
    private Monster monster;
    private Camera camera;
    private Pane root;

<<<<<<< HEAD
    public void start(Stage primaryStage) throws Exception {
        // Load FXML và ép kiểu root thành Pane
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("/healthbar loca.fxml"));

        // Tạo Scene từ FXML
        Scene scene = new Scene(root, 800, 600);

        // Tạo đối tượng Character
=======
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();



        // Đường dẫn đến các frame của nhân vật
>>>>>>> 5ccab4a77d03f9a527e53bb24dab9aa3b4f15b8c
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

        String[] idleFramesPaths = {
                "/character/idle/frame1.png",
                "/character/idle/frame2.png",
                "/character/idle/frame3.png",
                "/character/idle/frame4.png",
                "/character/idle/frame5.png",
                "/character/idle/frame6.png"
        };

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
                "/character/jump/frame11.png",
                "/character/jump/frame12.png"
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

        String[][] attackFramesPaths = {
                {
                        "/character/attack/frame1.png",
                        "/character/attack/frame2.png",
                        "/character/attack/frame3.png",
                        "/character/attack/frame4.png",
                        "/character/attack/frame5.png",
                        "/character/attack/frame6.png"
                },
                {
                        "/character/attack/frame7.png",
                        "/character/attack/frame8.png",
                        "/character/attack/frame9.png",
                        "/character/attack/frame10.png"
                },
                {
                        "/character/attack/frame11.png",
                        "/character/attack/frame12.png",
                        "/character/attack/frame13.png",

                }
        };

<<<<<<< HEAD
        Character character = new Character(
=======
        // Tạo đối tượng Character
        character = new Character(
>>>>>>> 5ccab4a77d03f9a527e53bb24dab9aa3b4f15b8c
                walkFramesPaths,
                jumpFramesPaths,
                idleFramesPaths,
                runFramesPaths,
                attackFramesPaths,
                100, 500
        );

<<<<<<< HEAD
        // Thêm nhân vật vào root Pane
=======
        // Tạo các frame cho hoạt ảnh đi bộ của quái vật
        String[] monsterWalkFrames = {
               /* "/Gotoku/Walk/sprite_0-ezgif.com-crop.png",
                "/Gotoku/Walk/sprite_0-ezgif.com-crop.png",
                "/Gotoku/Walk/sprite_2-ezgif.com-crop.png",
                "/Gotoku/Walk/sprite_3-ezgif.com-crop.png",
                "/Gotoku/Walk/sprite_4-ezgif.com-crop.png",
                "/Gotoku/Walk/sprite_5-ezgif.com-crop.png",*/
                "/Onre/Walk/sprite_0-ezgif.com-crop.png",
                "/Onre/Walk/sprite_1-ezgif.com-crop.png",
                "/Onre/Walk/sprite_2-ezgif.com-crop.png",
                "/Onre/Walk/sprite_3-ezgif.com-crop.png",
                "/Onre/Walk/sprite_4-ezgif.com-crop.png",
                "/Onre/Walk/sprite_5-ezgif.com-crop.png",
                "/Onre/Walk/sprite_6-ezgif.com-crop.png",
        };

// Tạo quái vật
        monster = new Monster(monsterWalkFrames, 300.0, 500.0);  // Dùng double cho vị trí


        // Tạo bản đồ
        var map = new Map(20, 20, root);

        // Khởi tạo camera
        camera = new Camera(800, 600, root);

>>>>>>> 5ccab4a77d03f9a527e53bb24dab9aa3b4f15b8c
        root.getChildren().add(character.getSprite());
        root.getChildren().add(monster.getSprite());

        // Thiết lập và hiển thị Stage
        primaryStage.setTitle("Ryoma");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Điều khiển nhân vật bằng bàn phím
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A -> character.setSpeedX(-2);
                case D -> character.setSpeedX(2);
                case SPACE -> character.jump();
                case J -> character.attack();
                case SHIFT -> {
                    if (event.isShiftDown()) {
                        if (character.getSpeedX() > 0) {
                            character.setSpeedX(5); // Chạy nhanh phải
                        } else if (character.getSpeedX() < 0) {
                            character.setSpeedX(-5); // Chạy nhanh trái
                        }
                    }
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A, D -> character.setSpeedX(0); // Dừng di chuyển khi thả phím
                case SHIFT -> character.setSpeedX(0); // Dừng chạy khi thả SHIFT
            }
        });

        // Vòng lặp game
        Timeline gameLoop = new Timeline(new KeyFrame(
                Duration.seconds(0.016),  // 60 FPS
                event -> updateGame()
        ));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }
<<<<<<< HEAD
}
=======

    // Cập nhật game, di chuyển nhân vật, quái vật và cập nhật camera
    private void updateGame() {
        // Cập nhật camera theo vị trí nhân vật
        camera.update(character.getX(), character.getY());

        // Di chuyển quái vật
        monster.move();

        // Các logic khác của game có thể đặt ở đây
    }

    public static void main(String[] args) {
        launch(args); // Khởi động ứng dụng JavaFX
    }
}
>>>>>>> 5ccab4a77d03f9a527e53bb24dab9aa3b4f15b8c
