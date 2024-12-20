package com.example.game1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private final double gravity = 0.5, jumpPower = -20, moveSpeed = 5;
    private boolean jumping = false, onGround = true, isMovingLeft = false, isMovingRight = false;

    // Quái vật
    private Rectangle monster;
    private double monsterX = 400, monsterY = 500, monsterVelocityY = 0;
    private boolean monsterJumping = false, monsterOnGround = true, monsterMovingRight = true;
    private Rectangle monsterAttackBox;
    private boolean monsterAttackActive = false;
    private long monsterAttackStartTime;
    private final long monsterAttackDuration = 1500000000; // 1.5 giây

    // Đòn tấn công nhân vật
    private Rectangle attackBox;
    private boolean attackActive = false;
    private long attackStartTime = 0;
    private final long attackDuration = 1000000000; // 1 giây

    // Mặt đất và cổng
    private Rectangle ground;
    private Rectangle portal;

    // Thanh máu
    private int playerHealth = 100;
    private int monsterHealth = 100;
    private Rectangle playerHealthBar;
    private Rectangle monsterHealthBar;
    private final int MAX_HEALTH = 100;

    // Vòng lặp trò chơi
    private AnimationTimer gameLoop;
    private int currentMap = 1;

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

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame(now);
            }
        };
        gameLoop.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Platform");
        primaryStage.show();
    }

    private void createMap(Group root) {
        root.getChildren().clear();

        // Mặt đất
        ground = new Rectangle(0, HEIGHT - 50, WIDTH, 50);
        ground.setFill(Color.GREEN);
        root.getChildren().add(ground);

        // Nhân vật
        player = new Rectangle(50, 50, Color.BLUE);
        player.setX(playerX);
        player.setY(playerY);
        root.getChildren().add(player);

        // Quái vật
        monster = new Rectangle(50, 50, Color.RED);
        monster.setX(monsterX);
        monster.setY(monsterY);
        root.getChildren().add(monster);

        // Thanh máu nhân vật
        playerHealthBar = new Rectangle(150, 20, Color.GREEN);
        playerHealthBar.setX(10);
        playerHealthBar.setY(10);
        root.getChildren().add(playerHealthBar);

        // Thanh máu quái vật
        monsterHealthBar = new Rectangle(150, 20, Color.RED);
        monsterHealthBar.setX(WIDTH - 170);
        monsterHealthBar.setY(10);
        root.getChildren().add(monsterHealthBar);

        // Đòn tấn công nhân vật
        attackBox = new Rectangle(60, 20, Color.YELLOW);
        attackBox.setVisible(false);
        root.getChildren().add(attackBox);

        // Đòn tấn công quái vật
        monsterAttackBox = new Rectangle(60, 20, Color.ORANGE);
        monsterAttackBox.setVisible(false);
        root.getChildren().add(monsterAttackBox);

        // Cổng
        portal = new Rectangle(WIDTH - 100, HEIGHT - 100, 40, 40);
        portal.setFill(Color.PURPLE);
        root.getChildren().add(portal);
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE && onGround) {
            jumping = true;
            onGround = false;
            playerVelocityY = jumpPower;
        } else if (event.getCode() == KeyCode.D) {
            isMovingRight = true;
        } else if (event.getCode() == KeyCode.A) {
            isMovingLeft = true;
        } else if (event.getCode() == KeyCode.F) {
            attackActive = true;
            attackStartTime = System.nanoTime();
            attackBox.setVisible(true);
            attackBox.setX(playerX + 50);
            attackBox.setY(playerY + 15);
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
        // Di chuyển nhân vật
        if (isMovingRight) {
            playerX += moveSpeed;
        }
        if (isMovingLeft) {
            playerX -= moveSpeed;
        }
        player.setX(playerX);

        // Kiểm tra trạng thái nhảy
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

        // Kiểm tra va chạm với cổng
        if (player.getBoundsInParent().intersects(portal.getBoundsInParent())) {
            System.out.println("Chạm cổng, chuyển map...");
            loadNextMap(); // Gọi chuyển map khi va chạm
        }

        // Di chuyển quái vật và hành vi AI
        if (playerX > monsterX) {
            monsterMovingRight = true;
        } else {
            monsterMovingRight = false;
        }

        if (monsterMovingRight) {
            monsterX++;
        } else {
            monsterX--;
        }

        if (Math.abs(playerX - monsterX) < 200 && monsterOnGround) {
            monsterVelocityY = -15;
            monsterJumping = true;
            monsterOnGround = false;
        }

        monster.setX(monsterX);

        // Hành vi tấn công của quái vật
        handleMonsterAttack(now);

        // Kiểm tra đòn tấn công
        if (attackActive) {
            if (now - attackStartTime > attackDuration) {
                attackActive = false;
                attackBox.setVisible(false);
            }
            if (attackBox.getBoundsInParent().intersects(monster.getBoundsInParent())) {
                monsterHealth -= 10;
                updateHealthBar(monsterHealthBar, monsterHealth);
                attackActive = false;
                addHitEffect(monster);
            }
        }

        // Kiểm tra máu
        if (playerHealth <= 0) {
            endGame("Game Over! You Lose!");
        } else if (monsterHealth <= 0) {
            endGame("Victory! You Win!");
        }


    }

    private void loadNextMap() {
        currentMap++;
        if (currentMap > 2) { // Giới hạn số map (tạm ví dụ 2 map)
            currentMap = 1; // Quay lại map đầu
        }

        // Reset vị trí nhân vật và quái vật
        playerX = 100;
        playerY = 500;
        monsterX = 400;
        monsterY = 500;

        System.out.println("Chuyển sang map " + currentMap);

        // Tạo lại map
        createMap((Group) player.getParent());
    }


    private void handleMonsterAttack(long now) {
        if (!monsterAttackActive) {
            monsterAttackActive = true;
            monsterAttackStartTime = now;
            monsterAttackBox.setVisible(true);
            monsterAttackBox.setX(monsterX - 10);
            monsterAttackBox.setY(monsterY + 15);
        } else if (now - monsterAttackStartTime > monsterAttackDuration) {
            monsterAttackActive = false;
            monsterAttackBox.setVisible(false);
        } else if (monsterAttackBox.getBoundsInParent().intersects(player.getBoundsInParent())) {
            playerHealth -= 1;
            updateHealthBar(playerHealthBar, playerHealth);
            addHitEffect(player);
        }
    }

    private void updateHealthBar(Rectangle healthBar, int health) {
        double healthRatio = Math.max(0, (double) health / MAX_HEALTH);
        healthBar.setWidth(150 * healthRatio);
        healthBar.setFill(healthRatio > 0.5 ? Color.GREEN : (healthRatio > 0.2 ? Color.ORANGE : Color.RED));
    }

    private void addHitEffect(Rectangle target) {
        target.setFill(Color.YELLOW);
        new AnimationTimer() {
            private long effectStart = System.nanoTime();

            @Override
            public void handle(long now) {
                if (now - effectStart > 200_000_000) {
                    target.setFill(target == player ? Color.BLUE : Color.RED);
                    stop();
                }
            }
        }.start();
    }

    private void endGame(String message) {
        gameLoop.stop();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        playerHealth = MAX_HEALTH;
        monsterHealth = MAX_HEALTH;
        createMap((Group) player.getParent());
        gameLoop.start();
    }
}
