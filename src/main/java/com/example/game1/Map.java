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
        this.platforms = new ArrayList<>(); // Khởi tạo danh sách nền tảng
        this.backgrounds = new ArrayList<>();
    }

    // Thêm nền (background) với layer cụ thể
    public void addBackground(String imagePath, double posX, double posY, double width, double height) {
        BackgroundLayer layer = new BackgroundLayer(imagePath, posX, posY, width, height);
        backgrounds.add(layer);
        root.getChildren().add(layer.getImageView()); // Thêm vào root
    }
    public Platform checkCollision(double x, double y, double width, double height) {
        for (Platform platform : platforms) {
            double platformLeft = platform.getX();
            double platformRight = platform.getX() + platform.getWidth();
            double platformTop = platform.getY();
            double platformBottom = platform.getY() + platform.getHeight();

            // Kiểm tra va chạm ngang và dọc
            boolean collisionX = x + width > platformLeft && x < platformRight;
            boolean collisionY = y + height > platformTop && y < platformBottom;

            if (collisionX && collisionY) {
                return platform; // Trả về nền tảng va chạm
            }
        }
        return null; // Không có va chạm
    }


    // Cuộn nền để tạo hiệu ứng parallax
    public void scrollBackgrounds(double speed) {
        for (int i = 0; i < backgrounds.size(); i++) {
            BackgroundLayer layer = backgrounds.get(i);
            double parallaxSpeed = speed / (i + 1); // Tầng càng xa cuộn càng chậm
            ImageView imageView = layer.getImageView();
            imageView.setLayoutX(imageView.getLayoutX() - parallaxSpeed);

            // Lặp lại background nếu cuộn ra khỏi màn hình
            if (imageView.getLayoutX() + imageView.getFitWidth() < 0) {
                imageView.setLayoutX(imageView.getLayoutX() + imageView.getFitWidth() * backgrounds.size());
            }
        }
    }

    // Thêm nền tảng (platform)
    public void addPlatform(Platform platform) {
        root.getChildren().add(platform.getSprite());
        platforms.add(platform); // Lưu nền tảng vào danh sách
    }

    public List<Platform> getPlatforms() {
        return platforms; // Trả về danh sách các nền tảng
    }

    // Thêm đối tượng (ví dụ: cổng, chướng ngại vật)
    public void addObject(ImageView object) {
        root.getChildren().add(object);
    }

    public Pane getRoot() {
        return root;
    }

    public String getName() {
        return name;
    }

    // Trả về danh sách background
    public List<BackgroundLayer> getBackgrounds() {
        return backgrounds;
    }
}
