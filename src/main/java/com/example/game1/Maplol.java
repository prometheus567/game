package com.example.game1;

import java.util.ArrayList;
import java.util.List;

public class Maplol {
    private List<Platform> platforms = new ArrayList<>();

    // Thêm nền tảng
    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    // Lấy danh sách nền tảng
    public List<Platform> getPlatforms() {
        return platforms;
    }

    // Xóa nền tảng
    public void removePlatform(Platform platform) {
        platforms.remove(platform);
    }

    // Kiểm tra va chạm
    public Platform checkCollision(double x, double y, double width, double height) {
        for (Platform platform : platforms) {
            double platformLeft = platform.getX();
            double platformRight = platform.getX() + platform.getWidth();
            double platformTop = platform.getY();
            double platformBottom = platform.getY() + platform.getHeight();

            // Kiểm tra va chạm theo chiều ngang
            boolean collisionX = x + width > platformLeft && x < platformRight;
            // Kiểm tra va chạm theo chiều dọc
            boolean collisionY = y + height > platformTop && y < platformBottom;

            if (collisionX && collisionY) {
                return platform; // Trả về nền tảng va chạm
            }
        }
        return null; // Không có va chạm
    }

    // Kiểm tra nếu nền tảng nằm trong màn hình
    public boolean isPlatformVisible(Platform platform, double screenWidth, double screenHeight) {
        double platformRight = platform.getX() + platform.getWidth();
        double platformBottom = platform.getY() + platform.getHeight();

        return platformRight > 0 && platform.getX() < screenWidth &&
                platformBottom > 0 && platform.getY() < screenHeight;
    }
}
