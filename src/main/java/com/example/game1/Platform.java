package com.example.game1;

import javafx.scene.image.ImageView;

class Platform {
    private ImageView sprite;

    public Platform(String imagePath, double x, double y, double width, double height) {
        sprite = new ImageView(imagePath);
        sprite.setFitWidth(width);
        sprite.setFitHeight(height);
        sprite.setLayoutX(x);
        sprite.setLayoutY(y);
    }

    public double getX() {
        return sprite.getLayoutX();
    }

    public double getY() {
        return sprite.getLayoutY();
    }

    public double getWidth() {
        return sprite.getFitWidth();
    }

    public double getHeight() {
        return sprite.getFitHeight();
    }

    public ImageView getSprite() {
        return sprite;
    }
}

