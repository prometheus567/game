package com.example.game1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {
    private ImageView sprite; // Ảnh đại diện cho Tile
    private double xPosition, yPosition;

    public Tile(String spritePath, double x, double y) {
        this.sprite = new ImageView();
        // Load ảnh
        this.sprite.setImage(new Image(getClass().getResource(spritePath).toExternalForm()));
        // Đặt kích thước ảnh khớp với ô
        this.sprite.setFitWidth(64);
        this.sprite.setFitHeight(64);

        // Vị trí
        this.xPosition = x;
        this.yPosition = y;
        this.sprite.setTranslateX(x);
        this.sprite.setTranslateY(y);
    }

    public ImageView getSprite() {
        return sprite;
    }
}
