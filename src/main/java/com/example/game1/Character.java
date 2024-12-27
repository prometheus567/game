package com.example.game1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Character {
    private ImageView sprite; // Hiển thị nhân vật
    private double speedX; // Tốc độ ngang
    private double speedY; // Tốc độ dọc
    private double xPosition; // Vị trí x
    private double yPosition; // Vị trí y

    private boolean onGround = true; // Kiểm tra nhân vật có đang đứng trên đất
    private double gravity = 0.3; // Lực kéo trọng lực
    private double jumpForce = -15; // Lực nhảy

    private Timeline moveTimeline; // Timeline để cập nhật di chuyển
    private Timeline walkTimeline; // Timeline hoạt ảnh đi bộ
    private Timeline jumpTimeline; // Timeline hoạt ảnh nhảy

    private int jumpCount = 0; // Số lần nhảy hiện tại
    private final int maxJumpCount = 2; // Giới hạn số lần nhảy (double jump)
    private int frameIndex = 0; // Theo dõi frame hiện tại của hoạt ảnh nhảy
    private Image[] jumpFrames;

    private Image[] idleFrames; // Frame cho trạng thái idle
    private Timeline idleTimeline; // Timeline cho hoạt ảnh idle

    private Image[] runFrames;  // Frame cho hoạt ảnh chạy
    private Timeline runTimeLine; // Timeline cho hoạt ảnh chạy

    private int comboStep = 0; // Theo dõi trạng thái combo (1 -> 3)
    private Timeline comboTimeline; // Quản lý hoạt ảnh combo
    private Image[][] attackFrames; // Chứa các frames cho từng đòn đánh
    private Timeline resetComboTimer;

    private Image[] hurtFrames;
    private Timeline hurtTimeLine;
    private boolean isHurt = false; // Mặc định không bị hurt

    private Image[] shieldFrames; // Frames cho trạng thái shield
    private boolean isShielding = false; // Kiểm tra trạng thái shield
    private Timeline shieldTimeline; // Quản lý frame chuyển đổi giữa frame 1 và 2
    private HealthBarController healthBarController; // Tham chiếu đến HealthBarController

    private Image[] deadFrames; // Frames cho hoạt ảnh chết
    private Timeline deadTimeline; // Timeline để quản lý hoạt ảnh chết

    private boolean isDead = false; // Trạng thái đã chết
    private Runnable onDeathCallback; // Hàm callback để gọi khi chết

    // Constructor
    public Character(String[] walkFramesPaths, String[] jumpFramesPaths, String[] idleFramesPaths,
                     String[] runFramesPaths, String[][] attackFramesPaths,String[] hurtFramesPaths,String[] shieldFramesPaths,String[] deadFramesPaths, double initialX, double initialY) {
        this.sprite = new ImageView();
        this.sprite.setFitWidth(100); // Kích thước nhân vật
        this.sprite.setFitHeight(100);

        this.xPosition = initialX; // Vị trí ban đầu
        this.yPosition = initialY;

        this.speedX = 0;
        this.speedY = 0;

        // Khởi tạo deadFrames
        this.deadFrames = new Image[deadFramesPaths.length];
        for (int i = 0; i < deadFramesPaths.length; i++) {
            this.deadFrames[i] = new Image(getClass().getResource(deadFramesPaths[i]).toExternalForm());
        }

        // Tạo deadTimeline
        deadTimeline = new Timeline();
        deadTimeline.setCycleCount(1);
        for (int i = 0; i < deadFrames.length; i++) {
            final int index = i;
            deadTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.2 * i),
                    event -> sprite.setImage(deadFrames[index]) // Hiển thị từng frame
            ));
        }

        // Khởi tạo shieldFrames
        this.shieldFrames = new Image[shieldFramesPaths.length];
        for (int i = 0; i < shieldFramesPaths.length; i++){
            shieldFrames[i] = new Image(getClass().getResource(shieldFramesPaths[i]).toExternalForm());
        }

        // Khởi tạo Timeline cho shield
        shieldTimeline = new Timeline();
        shieldTimeline.setCycleCount(1); // Chỉ chạy một lần
        shieldTimeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0.2), // Thời gian frame 2 hiển thị
                event -> sprite.setImage(shieldFrames[0]) // Quay lại frame 1
        ));

        // Khởi tạo attackFrames
        attackFrames = new Image[3][];
        for (int i = 0; i < attackFramesPaths.length; i++) {
            attackFrames[i] = new Image[attackFramesPaths[i].length];
            for (int j = 0; j < attackFramesPaths[i].length; j++) {
                attackFrames[i][j] = new Image(getClass().getResource(attackFramesPaths[i][j]).toExternalForm());
            }
        }

        comboTimeline = new Timeline();
        comboTimeline.setCycleCount(1);

        for (int i = 0; i < attackFrames.length; i++) { // Hàng: Đòn tấn công
            for (int j = 0; j < attackFrames[i].length; j++) { // Cột: Các frame của đòn
                final int attackIndex = i; // Lấy đòn tấn công
                final int frameIndex = j; // Lấy frame trong đòn
                comboTimeline.getKeyFrames().add(new KeyFrame(
                        Duration.seconds(0.1 * (frameIndex + attackIndex * 10)), // Tính thời gian cho mỗi frame
                        event -> sprite.setImage(attackFrames[attackIndex][frameIndex]) // Hiển thị frame tương ứng
                ));
            }
        }

        comboTimeline.setOnFinished(event -> {
            // Đặt trạng thái delay để chờ người chơi thực hiện đòn kế tiếp
            Timeline delayTimeline = new Timeline(new KeyFrame(
                    Duration.seconds(1), // Delay 0.5 giây
                    delayEvent -> {
                        // Nếu không có hành động tiếp theo, chuyển về idle
                        if (comboStep == 0) {
                            idleTimeline.play();
                        }
                    }
            ));

            delayTimeline.setCycleCount(1); // Chỉ chạy một lần
            delayTimeline.play();

            // Đặt trạng thái chờ để reset combo sau khi delay kết thúc
            resetComboAfterDelay();
        });

        // Khởi tạo hurtFrames
        this.hurtFrames = new Image[hurtFramesPaths.length];
        for (int i = 0; i < hurtFramesPaths.length; i++){
            hurtFrames[i] = new Image(getClass().getResource(hurtFramesPaths[i]).toExternalForm());
        }

        hurtTimeLine = new Timeline();
        hurtTimeLine.setCycleCount(1);

        for (int i = 0; i < hurtFrames.length; i++){
            final int index = i;
            hurtTimeLine.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.2*i),
                    event -> sprite.setImage(hurtFrames[index])
            ));
        }

        // Khi hurt kết thúc, quay về idle
        hurtTimeLine.setOnFinished(event -> idleTimeline.play());

        // Tạo hoạt ảnh chạy
        this.runFrames = new Image[runFramesPaths.length]; //Khởi tạo mảng runFrames
        for (int i = 0; i < runFramesPaths.length;i++){
            runFrames[i] = new Image(getClass().getResource(runFramesPaths[i]).toExternalForm());
        }

        runTimeLine = new Timeline();
        runTimeLine.setCycleCount(Timeline.INDEFINITE);

        for (int i = 0; i < runFrames.length; i++){
            final int index = i;
            runTimeLine.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1*i),
                    event -> sprite.setImage(runFrames[index])
            ));;
        }

        // Tạo hoạt ảnh đi bộ
        Image[] walkFrames = new Image[walkFramesPaths.length]; //Khởi tạo mảng walkFrames
        for (int i = 0; i < walkFramesPaths.length; i++) {
            walkFrames[i] = new Image(getClass().getResource(walkFramesPaths[i]).toExternalForm());
        }

        walkTimeline = new Timeline();
        walkTimeline.setCycleCount(Timeline.INDEFINITE);
        for (int i = 0; i < walkFrames.length; i++) {
            final int index = i;
            walkTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i),
                    event -> sprite.setImage(walkFrames[index])
            ));
        }

        // Tạo hoạt ảnh idle
        this.idleFrames = new Image[idleFramesPaths.length]; // Khởi tạo mảng idleFrames
        for (int i = 0; i < idleFramesPaths.length; i++) {
            this.idleFrames[i] = new Image(getClass().getResource(idleFramesPaths[i]).toExternalForm());
        }

        idleTimeline = new Timeline();
        idleTimeline.setCycleCount(Timeline.INDEFINITE); // Lặp lại vô hạn
        for (int i = 0; i < idleFrames.length; i++) {
            final int index = i;
            idleTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.2 * i), // Điều chỉnh tốc độ frame idle
                    event -> sprite.setImage(idleFrames[index])
            ));
        }



        // Tạo hoạt ảnh nhảy
        this.jumpFrames = new Image[jumpFramesPaths.length]; // Sử dụng biến toàn cục
        for (int i = 0; i < jumpFramesPaths.length; i++) {
            this.jumpFrames[i] = new Image(getClass().getResource(jumpFramesPaths[i]).toExternalForm());
        }

        jumpTimeline = new Timeline();
        jumpTimeline.setCycleCount(1); // Chỉ phát 1 lần
        for (int i = 0; i < jumpFrames.length; i++) {
            final int index = i;
            jumpTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i),
                    event -> sprite.setImage(jumpFrames[index])
            ));
        }

        // Timeline cập nhật di chuyển
        moveTimeline = new Timeline(new KeyFrame(
                Duration.seconds(0.016), // 60 FPS
                event -> moveCharacter()
        ));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();
    }

    public void hurt(){
        if (isHurt) return; // Nếu đang bị hurt, không làm gì cả

        isHurt = true; // Đặt trạng thái hurt

        // Chạy hoạt ảnh hurt
        hurtTimeLine.setOnFinished(event -> {
            isHurt = false; // Khi hoạt ảnh kết thúc, bỏ trạng thái hurt
            idleTimeline.play(); // Quay lại trạng thái idle
        });
        hurtTimeLine.playFromStart();
    }

    void moveCharacter() {
        if (isShielding || isDead) return;

        if (hurtTimeLine.getStatus() == Timeline.Status.RUNNING) return;

        xPosition += speedX;

        // Kiểm tra trọng lực
        if (!onGround) {
            speedY += gravity;
            if (speedY > 10) speedY = 10; // Giới hạn tốc độ rơi
        }
        yPosition += speedY;

        if (yPosition >= 500) {
            yPosition = 500;
            onGround = true;
            speedY = 0;
            jumpCount = 0;

            jumpTimeline.stop();
            frameIndex = 0;

            if (!isDead) {
                if (speedX == 0) {
                    idleTimeline.play();
                } else if (Math.abs(speedX) > 3) {
                    runTimeLine.play();
                } else {
                    walkTimeline.play();
                }
            }
        }

        sprite.setTranslateX(xPosition);
        sprite.setTranslateY(yPosition);
    }

    public void jump() {
        if (isHurt || isShielding) return; // Không nhảy nếu đang bị hurt hoặc shield

        if (jumpCount < maxJumpCount) { // Kiểm tra số lần nhảy
            speedY = jumpForce; // Thực hiện nhảy
            onGround = false; // Không còn trên đất
            jumpCount++; // Tăng số lần nhảy

            isShielding = false; // Tắt trạng thái shield khi nhảy
            walkTimeline.stop(); // Tạm dừng hoạt ảnh đi bộ

            // Cập nhật hoạt ảnh nhảy
            jumpTimeline.stop(); // Dừng hoạt ảnh trước đó
            jumpTimeline = new Timeline(); // Tạo lại Timeline nhảy
            for (int i = frameIndex; i < jumpFrames.length; i++) { // Tiếp tục từ frame hiện tại
                final int index = i;
                jumpTimeline.getKeyFrames().add(new KeyFrame(
                        Duration.seconds(0.1 * (i - frameIndex)), // Tính từ frame hiện tại
                        event -> {
                            sprite.setImage(jumpFrames[index]);
                            frameIndex = index + 1; // Cập nhật frame hiện tại
                        }
                ));
            }

            jumpTimeline.setOnFinished(e -> frameIndex = 0); // Reset frameIndex khi hoạt ảnh kết thúc
            jumpTimeline.playFromStart();
        }
    }

    public void triggerDeadAnimation() {
        // Kiểm tra nếu animation chết đang chạy hoặc nhân vật đã chết
        if (deadTimeline.getStatus() == Timeline.Status.RUNNING || isDead) {
            return; // Nếu đã chết hoặc đang chạy animation, thoát
        }

        // Đặt trạng thái chết
        isDead = true;

        // Dừng tất cả các hoạt ảnh khác
        idleTimeline.stop();
        walkTimeline.stop();
        runTimeLine.stop();
        jumpTimeline.stop();
        hurtTimeLine.stop();
        shieldTimeline.stop();

        // Phát hoạt ảnh chết
        deadTimeline.setOnFinished(event -> {
            // Sau khi hoàn thành animation chết, hiển thị Game Over
            if (onDeathCallback != null) {
                onDeathCallback.run();
            }
        });
        deadTimeline.playFromStart();
    }

    public void shield() {
        if (isShielding || !onGround) return; // Không bật shield nếu đang shield hoặc không ở trên mặt đất

        isShielding = true; // Bật trạng thái shield
        sprite.setImage(shieldFrames[0]); // Hiển thị frame 1
        idleTimeline.stop(); // Tắt trạng thái idle
        walkTimeline.stop(); // Dừng các trạng thái khác
        runTimeLine.stop();
        jumpTimeline.stop(); // Dừng hoạt ảnh nhảy nếu có
        hurtTimeLine.stop(); // Dừng trạng thái hurt nếu có
    }

    public void stopShielding() {
        if (!isShielding) return; // Nếu không trong trạng thái shield, không làm gì cả

        isShielding = false; // Tắt trạng thái shield
        idleTimeline.play(); // Quay lại trạng thái idle
    }

    public void takeHitWhileShielding() {
        if (!isShielding) return; // Nếu không trong trạng thái shield, thoát

        // Hiển thị frame 2 khi bị tấn công
        sprite.setImage(shieldFrames[1]);

        // Giảm shield thông qua HealthBarController
        if (healthBarController != null) {
            healthBarController.takeShieldDamage();
        }

        // Quay lại frame 1 sau một thời gian
        shieldTimeline.playFromStart();
    }


    public void setSpeedX(double speedX) {
        if (isHurt || isDead) return; // Không di chuyển nếu đang bị hurt hoặc đã chết

        // Gán giá trị tốc độ mới
        this.speedX = speedX;

        // Xử lý hoạt ảnh di chuyển
        walkTimeline.stop();
        runTimeLine.stop();
        idleTimeline.stop();

        if (speedX != 0) {
            if (Math.abs(speedX) > 3) { // Nếu chạy
                runTimeLine.play();
            } else { // Nếu đi bộ
                walkTimeline.play();
            }
            // Lật nhân vật nếu đi trái
            sprite.setScaleX(speedX > 0 ? 1 : -1);
        } else if (onGround) { // Đứng yên
            idleTimeline.play();
        }
    }

    public double getSpeedX() {
        return speedX;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void resetComboAfterDelay() {
        if (resetComboTimer != null) {
            resetComboTimer.stop();
        }

        resetComboTimer = new Timeline(new KeyFrame(
                Duration.seconds(1.5), // Thời gian chờ để reset combo (1.5 giây)
                event -> comboStep = 0 // Reset combo nếu không có hành động trong khoảng thời gian này
        ));
        resetComboTimer.play();
    }

    private void playAttackAnimation(int comboIndex) {
        comboTimeline = new Timeline();

        // Thêm delay ngắn trước frame đầu tiên (giúp chuyển tiếp mượt hơn)
        comboTimeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0.15), // Delay nhỏ 0.1 giây
                event -> sprite.setImage(attackFrames[comboIndex][0]) // Hiển thị frame đầu tiên
        ));


        // Thêm các frame của attack
        for (int i = 1; i < attackFrames[comboIndex].length; i++) { // Bắt đầu từ frame 1
            final int index = i;
            comboTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(0.1 * i),
                    event -> sprite.setImage(attackFrames[comboIndex][index])
            ));
        }

        // Kết thúc combo
        comboTimeline.setOnFinished(event -> {
            resetComboAfterDelay();
        });

        comboTimeline.playFromStart();
    }


    public void attack() {
        if (isHurt) return;

        if (comboTimeline != null && comboTimeline.getStatus() == Timeline.Status.RUNNING) {
            return; // Nếu đang đánh, không nhận thêm input
        }

        comboStep++; // Tăng bước combo
        if (comboStep > 3) { // Reset về đòn đầu sau khi hết combo
            comboStep = 1;
        }

        // Tạo hoạt ảnh cho đòn hiện tại
        playAttackAnimation(comboStep - 1);

        // Nếu đang trong trạng thái delay, hủy delay và tiếp tục combo
        if (resetComboTimer != null && resetComboTimer.getStatus() == Timeline.Status.RUNNING) {
            resetComboTimer.stop();
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setOnDeathCallback(Runnable callback) {
        this.onDeathCallback = callback;
    }

    // Getter for xPosition
    public double getX() {
        return xPosition;
    }

    // Getter for yPosition
    public double getY() {
        return yPosition;
    }



}