package com.example.game1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BackgroundLayer {
    private ImageView imageView;

    public BackgroundLayer(String imagePath, double posX, double posY, double width, double height) {
        Image image = new Image(imagePath);
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(posX);
        imageView.setLayoutY(posY);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
