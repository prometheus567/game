package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.example.game1.Character.GRAVITY;

public class Main extends Application {
    private Map currentMap = new Map("Map Lan Dau", 10000, 800);

    private Character character;
    private Monster monster;
    private Camera camera;
    private Stage primaryStage;
    private Platform platform;
    private Timeline moveTimeline;




    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        initializeCharacter();
        initializeMonster();

        // Khởi tạo các thành phần
        initializeMaps();


        // Thiết lập camera
        camera = new Camera(600, 600, currentMap.getRoot());

        // Khởi tạo Scene với map đầu tiên
        Scene scene = new Scene(currentMap.getRoot(), 800, 600);

        // Khởi tạo thanh máu và thanh shield
        initializeHealthBar();

        // Thiết lập điều khiển bàn phím
        setupKeyControls(scene);

        // Thiết lập Stage
        primaryStage.setTitle("Game Map Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Bắt đầu game loop
        startGameLoop();
    }
    private void initializeMaps() {

        // Thêm nhiều tầng hình nền
        currentMap.addBackground(getClass().getResource("/map/nen.jpg").toExternalForm(), 0, 0, 1920, 1080);  // Tầng xa
        currentMap.addBackground(getClass().getResource("/map/nen.jpg").toExternalForm(), 1920, 0, 1920, 1080);  // Tầng xa
        currentMap.addBackground(getClass().getResource("/map/nen.jpg").toExternalForm(), 3840, 0, 1920, 1080);  // Tầng xa
        currentMap.addBackground(getClass().getResource("/map/nen.jpg").toExternalForm(), 5760, 0, 1920, 1080);  // Tầng xa
        currentMap.addBackground(getClass().getResource("/map/nen.jpg").toExternalForm(), 7680, 0, 1920, 1080);  // Tầng xa
        // Khởi tạo và thêm nền tảng
        platform = new Platform(getClass().getResource("/map/co.png").toExternalForm(), 0, 800, 2000, 100);
        currentMap.addPlatform(platform); // Gán platform chính vào biến toàn cục

        // Thêm nền tảng khác
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 800, 400, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 2400, 800, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 2800, 800, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 3400, 800, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 4200, 800, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 4600, 800, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 5000, 800, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 5400, 800, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 5800, 800, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 6400, 800, 300, 50));

        // Thêm nhân vật và quái vật vào map
        currentMap.getRoot().getChildren().add(character.getSprite());
        currentMap.getRoot().getChildren().add(monster.getSprite());
    }

    private void initializeCharacter() {
        if (currentMap == null) {
            throw new IllegalStateException("Map has not been initialized. Call initializeMaps() first.");
        }
        // Tạo nhân vật với các frame hoạt ảnh
        String[] walkFramesPaths = {
                "/character/walk/frame1.png", "/character/walk/frame2.png",
                "/character/walk/frame3.png", "/character/walk/frame4.png",
                "/character/walk/frame5.png", "/character/walk/frame6.png",
                "/character/walk/frame7.png", "/character/walk/frame8.png"
        };
        String[] jumpFramesPaths = {
                "/character/jump/frame1.png", "/character/jump/frame2.png",
                "/character/jump/frame3.png", "/character/jump/frame4.png",
                "/character/jump/frame5.png", "/character/jump/frame6.png",
                "/character/jump/frame7.png", "/character/jump/frame8.png",
                "/character/jump/frame9.png", "/character/jump/frame10.png",
                "/character/jump/frame11.png", "/character/jump/frame12.png"
        };
        String[] idleFramesPaths = {
                "/character/idle/frame1.png", "/character/idle/frame2.png",
                "/character/idle/frame3.png", "/character/idle/frame4.png",
                "/character/idle/frame5.png", "/character/idle/frame6.png"
        };
        String[] runFramesPaths = {
                "/character/run/frame1.png", "/character/run/frame2.png",
                "/character/run/frame3.png", "/character/run/frame4.png",
                "/character/run/frame5.png", "/character/run/frame6.png",
                "/character/run/frame7.png", "/character/run/frame8.png"
        };
        String[][] attackFramesPaths = {
                {"/character/attack/frame1.png", "/character/attack/frame2.png",
                        "/character/attack/frame3.png", "/character/attack/frame4.png",
                        "/character/attack/frame5.png", "/character/attack/frame6.png"},
                {"/character/attack/frame7.png", "/character/attack/frame8.png",
                        "/character/attack/frame9.png", "/character/attack/frame10.png"},
                {"/character/attack/frame11.png", "/character/attack/frame12.png",
                        "/character/attack/frame13.png"}
        };
        String[] hurtFramesPaths = {
                "/character/hurt/frame1.png", "/character/hurt/frame2.png",
                "/character/hurt/frame3.png", "/character/hurt/frame4.png"
        };
        String[] shieldFramesPaths = {
                "/character/shield/frame1.png", "/character/shield/frame2.png"
        };

        character = new Character(
                walkFramesPaths, jumpFramesPaths, idleFramesPaths, runFramesPaths,
                attackFramesPaths, hurtFramesPaths, shieldFramesPaths, 100, 400
        );
        // Kiểm tra ngay lập tức xem nhân vật có trên nền không
        Platform collidedPlatform = currentMap.checkCollision(character.getX(),
                character.getY() + character.getHeight(),
                character.getWidth(),
                character.getHeight());
        if (collidedPlatform != null) {
            character.setOnGround(true); // Đặt trạng thái đứng trên nền
            character.setY(collidedPlatform.getY() - character.getHeight()); // Đặt lại vị trí y
        } else {
            character.setOnGround(false); // Không trên nền
        }
    };

    private void initializeMonster() {
        String[] monsterWalkFrames = {
                "/monster/Onre/Walk/sprite_0-ezgif.com-crop.png",
                "/monster/Onre/Walk/sprite_1-ezgif.com-crop.png",
                "/monster/Onre/Walk/sprite_2-ezgif.com-crop.png",
                "/monster/Onre/Walk/sprite_3-ezgif.com-crop.png",
                "/monster/Onre/Walk/sprite_4-ezgif.com-crop.png",
                "/monster/Onre/Walk/sprite_5-ezgif.com-crop.png",
        };
        String[] monsterAttackFrames = {
                "/monster/Onre/Attack/sprite_00-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_01-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_02-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_03-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_04-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_05-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_06-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_07-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_08-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_09-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_10-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_11-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_12-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_13-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_14-ezgif.com-crop.png",
                "/monster/Onre/Attack/sprite_15-ezgif.com-crop.png",
        };
        monster = new Monster(monsterWalkFrames, monsterAttackFrames, 300.0, 500.0);
    }

    private void initializeHealthBar() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/healthbar loca.fxml"));
        Pane healthBarPane = loader.load();

        // Tương tác với controller
        HealthBarController controller = loader.getController();
        controller.updateShieldBar(1.0);
        controller.takeShieldDamage();

        // Thêm thanh máu vào map
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
                case SHIFT -> {
                    if (event.isShiftDown()) {
                        character.setSpeedX(character.getSpeedX() > 0 ? 5 : -5); // Chạy nhanh
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
        new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            };
        }.start();



    };

    private void updateGame() {
        // Kiểm tra va chạm với nền
        if (isOnPlatform()) {
            if (!character.isOnGround()) {
                character.setSpeedY(0); // Dừng rơi
                character.resetJumpCount(); // Đặt lại số lần nhảy
                character.setOnGround(true); // Đặt trạng thái đứng trên nền
            }
        } else {
            character.setOnGround(false); // Đặt trạng thái không trên nền
            character.setSpeedY(character.getSpeedY() + GRAVITY); // Áp dụng trọng lực
        }

        // Cập nhật vị trí nhân vật
        character.moveCharacter(currentMap);

        // Cập nhật camera
        camera.update(character.getX(), character.getY());
        // Cập nhật logic quái vật
        monster.setTarget(character.getX(), character.getY());
        monster.move(character.getX(), character.getY());
    }

    private void syncAnimationWithFrameRate() {
        Timeline gameLoop = new Timeline(new KeyFrame(
                Duration.millis(16), // Tương ứng với 60 FPS
                event -> updateGame()
        ));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }


    private boolean isOnPlatform() {
        for (Platform platform : currentMap.getPlatforms()) {
            // Kiểm tra va chạm theo chiều ngang
            boolean collisionX = character.getX() + character.getWidth() > platform.getX() &&
                    character.getX() < platform.getX() + platform.getWidth();

            // Kiểm tra va chạm theo chiều dọc
            boolean collisionY = character.getY() + character.getHeight() >= platform.getY() - 1 &&
                    character.getY() + character.getHeight() <= platform.getY() + 1;

            if (collisionX && collisionY) {
                character.setY(platform.getY() - character.getHeight()); // Đặt lại vị trí đứng trên nền
                return true;
            }
        }
        return false;
    }



    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Game stopped.");
    };

    public static void main(String[] args) {
        launch(args);
    };
};
