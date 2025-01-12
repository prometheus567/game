package com.example.game1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static com.example.game1.Character.GRAVITY;

public class Main extends Application {
    private Map currentMap;
    private Character character;
    private Monster monster;
    private Camera camera;
    private Stage primaryStage;
    private Platform platform;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage; // Gán giá trị cho biến toàn cục

        // Tải giao diện login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene loginScene = new Scene(loader.load());

        // Lấy controller của login.fxml
        LoginController loginController = loader.getController();

        // Truyền Stage vào LoginController để chuyển giao diện sau này
        loginController.setPrimaryStage(this.primaryStage);
        loginController.setMainApp(this);

        // Hiển thị giao diện đăng nhập
        this.primaryStage.setTitle("Login");
        this.primaryStage.setScene(loginScene);
        this.primaryStage.show();
    }

    public void showGameScreen() {
        try {
            // Kiểm tra xem primaryStage có bị null không
            if (this.primaryStage == null) {
                System.err.println("primaryStage is null in showGameScreen().");
                return;
            }

            // Khởi tạo các thành phần game
            initializeCharacter();
            initializeMonster();
            initializeMaps();

            // Thiết lập camera
            camera = new Camera(600, 600, currentMap.getRoot());

            // Khởi tạo Scene với map đầu tiên
            Scene gameScene = new Scene(currentMap.getRoot(), 800, 600);

            // Khởi tạo thanh máu và thanh shield
            initializeHealthBar();

            // Thiết lập điều khiển bàn phím
            setupKeyControls(gameScene);

            // Thiết lập Stage và hiển thị giao diện game
            primaryStage.setTitle("Game Map Example");
            primaryStage.setScene(gameScene);
            primaryStage.show();

            // Bắt đầu game loop
            startGameLoop();
        } catch (Exception e) {
            System.err.println("Error while showing game screen:");
            e.printStackTrace();
        }
    }


    private void initializeCharacter() {
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
                attackFramesPaths, hurtFramesPaths, shieldFramesPaths, 100, 500
        );
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


    private void initializeMaps() {
        // Tạo map đầu tiên
        currentMap = new Map("Map A", 10000, 900);
        // Thêm nhiều tầng hình nền
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 1600, 0, 1600, 1000);  // Tầng xa
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 3200, 0, 1600, 1000); // Tầng giữa
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 0, 0, 1600, 1000); // Tầng gần
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 4800, 0, 1600, 1000); // Tầng gần
        currentMap.addBackground(getClass().getResource("/map/Background.png").toExternalForm(), 4800, 0, 1600, 1000); // Tầng gần
        // Khởi tạo và thêm nền tảng
        platform = new Platform(getClass().getResource("/map/dat.png").toExternalForm(), 0, 600, 1600, 100);
        currentMap.addPlatform(platform); // Gán platform chính vào biến toàn cục

        // Thêm nền tảng khác
        currentMap.addPlatform(new Platform(getClass().getResource("/map/dat2.jpg").toExternalForm(), 800, 400, 300, 50));

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
            };
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A, D -> character.setSpeedX(0);  // Dừng lại
                case S -> character.stopShielding(); // Dừng shield
                case SHIFT -> character.setSpeedX(0); // Dừng chạy nhanh
            };
        });
    };

    private void startGameLoop() {
        new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            };
        }.start();
    };

    private void updateGame() {
        // Cập nhật vị trí camera
        camera.update(character.getX(), character.getY());

        // Cập nhật logic quái vật
        monster.setTarget(character.getX(), character.getY());
        monster.move(character.getX(), character.getY()); // Truyền vào vị trí của nhân vật

        // Đảm bảo các đối tượng tồn tại trong root của map
        Pane root = currentMap.getRoot();
        if (!root.getChildren().contains(character.getSprite())) {
            root.getChildren().add(character.getSprite());
        };
        if (!root.getChildren().contains(monster.getSprite())) {
            root.getChildren().add(monster.getSprite());
        };

        // Kiểm tra va chạm với nền tảng
        if (isOnPlatform()) {
            character.setSpeedY(0); // Dừng lại khi đứng trên nền tảng
            character.setY(platform.getY() - character.getHeight()); // Đặt lại vị trí nhân vật trên nền tảng
        } else {
            character.setSpeedY(character.getSpeedY() + GRAVITY); // Rơi xuống khi không có nền tảng dưới
        };

        // Cập nhật vị trí nhân vật theo vận tốc Y
        character.setY(character.getY() + character.getSpeedY());
    };

    private boolean isOnPlatform() {
        for (Platform platform : currentMap.getPlatforms()) {
            if (character.getY() + character.getHeight() <= platform.getY() &&
                    character.getY() + character.getHeight() >= platform.getY() - 5 && // Tăng độ chính xác kiểm tra
                    character.getX() + character.getWidth() > platform.getX() &&
                    character.getX() < platform.getX() + platform.getWidth()) {
                return true;
            };
        };
        return false;
    };

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Game stopped.");
    };

    public static void main(String[] args) {
        launch(args);
    };
};
