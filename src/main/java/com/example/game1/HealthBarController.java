package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;

public class HealthBarController {

    @FXML
    private ImageView greenbar; // Thanh máu

    @FXML
    private ImageView shieldbar; // Thanh shield

    private Rectangle shieldClip; // Clip để cố định vùng hiển thị của thanh shield
    private double maxShieldWidth; // Độ rộng tối đa của thanh shield
    private double currentShieldWidth; // Độ rộng hiện tại của thanh shield
    private double shieldRegenRate = 5; // Tốc độ hồi shield (pixels/giây)
    private double shieldDecreaseAmount = 30; // Lượng giảm shield mỗi lần bị đánh
    private Timeline shieldRegenTimeline; // Timeline để hồi shield
    private boolean isShieldRegenActive = false; // Kiểm tra trạng thái đang hồi shield

    @FXML
    private void initialize() {
        // Khởi tạo giá trị cho thanh shield
        maxShieldWidth = shieldbar.getFitWidth();
        currentShieldWidth = maxShieldWidth;

        // Khởi tạo clip cho thanh shield để giữ nguyên layout
        shieldClip = new Rectangle(maxShieldWidth, shieldbar.getFitHeight());
        shieldbar.setClip(shieldClip); // Đặt clip để điều chỉnh vùng hiển thị

        // Khởi tạo Timeline để hồi shield
        shieldRegenTimeline = new Timeline(new KeyFrame(
                Duration.seconds(0.1),
                event -> {
                    if (currentShieldWidth < maxShieldWidth) {
                        currentShieldWidth += shieldRegenRate; // Hồi shield
                        if (currentShieldWidth > maxShieldWidth) {
                            currentShieldWidth = maxShieldWidth; // Không vượt quá giới hạn
                        }
                        updateShieldBarUI(); // Cập nhật giao diện
                    }


                }

        ));
        shieldRegenTimeline.setCycleCount(Timeline.INDEFINITE);

        // Cập nhật giao diện ban đầu
        updateShieldBarUI();
    }

    public void takeShieldDamage() {
        if (currentShieldWidth > 0) {
            currentShieldWidth -= shieldDecreaseAmount; // Giảm shield
            if (currentShieldWidth < 0) {
                currentShieldWidth = 0; // Không âm
            }
            updateShieldBarUI(); // Cập nhật giao diện
        }



        // Nếu đang hồi shield, dừng lại và chờ delay
        if (isShieldRegenActive) {
            shieldRegenTimeline.stop();
            isShieldRegenActive = false;
        }

        // Bắt đầu hồi lại shield sau 2 giây
        Timeline delay = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event -> startShieldRegen()
        ));
        delay.setCycleCount(1);
        delay.play();
    }

    private void startShieldRegen() {
        if (!isShieldRegenActive) {
            isShieldRegenActive = true;
            shieldRegenTimeline.play(); // Bắt đầu hồi shield
        }
    }

    private void updateShieldBarUI() {
        // Điều chỉnh độ rộng thanh shield
        shieldbar.setFitWidth(currentShieldWidth);
    }

    public void updateShieldBar(double scale) {
        // Cập nhật thanh shield theo tỷ lệ (scale: 0.0 - 1.0)
        currentShieldWidth = maxShieldWidth * scale;
        updateShieldBarUI();
    }
}