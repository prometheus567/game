package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Monster {
    private ImageView sprite; // Sprite for monster
    private double speedX; // Horizontal speed
    private double speedY; // Vertical speed
    private double xPosition; // Horizontal position
    private double yPosition; // Vertical position

    private boolean onGround = true; // Checks if monster is on the ground
    private double gravity = 0.5; // Gravity
    private double walkSpeed = 1; // Monster walking speed

    private Timeline moveTimeline; // Timeline for monster's movement

    // Constructor
    public Monster(String[] walkFramesPaths, double initialX, double initialY) {
        this.sprite = new ImageView();
        this.sprite.setFitWidth(100); // Set monster size
        this.sprite.setFitHeight(100);
        this.xPosition = initialX;
        this.yPosition = initialY;

        this.speedX = 0;
        this.speedY = 0;

        // Load walk animation frames
        Image[] walkFrames = new Image[walkFramesPaths.length];
        for (int i = 0; i < walkFramesPaths.length; i++) {
            walkFrames[i] = new Image(getClass().getResource(walkFramesPaths[i]).toExternalForm());
        }

        // Set up walk timeline
        moveTimeline = new Timeline();
        moveTimeline.setCycleCount(Timeline.INDEFINITE);

        // Add keyframes for walk animation
        for (int i = 0; i < walkFrames.length; i++) {
            final int index = i;
            moveTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i), // Timing for each frame
                    event -> sprite.setImage(walkFrames[index]) // Set corresponding walk frame
            ));
        }

        // Play walking animation and movement timeline
        moveTimeline.play();
    }

    public void moveMonster() {
        // Move monster along X
        xPosition += speedX;

        if (!onGround) {
            speedY += gravity; // Apply gravity if not on the ground
        }

        // Move monster along Y
        yPosition += speedY;

        if (yPosition >= 500) { // If the monster hits the ground
            yPosition = 500;
            onGround = true;
            speedY = 0;
        }

        // Set position for sprite
        sprite.setTranslateX(xPosition);
        sprite.setTranslateY(yPosition);
    }

    // Set horizontal movement speed
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    // Get sprite to add to scene
    public ImageView getSprite() {
        return sprite;
    }

    // Call moveMonster to actually move the monster in each game loop
    public void move() {
        moveMonster();  // Move the monster
    }
}
