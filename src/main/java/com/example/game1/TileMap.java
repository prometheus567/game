package com.example.game1;

import javafx.scene.layout.Pane;
import java.util.HashMap;

public class TileMap { // Đổi tên từ Map -> TileMap để tránh nhầm lẫn
    private Tile[][] tiles; // Mảng lưu trữ Tile của bản đồ
    private HashMap<Integer, String> tilePaths; // Mã Tile -> đường dẫn ảnh

    public TileMap(int[][] mapData, Pane root) {
        int width = mapData.length;
        int height = mapData[0].length;

        tiles = new Tile[width][height];
        tilePaths = new HashMap<>();

        // Map mã tile -> ảnh
        tilePaths.put(0, "/tiles/dat.png");
        tilePaths.put(1, "/tiles/nuoc.png");
        tilePaths.put(2, "/tiles/troi.png");

        // Tạo tiles từ mapData
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int tileType = mapData[x][y];
                String tilePath = tilePaths.getOrDefault(tileType, "/character/background/bg2.png");

                try {
                    // Tạo Tile với tọa độ và thêm vào root
                    tiles[x][y] = new Tile(tilePath, y * 64, x * 64);
                    root.getChildren().add(tiles[x][y].getSprite());
                } catch (Exception e) {
                    System.err.println("Không thể tải tile tại (" + x + "," + y + "): " + e.getMessage());
                }
            }
        }
    }
}
