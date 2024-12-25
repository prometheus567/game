package com.example.game1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

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

        // Tạo đối tượng Character
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
    }
}