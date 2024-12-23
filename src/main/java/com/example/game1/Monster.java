package com.example.game1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Monster {

    private ImageView sprite;
    private double xPosition, yPosition, speedX;

    public Monster(double x, double y) {
        sprite = new ImageView(new Image(getClass().getResource("/monster.png").toExternalForm()));
        sprite.setFitWidth(100);
        sprite.setFitHeight(100);
        xPosition = x;
        yPosition = y;
        speedX = 2;
    }

    public void move() {
        xPosition += speedX;
        if (xPosition <= 0 || xPosition >= 800 - sprite.getFitWidth()) {
            speedX *= -1;  // Reverse direction if reaches edge
        }
        sprite.setTranslateX(xPosition);
    }

    public void changeDirectionIfEdge(double maxWidth) {
        if (xPosition <= 0 || xPosition >= maxWidth - sprite.getFitWidth()) {
            speedX *= -1;
        }
    }

    public ImageView getSprite() {
        return sprite;
    }
}
