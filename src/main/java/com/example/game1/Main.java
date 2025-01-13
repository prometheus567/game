package com.example.game1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
    private Map currentMap;          // Quản lý map hiện tại
    private Character character;     // Nhân vật chính
    private Monster monster;         // Quái vật
    private Camera camera;           // Camera
    private Platform platform;       // Nền
    private HealthBarController controller;  // Controller cho thanh máu và shield
    private GameOverEffect gameOverEffect;   // Hiệu ứng game over

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Khởi tạo Map, Character, Monster và các thành phần khác
        initializeMaps();
        initializeCharacter();
        initializeMonster();
        initializeHealthBar();

        // Thiết lập camera
        camera = new Camera(800, 600, currentMap.getRoot());

        // Tạo Scene
        Scene scene = new Scene(currentMap.getRoot(), 800, 600);

        // Thiết lập điều khiển bàn phím
        setupKeyControls(scene);

        // Hiển thị stage
        primaryStage.setTitle("Ryoma");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Tạo hiệu ứng game over
        gameOverEffect = new GameOverEffect(currentMap.getRoot(), character, scene, null);

        // Khởi động game loop
        startGameLoop();
    }

    private void initializeMaps() {
        currentMap = new Map("Lan Dau", 10000, 800);

        // Thêm nhiều tầng nền
        currentMap.addBackground(getClass().getResource("/map/nen.jpg").toExternalForm(), 0, 0, 1920, 1080);
        currentMap.addBackground(getClass().getResource("/map/nen.jpg").toExternalForm(), 1920, 0, 1920, 1080);

        // Thêm nền tảng
        platform = new Platform(getClass().getResource("/map/co.png").toExternalForm(), 0, 800, 2000, 100);
        currentMap.addPlatform(platform);
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 800, 400, 300, 50));

        // Thêm các thành phần vào root
        currentMap.getRoot().getChildren().add(character.getSprite());
        currentMap.getRoot().getChildren().add(monster.getSprite());
    }

    private void initializeCharacter() {
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
                deadFramesPaths, // Thêm deadFramesPaths vào đây
                100,             // initialX
                400              // initialY
        );
        character.setOnDeathCallback(() -> gameOverEffect.triggerGameOver());
    }

    private void initializeMonster() {
        String[] walkFramesPaths = {
                "/monster/Onre/Walk/sprite_0-ezgif.com-crop.png", "/monster/Onre/Walk/sprite_1-ezgif.com-crop.png"
        };
        String[] attackFramesPaths = {
                "/monster/Onre/Attack/sprite_00-ezgif.com-crop.png", "/monster/Onre/Attack/sprite_01-ezgif.com-crop.png"
        };

        monster = new Monster(walkFramesPaths, attackFramesPaths, 300.0, 500.0);
    }

    private void initializeHealthBar() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/healthbar loca.fxml"));
        Pane healthBarPane = loader.load();

        controller = loader.getController();
        controller.updateShieldBar(1.0);
        controller.updateGreenbar(1.0);

        currentMap.getRoot().getChildren().add(healthBarPane);
    }

    private void setupKeyControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A -> character.setSpeedX(-2);  // Đi sang trái
                case D -> character.setSpeedX(2);   // Đi sang phải
                case SPACE -> character.jump();     // Nhảy
                case J -> character.attack();       // Tấn công
                case H -> character.hurt();         // Bị thương
                case S -> character.shield();       // Bật shield
                case K -> controller.takeShieldDamage();  // Giảm shield
                case U -> controller.takeGreenbarDamage(); // Giảm máu
                case R -> controller.updateGreenbar(1.0);  // Hồi phục đầy máu
                case SHIFT -> {
                    if (event.isShiftDown()) {
                        character.setSpeedX(character.getSpeedX() > 0 ? 4 : -4); // Chạy nhanh
                    }
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A, D -> character.setSpeedX(0);  // Dừng lại
                case S -> character.stopShielding(); // Dừng shield
                case SHIFT -> character.setSpeedX(0); // Dừng chạy nhanh
            }
        });
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        // Cập nhật logic nhân vật
        if (character.isDead()) {
            return;
        }
        character.moveCharacter(currentMap);

        // Cập nhật logic quái vật
        monster.setTarget(character.getX(), character.getY());
        monster.move(character.getX(), character.getY());

        // Cập nhật camera
        camera.update(character.getX(), character.getY());
    }

    public static void main(String[] args) {
        launch(args);
    }
}