package com.example.game1;

import javafx.scene.layout.Pane;

public class Map {
    private Tile[][] tiles; // Mảng lưu trữ các tile của bản đồ

    public Map(int width, int height, Pane root) {
        tiles = new Tile[width][height];

        // Khởi tạo bản đồ với các tiles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String tilePath = "/character/background/bg2.png"; // Đường dẫn đến hình ảnh tile
                tiles[x][y] = new Tile(tilePath, x * 64, y * 64); // Tạo Tile 64x64 pixels
                root.getChildren().add(tiles[x][y].getSprite()); // Thêm vào màn hình
            }
        }
    }
}
