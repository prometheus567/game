package com.example.game1;

import javafx.scene.image.ImageView;

class Platform {
    private ImageView sprite;

    public Platform(String imagePath, double x, double y, double width, double height) {
        try {
            sprite = new ImageView(imagePath);
            sprite.setFitWidth(width);
            sprite.setFitHeight(height);
            sprite.setLayoutX(x);
            sprite.setLayoutY(y);
        } catch (Exception e) {
            sprite = new ImageView(); // Tạo đối tượng rỗng nếu không tải được hình ảnh
        }
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
        if (sprite == null) {
            sprite = new ImageView(); // Tạo đối tượng rỗng nếu sprite chưa được khởi tạo
        }
        return sprite;
    }

    // Kiểm tra va chạm với một đối tượng khác
    public boolean isColliding(double x, double y, double width, double height) {
        boolean collisionX = x + width > getX() && x < getX() + getWidth();
        boolean collisionY = y + height > getY() && y < getY() + getHeight();
        return collisionX && collisionY;
    }

    // Di chuyển nền tảng
    public void move(double deltaX, double deltaY) {
        sprite.setLayoutX(sprite.getLayoutX() + deltaX);
        sprite.setLayoutY(sprite.getLayoutY() + deltaY);
    }

    // Kiểm tra nếu nền tảng nằm ngoài màn hình
    public boolean isOutOfScreen(double screenWidth, double screenHeight) {
        return getX() + getWidth() < 0 || getY() + getHeight() < 0 || getX() > screenWidth || getY() > screenHeight;
    }
}
