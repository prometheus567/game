package com.example.game1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private Pane root; // Pane chính
    private Character character; // Nhân vật chính
    private HealthBarController controller; // Controller thanh máu
    private GameOverEffect gameOverEffect; // Hiệu ứng Game Over

    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/healthbar loca.fxml"));
        root = loader.load();

        // Truy cập controller
        controller = loader.getController();

        // Tạo Scene từ FXML, đặt kích thước cố định
        Scene scene = new Scene(root, 800, 600);

        // Tương tác với thanh shieldbar
        controller.updateShieldBar(1.0); // Gọi một phương thức để thay đổi trạng thái shield bar
        controller.takeShieldDamage(); // Giảm shield

        // Tương tác với thanh greenbar
        controller.takeGreenbarDamage(); // Gọi để giảm máu
        controller.updateGreenbar(1.0); // Hồi phục đầy máu

        // Tạo đối tượng Character
        String[] deadFramesPaths = {
                "/character/dead/frame1.png",
                "/character/dead/frame2.png",
                "/character/dead/frame3.png"
        };

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

        character = new Character(
                walkFramesPaths,
                jumpFramesPaths,
                idleFramesPaths,
                runFramesPaths,
                attackFramesPaths,
                hurtFramesPaths,
                shieldFramesPaths,
                deadFramesPaths,
                100,
                500
        );

        // Thiết lập callback cho animation chết
        character.setOnDeathCallback(() -> gameOverEffect.triggerGameOver());

        // Thêm nhân vật vào root Pane
        root.getChildren().add(character.getSprite());


        // Khởi tạo game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame(); // Cập nhật logic game
                checkGameOver(); // Kiểm tra điều kiện Game Over
            }
        };
        gameLoop.start();

        // Tạo hiệu ứng game over
        gameOverEffect = new GameOverEffect(root, character, scene, gameLoop);



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
                case H -> character.hurt();
                case S -> character.shield(); // Bật shield khi nhấn phím S
                case K -> controller.takeShieldDamage();
                case U -> controller.takeGreenbarDamage(); // Giảm máu khi nhấn phím H
                case R -> controller.updateGreenbar(1.0); // Hồi phục đầy máu khi nhấn phím R
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

    }

    private void updateGame() {
        // Cập nhật trạng thái nhân vật
        if (!character.isDead()) {
            character.moveCharacter();
        }
    }

    private void checkGameOver() {
        if (controller.getGreenbarHealth() <= 0) {
            character.triggerDeadAnimation(); // Gọi animation chết (nếu cần)
            gameOverEffect.triggerGameOver(); // Hiển thị hình ảnh Game Over
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}