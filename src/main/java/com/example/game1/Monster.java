package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class Monster {
    private ImageView sprite;
    private double speedX;
    private double speedY;
    private double xPosition;
    private double yPosition;

    private boolean onGround = true;
    private double gravity = 0.5;
    private double walkSpeed = 1;

    private Timeline moveTimeline;

    // Tham chiếu đến vị trí nhân vật (để AI có thể đuổi theo)
    private double targetX;
    private double targetY;

    public Monster(String[] walkFramesPaths, double initialX, double initialY) {
        this.sprite = new ImageView();
        this.sprite.setFitWidth(100);
        this.sprite.setFitHeight(100);
        this.xPosition = initialX;
        this.yPosition = initialY;

        this.speedX = 0;
        this.speedY = 0;

        Image[] walkFrames = new Image[walkFramesPaths.length];
        for (int i = 0; i < walkFramesPaths.length; i++) {
            walkFrames[i] = new Image(getClass().getResource(walkFramesPaths[i]).toExternalForm());
        }

        moveTimeline = new Timeline();
        moveTimeline.setCycleCount(Timeline.INDEFINITE);

        for (int i = 0; i < walkFrames.length; i++) {
            final int index = i;
            moveTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i),
                    event -> sprite.setImage(walkFrames[index])
            ));
        }

        moveTimeline.play();
    }

    public void moveMonster() {
        // Di chuyển quái vật theo logic AI
        double distanceToTarget = targetX - xPosition;

        if (Math.abs(distanceToTarget) > 5) { // Nếu không quá gần nhân vật
            speedX = walkSpeed * Math.signum(distanceToTarget);
        } else {
            speedX = 0; // Đứng yên nếu đã gần nhân vật
        }

        xPosition += speedX;

        if (!onGround) {
            speedY += gravity;
        }

        yPosition += speedY;

        if (yPosition >= 500) {
            yPosition = 500;
            onGround = true;
            speedY = 0;
        }

        sprite.setTranslateX(xPosition);
        sprite.setTranslateY(yPosition);
    }

    public void setTarget(double x, double y) {
        this.targetX = x;
        this.targetY = y;
    }

    public void move() {
        moveMonster();
    }

    public ImageView getSprite() {
        return sprite;
    }
}
