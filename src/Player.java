import java.awt.*;
import javax.swing.*;

public class Player {
    private int x, y;          // Vị trí của Kane
    private int health;
    private Image playerImage; // Hình ảnh của Kane

    public Player(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
        loadImage();
    }

    private void loadImage() {
        // Tải hình ảnh từ thư mục
        ImageIcon icon = new ImageIcon("resources/images/Player.png"); // Sử dụng dấu gạch chéo đơn giản

        playerImage = icon.getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, 50, 50, null); // Vẽ hình ảnh (kích thước 50x50)
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public boolean collidesWith(Enemy enemy) {
        return Math.abs(x - enemy.getX()) < 50 && Math.abs(y - enemy.getY()) < 50;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public int getHealth() {
        return health;
    }
}
