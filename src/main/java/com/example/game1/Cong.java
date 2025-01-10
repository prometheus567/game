package com.example.game1;

import javafx.scene.image.ImageView;

public class Cong {
    private ImageView sprite;
    private double x, y;
    private Map targetMap;
    private double targetX, targetY;

    public Cong(String imagePath, double x, double y, Map targetMap, double targetX, double targetY) {
        this.sprite = new ImageView(imagePath);
        this.x = x;
        this.y = y;
        this.targetMap = targetMap;
        this.targetX = targetX;
        this.targetY = targetY;

        // Cài đặt vị trí
        sprite.setX(x);
        sprite.setY(y);
    }

    public ImageView getSprite() {
        return sprite;
    }

    public boolean isCharacterColliding(Character character) {
        return sprite.getBoundsInParent().intersects(character.getSprite().getBoundsInParent());
    }

    public Map getTargetMap() {
        return targetMap;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }
}
