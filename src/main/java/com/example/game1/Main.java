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
    private Map currentMap;
    private Character character;
    private Monster monster;
    private Camera camera;
    private Stage primaryStage;
    private Platform platform;
    private Timeline moveTimeline;




    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;


        // Khởi tạo các thành phần
        initializeCharacter();
        initializeMonster();
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

    private void initializeCharacter() {
        // Tạo nhân vật với các frame hoạt ảnh
        String[] walkFramesPaths = {  "/character/walk/frame1.png",
                "/character/walk/frame2.png",
                "/character/walk/frame3.png",
                "/character/walk/frame4.png",
                "/character/walk/frame5.png",
                "/character/walk/frame6.png",
                "/character/walk/frame7.png",
                "/character/walk/frame8.png"};
        String[] jumpFramesPaths = {  "/character/jump/frame1.png",
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
                "/character/jump/frame12.png"};
        String[] idleFramesPaths = { "/character/idle/frame1.png",
                "/character/idle/frame2.png",
                "/character/idle/frame3.png",
                "/character/idle/frame4.png",
                "/character/idle/frame5.png",
                "/character/idle/frame6.png"};
        String[] runFramesPaths = {   "/character/run/frame1.png",
                "/character/run/frame2.png",
                "/character/run/frame3.png",
                "/character/run/frame4.png",
                "/character/run/frame5.png",
                "/character/run/frame6.png",
                "/character/run/frame7.png",
                "/character/run/frame8.png"};
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
        String[] hurtFramesPaths = {     "/character/hurt/frame1.png",
                "/character/hurt/frame2.png",
                "/character/hurt/frame3.png",
                "/character/hurt/frame4.png"};
        String[] shieldFramesPaths = {"/character/shield/frame1.png", "/character/shield/frame2.png"};

        character = new Character(
                walkFramesPaths, jumpFramesPaths, idleFramesPaths, runFramesPaths,
                attackFramesPaths, hurtFramesPaths, shieldFramesPaths, 100, 500
        );
    }

    private void initializeMonster() {
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
        monster = new Monster(monsterWalkFrames, 300.0, 500.0);
    }

    private void initializeMaps() {

        // Tạo map đầu tiên
        currentMap = new Map("Map A", 10000, 900);
        // Thêm nhiều tầng hình nền
        currentMap.addBackground(getClass().getResource("/map/nen.jpg").toExternalForm(), 0, 0, 1600, 800);  // Tầng xa
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 1600, 0, 1600, 800); // Tầng giữa
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 3200, 0, 1600, 800); // Tầng gần
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 4800, 0, 1600, 800); // Tầng gần
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 6400, 0, 1600, 800);
        currentMap.addBackground(getClass().getResource("/map/datt.png").toExternalForm(), 0, 632, 6600, 400);
        // Khởi tạo và thêm nền tảng
        platform = new Platform(getClass().getResource("/map/dat.png").toExternalForm(), 0, 800, 6500, 32);

        currentMap.addPlatform(platform); // Gán platform chính vào biến toàn cục

        // Thêm nền tảng khác
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat.png").toExternalForm(), 800, 400, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat.png").toExternalForm(), 1600, 400, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat.png").toExternalForm(), 3200, 400, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat.png").toExternalForm(), 4800, 400, 300, 50));
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat.png").toExternalForm(), 6000, 400, 300, 50));
        // Thêm nhân vật và quái vật vào map
        currentMap.getRoot().getChildren().add(character.getSprite());
        currentMap.getRoot().getChildren().add(monster.getSprite());

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
                updateGame(); // Gọi cập nhật game
            }
        }.start();
        moveTimeline = new Timeline(new KeyFrame(
                Duration.seconds(0.016), // 60 FPS
                event -> character.moveCharacter(currentMap) // Truyền `currentMap` vào
        ));
        moveTimeline.setCycleCount(Timeline.INDEFINITE); // Lặp vô hạn
        moveTimeline.play(); // Bắt đầu chạy timeline

    }


    private void updateGame() {
        // Cập nhật vị trí camera
        camera.update(character.getX(), character.getY());

        // Cập nhật logic quái vật
        monster.setTarget(character.getX(), character.getY());
        monster.move();


        // Đảm bảo các đối tượng tồn tại trong root của map
        Pane root = currentMap.getRoot();
        if (!root.getChildren().contains(character.getSprite())) {
            root.getChildren().add(character.getSprite());
        }
        if (!root.getChildren().contains(monster.getSprite())) {
            root.getChildren().add(monster.getSprite());
        }
        // Kiểm tra va chạm với nền tảng
        if (isOnPlatform()) {
            character.setSpeedY(0); // Dừng lại khi đứng trên nền tảng
            character.setY(platform.getY() - character.getHeight()); // Đặt lại vị trí nhân vật trên nền tảng
        } else {
            character.setSpeedY(character.getSpeedY() + GRAVITY); // Rơi xuống khi không có nền tảng dưới
        }

        // Cập nhật vị trí nhân vật theo vận tốc Y
        character.setY(character.getY() + character.getSpeedY());

        // Di chuyển nhân vật
        character.moveCharacter(currentMap);

        // Cập nhật logic khác (camera, quái vật, v.v.)
        camera.update(character.getX(), character.getY());
        monster.setTarget(character.getX(), character.getY());
        monster.move();

    }

    private boolean isOnPlatform() {
        for (Platform platform : currentMap.getPlatforms()) {
            // Lấy tọa độ của nền
            int platformTop = (int) platform.getY();
            int platformLeft = (int) platform.getX();
            int platformRight = (int) (platform.getX() + platform.getWidth());

            // Kiểm tra va chạm với cạnh trên của nền
            if (character.getY() + character.getHeight() >= platformTop - 5 && // Cho phép sai số để kiểm tra chính xác
                    character.getY() + character.getHeight() <= platformTop + 5 &&
                    character.getX() + character.getWidth() > platformLeft &&
                    character.getX() < platformRight) {
                // Giữ nhân vật đứng trên nền
                character.setY(platformTop - character.getHeight());
                return true;
            }
        }
        return false;
    }







    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Game stopped.");
    }



    public static void main(String[] args) {
        launch(args);
    }
}
