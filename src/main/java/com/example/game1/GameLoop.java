package com.example.game1;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
    private Player player;
    private Platform platform;
    private Platform ground;
    private Monster monster;

    public GameLoop(Player player, Platform platform, Platform ground, Monster monster) {
        this.player = player;
        this.platform = platform;
        this.ground = ground;
        this.monster = monster;
    }

    @Override
    public void handle(long now) {
        player.move();  // Di chuyển nhân vật

        // Kiểm tra va chạm với nền tảng giữa
        if (platform.isCollidingWith(player)) {
            player.setVelocityY(0);  // Dừng rơi khi va chạm
            player.setTranslateY(platform.getTranslateY() - player.getHeight());  // Đặt nhân vật ngay trên nền tảng
        }

        // Kiểm tra va chạm với mặt đất
        if (ground.isCollidingWith(player)) {
            player.setVelocityY(0);  // Dừng rơi khi chạm đất
            player.setTranslateY(ground.getTranslateY() - player.getHeight());  // Đặt nhân vật trên mặt đất
        }

        // Di chuyển quái vật
        monster.move();

        // Kiểm tra nếu quái vật chạm vào biên giới (thay đổi hướng khi chạm biên)
        monster.changeDirectionIfEdge(800);

        // Kiểm tra va chạm giữa quái vật và nhân vật
        if (monster.isCollidingWith(player)) {
            System.out.println("Va chạm với quái vật!");
            // Tại đây bạn có thể thêm hành động khi nhân vật va chạm với quái vật, ví dụ: trừ mạng.
        }

        // Nếu không va chạm với nền tảng hoặc mặt đất, áp dụng trọng lực
        if (!platform.isCollidingWith(player) && !ground.isCollidingWith(player)) {
            player.applyGravity();
        }
    }
}
