package com.example.game1;
import java.util.ArrayList;
import java.util.List;

public class Maplol {
    private List<Platform> platforms = new ArrayList<>();

    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }
    public Platform checkCollision(double x, double y, double width, double height) {
        for (Platform platform : platforms) {
            double platformLeft = platform.getX();
            double platformRight = platform.getX() + platform.getWidth();
            double platformTop = platform.getY();
            double platformBottom = platform.getY() + platform.getHeight();

            // Kiểm tra va chạm ngang và dọc
            boolean collisionX = x + width > platformLeft && x < platformRight;
            boolean collisionY = y > platformTop && y < platformBottom;

            if (collisionX && collisionY) {
                return platform; // Trả về nền tảng va chạm
            }
        }
        return null; // Không có va chạm
    }

}