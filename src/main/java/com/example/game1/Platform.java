package com.example.game1;

import javafx.scene.image.ImageView;

public class Platform {
    private ImageView sprite;
    private double x, y, width, height;

    public Platform(String imagePath, double x, double y, double width, double height) {
        this.sprite = new ImageView(imagePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Cài đặt kích thước và vị trí
        sprite.setFitWidth(width);
        sprite.setFitHeight(height);
        sprite.setX(x);
        sprite.setY(y);
    }

    public ImageView getSprite() {
        return sprite;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
