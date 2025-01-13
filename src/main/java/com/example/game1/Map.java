package com.example.game1;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private Pane root; // Pane chứa tất cả các thành phần của map
    private String name; // Tên map để quản lý
    private double width, height;
    private List<Platform> platforms; // Danh sách các nền tảng
    private List<BackgroundLayer> backgrounds;

    public Map(String name, double width, double height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.root = new Pane();
        this.root.setPrefSize(width, height);
        this.platforms = new ArrayList<>();
        this.backgrounds = new ArrayList<>();
    }

    public void addBackground(String imagePath, double posX, double posY, double width, double height) {
        BackgroundLayer layer = new BackgroundLayer(imagePath, posX, posY, width, height);
        backgrounds.add(layer);
        root.getChildren().add(layer.getImageView());
    }

    public Platform checkCollision(double x, double y, double width, double height) {
        for (Platform platform : platforms) {
            // Lấy tọa độ của nền
            double platformLeft = platform.getX();
            double platformRight = platform.getX() + platform.getWidth();
            double platformTop = platform.getY();

            // Kiểm tra va chạm ngang
            boolean collisionX = x + width > platformLeft && x < platformRight;

            // Kiểm tra va chạm dọc (chỉ với sai số nhỏ khi nhân vật đứng trên nền)
            boolean collisionY = y + height >= platformTop - 5 && y + height <= platformTop + 5;

            if (collisionX && collisionY) {
                return platform; // Trả về nền mà nhân vật va chạm
            }
        }
        return null; // Không có va chạm
    }


    public void scrollBackgrounds(double speedX, double speedY) {
        for (int i = 0; i < backgrounds.size(); i++) {
            BackgroundLayer layer = backgrounds.get(i);
            double parallaxSpeedX = speedX / (i + 1);
            double parallaxSpeedY = speedY / (i + 1);
            ImageView imageView = layer.getImageView();
            imageView.setLayoutX(imageView.getLayoutX() - parallaxSpeedX);
            imageView.setLayoutY(imageView.getLayoutY() - parallaxSpeedY);

            if (imageView.getLayoutX() + imageView.getFitWidth() < 0) {
                imageView.setLayoutX(imageView.getLayoutX() + imageView.getFitWidth() * backgrounds.size());
            }
            if (imageView.getLayoutY() + imageView.getFitHeight() < 0) {
                imageView.setLayoutY(imageView.getLayoutY() + imageView.getFitHeight() * backgrounds.size());
            }
        }
    }

    public void addPlatform(Platform platform) {
        root.getChildren().add(platform.getSprite());
        platforms.add(platform);
    }

    public void removePlatform(Platform platform) {
        root.getChildren().remove(platform.getSprite());
        platforms.remove(platform);
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void addObject(ImageView object) {
        root.getChildren().add(object);
    }

    public Pane getRoot() {
        return root;
    }

    public String getName() {
        return name;
    }

    public List<BackgroundLayer> getBackgrounds() {
        return backgrounds;
    }
}
