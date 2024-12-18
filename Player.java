import java.awt.*;

public class Player {
    private int x, y;
    private int width, height;
    private int velocityX, velocityY;
    private boolean onGround;
    private static final int GRAVITY = 1;
    private boolean attacking; // Trạng thái tấn công
    private int attackRange = 50; // Phạm vi tấn công

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 50;
        this.velocityX = 0;
        this.velocityY = 0;
        this.onGround = false;
        this.attacking = false;
    }

    public void draw(Graphics g) {
        g.setColor(attacking ? Color.ORANGE : Color.RED); // Màu cam khi tấn công
        g.fillRect(x, y, width, height);

        if (attacking) {
            // Hiển thị vùng tấn công
            g.setColor(Color.YELLOW);
            if (velocityX >= 0) {
                g.fillRect(x + width, y, attackRange, height);
            } else {
                g.fillRect(x - attackRange, y, attackRange, height);
            }
        }
    }

    public void update(Platform[] platforms) {
        // Cập nhật vị trí nhân vật
        x += velocityX;
        y += velocityY;

        // Áp dụng trọng lực khi không tiếp đất
        // Bên trong phương thức update() của Player
        if (!onGround) {
            velocityY += GRAVITY; // Tăng tốc theo trọng lực
            if (velocityY > 15) {
                velocityY = 15; // Giới hạn vận tốc rơi xuống
            }
        }


        // Kiểm tra va chạm với nền
        onGround = false;
        for (Platform platform : platforms) {
            if (getBounds().intersects(platform.getBounds())) {
                // Nếu va chạm với nền khi rơi xuống
                if (velocityY >= 0) {
                    y = platform.getY() - height; // Điều chỉnh vị trí bên trên nền
                    velocityY = 0;
                    onGround = true; // Đang tiếp đất
                }
            }
        }

        // Đảm bảo nhân vật không đi ra ngoài màn hình
        if (x < 0) x = 0;
        if (x + width > 800) x = 800 - width;
        if (y > 600) y = 600 - height; // Không rơi xuống đáy màn hình
    }


    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public void jump() {
        if (onGround) {
            velocityY = -15; // Nhảy khi đang tiếp đất
            onGround = false;
        }
    }


    public void attack() {
        attacking = true;
    }

    public void stopAttack() {
        attacking = false;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getAttackBounds() {
        if (velocityX >= 0) {
            return new Rectangle(x + width, y, attackRange, height);
        } else {
            return new Rectangle(x - attackRange, y, attackRange, height);
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
