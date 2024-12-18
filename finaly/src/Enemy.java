import java.awt.*;

public class Enemy {
    private int x, y;           // Vị trí quái vật
    private int width, height;  // Kích thước
    private int velocityX;      // Tốc độ di chuyển ngang
    private int moveRange;      // Phạm vi di chuyển
    private int startX;         // Vị trí bắt đầu

    public Enemy(int x, int y, int width, int height, int velocityX, int moveRange) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = velocityX;
        this.moveRange = moveRange;
        this.startX = x;
    }

    public void update() {
        x += velocityX;

        // Đổi hướng khi quái vật di chuyển hết phạm vi
        if (x < startX || x > startX + moveRange) {
            velocityX = -velocityX;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
