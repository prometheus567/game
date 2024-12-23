package com.example.game1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {
    private ImageView sprite; // Tile của bản đồ
    private double xPosition, yPosition;

    public Tile(String spritePath, double x, double y) {
        // Kiểm tra sự tồn tại của tài nguyên
        if (getClass().getResource(spritePath) == null) {
            throw new IllegalArgumentException("Tài nguyên không tồn tại: " + spritePath);
        }
        this.sprite = new ImageView();
        this.sprite.setImage(new Image(getClass().getResource(spritePath).toExternalForm()));
        this.xPosition = x;
        this.yPosition = y;
        this.sprite.setTranslateX(x);
        this.sprite.setTranslateY(y);
    }

    public ImageView getSprite() {
        return sprite;
    }
}
