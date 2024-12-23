package com.example.game1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform extends Rectangle {

    public Platform(double x, double y, double width, double height) {
        super(x, y, width, height);
        setFill(Color.GRAY); // or any color you like
    }
}
