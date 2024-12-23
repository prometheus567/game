package com.example.game1;

import javafx.scene.layout.Pane;

public class Camera {
    private double width, height;
    private Pane root;

    public Camera(double width, double height, Pane root) {
        this.width = width;
        this.height = height;
        this.root = root;
    }

    public void update(double characterX, double characterY) {
        double cameraX = Math.max(0, Math.min(characterX - width / 2, 1000)); // Giới hạn camera
        double cameraY = Math.max(0, Math.min(characterY - height / 2, 500));

        root.setLayoutX(-cameraX);
        root.setLayoutY(-cameraY);
    }
}
