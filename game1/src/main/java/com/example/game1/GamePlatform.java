package com.example.game1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GamePlatform extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Nhân vật
    private Rectangle player;
    private double playerX = 100, playerY = 500, playerVelocityY = 0;
    private final double gravity = 0.5, jumpPower = -20, moveSpeed = 5, maxSpeed = 7;
    private boolean jumping = false, onGround = true, isMovingLeft = false, isMovingRight = false;

    // Quái vật
    private Rectangle monster;
    private double monsterX = 400, monsterY = 500, monsterVelocityY = 0;
    private boolean monsterJumping = false, monsterOnGround = true, monsterMovingRight = true;

    // Tấn công nhân vật
    private Rectangle attackBox;
    private boolean attackActive = false;
    private long attackStartTime = 0;
    private final long attackDuration = 1000000000; // 1 giây

    // Mặt đất
    private Rectangle ground;

    // Cổng
    private Rectangle portal;

    // Quái vật tấn công
    private Rectangle monsterAttackBox;
    private boolean monsterAttackActive = false;
    private long monsterAttackStartTime = 0;
    private final long monsterAttackDuration = 1000000000; // 1 giây

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.LIGHTBLUE);

        createMap(root);

        scene.setOnKeyPressed(this::handleKeyPress);
        scene.setOnKeyReleased(this::handleKeyRelease);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame(now);
            }
        }.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Platform");
        primaryStage.show();
    }

    private void createMap(Group root) {
        root.getChildren().clear();

        // Mặt đất - màu xanh lá
        ground = new Rectangle(0, HEIGHT - 50, WIDTH, 50);
        ground.setFill(Color.GREEN);
        root.getChildren().add(ground);

        // Nhân vật - màu xanh dương
        player = new Rectangle(50, 50, Color.BLUE);
        player.setX(playerX);
        player.setY(playerY);
        root.getChildren().add(player);

        // Quái vật
        monster = new Rectangle(50, 50, Color.RED);
        monster.setX(monsterX);
        monster.setY(monsterY);
        root.getChildren().add(monster);

        // Đòn tấn công nhân vật
        attackBox = new Rectangle(60, 20, Color.YELLOW);
        attackBox.setVisible(false);
        root.getChildren().add(attackBox);

        // Cổng
        portal = new Rectangle(WIDTH - 100, HEIGHT - 100, 40, 40);
        portal.setFill(Color.PURPLE);
        portal.setOnMouseClicked(event -> changeMap(root));
        root.getChildren().add(portal);

        // Đòn tấn công quái vật
        monsterAttackBox = new Rectangle(60, 20, Color.ORANGE);
        monsterAttackBox.setVisible(false);
        root.getChildren().add(monsterAttackBox);
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE && onGround) {
            jumping = true;
            onGround = false;
            playerVelocityY = jumpPower;  // Khởi tạo lực nhảy
        } else if (event.getCode() == KeyCode.RIGHT) {
            isMovingRight = true;
        } else if (event.getCode() == KeyCode.LEFT) {
            isMovingLeft = true;
        } else if (event.getCode() == KeyCode.ENTER) {
            attackActive = true;
            attackStartTime = System.nanoTime();
            attackBox.setVisible(true);
            attackBox.setX(playerX + 50);  // Vị trí đòn tấn công
            attackBox.setY(playerY + 15);  // Vị trí đòn tấn công
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT) {
            isMovingRight = false;
        } else if (event.getCode() == KeyCode.LEFT) {
            isMovingLeft = false;
        }
    }

    private void updateGame(long now) {
        // Cập nhật vị trí nhân vật nếu có di chuyển
        if (isMovingRight) {
            playerX = Math.min(playerX + moveSpeed, playerX + maxSpeed);
        } else if (isMovingLeft) {
            playerX = Math.max(playerX - moveSpeed, playerX - maxSpeed);
        }

        player.setX(playerX);

        // Cập nhật tình trạng nhảy của nhân vật
        if (jumping) {
            playerVelocityY += gravity;
            playerY += playerVelocityY;

            if (playerY + player.getHeight() >= HEIGHT - 50) {
                playerY = HEIGHT - 50 - player.getHeight();
                jumping = false;
                onGround = true;
                playerVelocityY = 0;
            }
        }

        player.setY(playerY);

        // Cập nhật quái vật
        if (monsterMovingRight) {
            monsterX += 1; // Quái vật di chuyển phải
        } else {
            monsterX -= 1; // Quái vật di chuyển trái
        }

        if (monsterX >= WIDTH - 100 || monsterX <= 0) {
            monsterMovingRight = !monsterMovingRight;  // Đổi hướng di chuyển
        }

        // Kiểm tra đòn tấn công quái vật
        if (monsterAttackActive) {
            long elapsedTime = now - monsterAttackStartTime;
            if (elapsedTime > monsterAttackDuration) {
                monsterAttackActive = false;
                monsterAttackBox.setVisible(false);
            } else {
                monsterAttackBox.setX(monsterX + 50);
                monsterAttackBox.setY(monsterY + 15);
                monsterAttackBox.setVisible(true);
            }
        }

        // Cập nhật quái vật nhảy
        if (monsterJumping) {
            monsterVelocityY += gravity;
            monsterY += monsterVelocityY;

            if (monsterY + monster.getHeight() >= HEIGHT - 50) {
                monsterY = HEIGHT - 50 - monster.getHeight();
                monsterJumping = false;
                monsterOnGround = true;
                monsterVelocityY = 0;
            }
        }

        monster.setX(monsterX);
        monster.setY(monsterY);

        // Xử lý đòn tấn công nhân vật
        if (attackActive) {
            long elapsedTime = now - attackStartTime;
            if (elapsedTime > attackDuration) {
                attackBox.setVisible(false);
                attackActive = false;
            }
        }

        // Kiểm tra va chạm giữa nhân vật và quái vật
        if (player.getBoundsInParent().intersects(monster.getBoundsInParent()) && attackActive) {
            attackActive = false;
            attackBox.setVisible(false);
        }

        // Kiểm tra va chạm cổng chuyển map
        if (player.getBoundsInParent().intersects(portal.getBoundsInParent())) {
            changeMap(new Group());
        }
    }

    private void changeMap(Group root) {
        // Reset lại vị trí của các đối tượng
        playerX = 100;
        playerY = 500;
        monsterX = 400;
        monsterY = 500;

        createMap(root);
    }
}
