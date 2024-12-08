import java.awt.*;
import javax.swing.*;

public class Enemy {
    private int x, y;
    private int health;
    private Image enemyImage;

    public Enemy(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
        loadImage();
    }

    private void loadImage() {
        // Tải hình ảnh từ thư mục
        ImageIcon icon = new ImageIcon("resources/images/Enemy.png");
        enemyImage = icon.getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(enemyImage, x, y, 50, 50, null); // Vẽ hình ảnh (kích thước 50x50)
    }

    public void moveRandom() {
        x += (int) (Math.random() * 5 - 2);
        y += (int) (Math.random() * 5 - 2);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }
}
