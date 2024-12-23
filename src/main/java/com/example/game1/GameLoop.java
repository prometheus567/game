package com.example.game1;


import javafx.animation.AnimationTimer;

// GameLoop để cập nhật trạng thái của game liên tục
class GameLoop extends AnimationTimer {

    private Character character;
    private Platform ground;
    private Platform platform;
    private Monster monster;

    public GameLoop(Character character, Platform platform, Platform ground, Monster monster) {
        this.character = character;
        this.platform = platform;
        this.ground = ground;
        this.monster = monster;
    }

    @Override
    public void handle(long now) {
        // Cập nhật chuyển động của nhân vật
        character.move();

        // Kiểm tra va chạm với nền tảng đất
        if (character.getSprite().getBoundsInParent().intersects(ground.getBoundsInParent())) {
            if (character.getTranslateY() + character.getSprite().getFitHeight() >= ground.getTranslateY()) {
                character.setTranslateY(ground.getTranslateY() - character.getSprite().getFitHeight());
                character.land();
            }
        } else {
            character.setFalling(true);
        }

        // Kiểm tra va chạm với platform
        if (character.getSprite().getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (character.getTranslateY() + character.getSprite().getFitHeight() <= platform.getTranslateY() + platform.getHeight()) {
                character.setTranslateY(platform.getTranslateY() - character.getSprite().getFitHeight());
                character.land();
            }
        }

        // Ngăn nhân vật đi ra ngoài biên giới màn chơi
        if (character.getTranslateX() < 0) {
            character.setTranslateX(0);
        } else if (character.getTranslateX() > 800 - character.getSprite().getFitWidth()) {
            character.setTranslateX(800 - character.getSprite().getFitWidth());
        }

        // Cập nhật logic của quái vật
        monster.move();
        monster.changeDirectionIfEdge(800);
    }
}