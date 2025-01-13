package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class MonsterNew {
        private ImageView sprite; // Hiển thị monster
        private double x; // Vị trí x
        private double y; // Vị trí y
        private double speed; // Tốc độ di chuyển
        private int health; // Máu của monster
        private boolean isAlive = true; // Trạng thái sống/chết

        // Các hoạt ảnh
        private Image[] walkFrames; // Các frame cho di chuyển
        private Image[] attackFrames; // Các frame cho tấn công
        private Image[] hurtFrames; // Các frame khi bị tấn công
        private Image[] deadFrames; // Các frame khi chết

        private Timeline animationTimeline; // Quản lý hoạt ảnh
        private int frameIndex = 0; // Theo dõi frame hiện tại

        public MonsterNew(String[] walkPaths, String[] attackPaths, String[] hurtPaths, String[] deadPaths, double x, double y, double speed, int health) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.health = health;

            // Khởi tạo ImageView cho monster
            this.sprite = new ImageView();
            this.sprite.setTranslateX(x);
            this.sprite.setTranslateY(y);

            // Load frames cho các hoạt ảnh
            this.walkFrames = loadFrames(walkPaths);
            this.attackFrames = loadFrames(attackPaths);
            this.hurtFrames = loadFrames(hurtPaths);
            this.deadFrames = loadFrames(deadPaths);

            // Mặc định bắt đầu với hoạt ảnh di chuyển
            playAnimation(walkFrames, 0.2);
        }

        // Phương thức load hình ảnh từ đường dẫn
        private Image[] loadFrames(String[] paths) {
            Image[] frames = new Image[paths.length];
            for (int i = 0; i < paths.length; i++) {
                frames[i] = new Image(getClass().getResource(paths[i]).toExternalForm());
            }
            return frames;
        }

        // Phát hoạt ảnh
        private void playAnimation(Image[] frames, double durationPerFrame) {
            if (animationTimeline != null) {
                animationTimeline.stop();
            }
            frameIndex = 0;
            animationTimeline = new Timeline(new KeyFrame(
                    Duration.seconds(durationPerFrame),
                    event -> {
                        sprite.setImage(frames[frameIndex]);
                        frameIndex = (frameIndex + 1) % frames.length;
                    }
            ));
            animationTimeline.setCycleCount(Timeline.INDEFINITE);
            animationTimeline.play();
        }

        // Di chuyển monster
        public void walk() {
            if (!isAlive) return;

            x += speed;
            sprite.setTranslateX(x);
            playAnimation(walkFrames, 0.2);
        }

        // Monster tấn công
        public void attack() {
            if (!isAlive) return;

            playAnimation(attackFrames, 0.1);
        }

        // Nhận sát thương
        public void takeDamage(int damage) {
            if (!isAlive) return;

            health -= damage;
            if (health <= 0) {
                die();
            } else {
                playAnimation(hurtFrames, 0.1);
            }
        }

        // Monster chết
        public void die() {
            if (!isAlive) return;

            isAlive = false;
            playAnimation(deadFrames, 0.2);
            animationTimeline.setOnFinished(event -> sprite.setVisible(false)); // Ẩn khi hoạt ảnh kết thúc
        }

        // Getter cho ImageView để hiển thị monster trong giao diện
        public ImageView getSprite() {
            return sprite;
        }

        public boolean isAlive() {
            return isAlive;
        }

        public int getHealth() {
            return health;
        }
    }

