package com.example.game1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private Camera camera;
    private Monster monster;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/healthbar loca.fxml"));
        Pane root = loader.load();

        // Truy cập controller
        HealthBarController controller = loader.getController();

        // Tương tác với thanh shieldbar (ví dụ: thay đổi kích thước hoặc màu sắc)
        controller.updateShieldBar(1.0); // Gọi một phương thức để thay đổi trạng thái shield bar
        controller.takeShieldDamage(); // Giảm shield

        // Tạo Scene từ FXML, đặt kích thước cố định
        Scene scene = new Scene(root, 800, 600);

        // Ma trận đại diện cho map
        int[][] mapData = {
                {0, 0, 1, 1, 2, 2, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 1, 0, 2, 0, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {0, 0, 1, 1, 2, 2, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 1, 0, 2, 0, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {0, 0, 1, 1, 2, 2, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 1, 0, 2, 0, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {0, 0, 1, 1, 2, 2, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 1, 0, 2, 0, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
        };

        // Tạo map
        TileMap map = new TileMap(mapData, root);

        // Hoặc dùng toFront() cho thanh máu
        controller.getGreenbar().toFront();
        controller.getShieldbar().toFront();

        // Đường dẫn đến các frame của nhân vật
        String[] shieldFramesPaths = {
                "/character/shield/frame1.png",
                "/character/shield/frame2.png"
        };

        String[] hurtFramesPaths = {
                "/character/hurt/frame1.png",
                "/character/hurt/frame2.png",
                "/character/hurt/frame3.png",
                "/character/hurt/frame4.png"
        };


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
                        "/character/attack/frame13.png"
                }
        };

        // Tạo nhân vật chính
        Character character = new Character(
                walkFramesPaths,
                jumpFramesPaths,
                idleFramesPaths,
                runFramesPaths,
                attackFramesPaths,
                hurtFramesPaths,
                shieldFramesPaths,
                100,
                500
        );

        // Thêm nhân vật vào root Pane
        root.getChildren().add(character.getSprite());

// Tạo các frame cho hoạt ảnh đi bộ của quái vật
        String[] monsterWalkFrames = {
                "/monster/hl/hl1/Walk/Walk_1.png",
                "/monster/hl/hl1/Walk/Walk_2.png",
                "/monster/hl/hl1/Walk/Walk_3.png",
                "/monster/hl/hl1/Walk/Walk_4.png",
                "/monster/hl/hl1/Walk/Walk_5.png",
                "/monster/hl/hl1/Walk/Walk_6.png",
                "/monster/hl/hl1/Walk/Walk_7.png",
                "/monster/hl/hl1/Walk/Walk_8.png"
        };

        // Tạo quái vật
        monster = new Monster(monsterWalkFrames, 300.0, 500.0);
        root.getChildren().add(monster.getSprite());

        // Khởi tạo camera
        camera = new Camera(800, 600, root);

        // Thiết lập và hiển thị Stage
        primaryStage.setTitle("Game Map Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Điều khiển nhân vật bằng bàn phím
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A -> character.setSpeedX(-2);
                case D -> character.setSpeedX(2);
                case SPACE -> character.jump();
                case J -> character.attack();
                case H -> character.hurt();
                case S -> character.shield(); // Bật shield khi nhấn phím S
                case K -> controller.takeShieldDamage();
                case R -> controller.updateShieldBar(1.0); // Reset thanh shield về đầy đủ
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
                case S -> character.stopShielding(); // Dừng shield khi thả phím
                case SHIFT -> character.setSpeedX(0); // Dừng chạy khi thả SHIFT
            }
        });


        // Game loop đơn giản
        new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame(character);
            }
        }.start();
    }

    private void updateGame(Character character) {
        // Cập nhật camera theo vị trí nhân vật
        camera.update(character.getX(), character.getY());

        // Cập nhật mục tiêu AI cho quái vật
        monster.setTarget(character.getX(), character.getY());

        // Di chuyển quái vật
        monster.move();

        // Các logic khác của game có thể đặt ở đây
    }

    public static void main(String[] args) {
        launch(args);
    }
}