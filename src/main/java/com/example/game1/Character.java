package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Character {

    private ImageView sprite;
    private double speedX, speedY, xPosition, yPosition;
    private boolean onGround = true, isFalling = false;
    private double gravity = 0.5;
    private int maxJumpCount = 2;
    private Timeline walkTimeline, jumpTimeline, idleTimeline, runTimeline;

    public Character(double initialX, double initialY) {
        sprite = new ImageView();
        sprite.setFitWidth(100);
        sprite.setFitHeight(100);
        xPosition = initialX;
        yPosition = initialY;
        speedX = 0;
        speedY = 0;
        loadAnimations();
    }

    private void loadAnimations() {
        // load animation frames here
        walkTimeline = new Timeline();
        walkTimeline.setCycleCount(Timeline.INDEFINITE);
        // Set up keyframes and frame changes for walk and jump
    }

    public void move() {
        xPosition += speedX;
        yPosition += speedY;

        if (isFalling) {
            speedY += gravity; // Apply gravity
        }

        sprite.setTranslateX(xPosition);
        sprite.setTranslateY(yPosition);
    }

    public void jump() {
        if (onGround) {
            speedY = -10;  // Jump force
            isFalling = true;
        }
    }

    public void land() {
        speedY = 0;
        isFalling = false;
        onGround = true;
    }

    public void attack() {
        // Logic to attack (activate attack animation)
    }

    public ImageView getSprite() {
        return sprite;
    }

    // Setters and getters for translateX, speedX, etc.
    public void setTranslateX(double x) {
        this.xPosition = x;
    }

    public double getTranslateX() {
        return xPosition;
    }

    public void setTranslateY(double y) {
        this.yPosition = y;
    }

    public double getTranslateY() {
        return yPosition;  // Cần thêm phương thức này để trả về vị trí Y của nhân vật.
    }

    public void setSpeedX(double speed) {
        this.speedX = speed;
    }

    public void setFalling(boolean falling) {
        this.isFalling = falling;
    }
}
