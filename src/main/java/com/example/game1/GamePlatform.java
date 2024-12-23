package com.example.game1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GamePlatform extends Application {

    private Character character;
    private Platform ground;
    private Platform platform;
    private Monster monster;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Khởi tạo các đối tượng
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        // Khởi tạo các đối tượng cần thiết trong game
        ground = new Platform(0, 500, 800, 100);
        platform = new Platform(200, 400, 200, 20);
        character = new Character(100, 400);
        monster = new Monster(600, 420);

        // Thêm các đối tượng vào root
        root.getChildren().addAll(ground, platform, character.getSprite(), monster.getSprite());

        // Tạo game loop để liên tục cập nhật trạng thái game
        GameLoop gameLoop = new GameLoop(character, platform, ground, monster);

        // Tạo scene và các sự kiện từ bàn phím
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        // Thiết lập scene và bắt đầu game
        primaryStage.setScene(scene);
        primaryStage.setTitle("Platformer Game");
        primaryStage.show();

        // Bắt đầu game loop
        gameLoop.start();
    }

    // Xử lý khi nhấn phím
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            character.setSpeedX(-5);
        } else if (event.getCode() == KeyCode.RIGHT) {
            character.setSpeedX(5);
        } else if (event.getCode() == KeyCode.UP) {
            character.jump();
        } else if (event.getCode() == KeyCode.SPACE) {
            character.attack();
        }
    }

    // Xử lý khi thả phím
    private void onKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            character.setSpeedX(0);
        }
    }

    // GameLoop class để xử lý logic game liên tục
    class GameLoop extends AnimationTimer {

        private Character character;
        private Platform ground;
        private Platform platform;
        private Monster monster;

        public GameLoop(Character character, Platform platform, Platform ground, Monster monster) {
            this.character = character;
            this.platform = platform;
            this.ground = ground;
            this.monster = monster;
        }

        @Override
        public void handle(long now) {
            character.move();
            monster.move();
            monster.changeDirectionIfEdge(800);

            // Kiểm tra va chạm của nhân vật với đất hoặc các platform
            checkCollisions();
        }

        private void checkCollisions() {
            if (character.getSprite().getBoundsInParent().intersects(ground.getBoundsInParent())) {
                if (character.getTranslateY() + character.getSprite().getFitHeight() >= ground.getTranslateY()) {
                    character.setTranslateY(ground.getTranslateY() - character.getSprite().getFitHeight());
                    character.land();
                }
            } else {
                character.setFalling(true);
            }


            if (character.getSprite().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                if (character.getTranslateY() + character.getSprite().getFitHeight() <= platform.getTranslateY() + platform.getHeight()) {
                    character.setTranslateY(platform.getTranslateY() - character.getSprite().getFitHeight());
                    character.land();
                }
            }

            // Ngăn nhân vật ra ngoài màn hình
            if (character.getTranslateX() < 0) {
                character.setTranslateX(0);
            } else if (character.getTranslateX() > 800 - character.getSprite().getFitWidth()) {
                character.setTranslateX(800 - character.getSprite().getFitWidth());
            }
        }
    }
}
