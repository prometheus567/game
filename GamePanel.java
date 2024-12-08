import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;  // Thêm import cho ActionEvent
import java.awt.event.ActionListener;  // Thêm import cho ActionListener
import java.awt.event.KeyEvent;  // Thêm import cho KeyEvent
import java.awt.event.KeyListener;  // Thêm import cho KeyListener

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private Enemy enemy;
    private String gameState = "village"; // Trạng thái hiện tại
    private Image villageBackground;
    private Image adventureBackground;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        // Khởi tạo nhân vật và kẻ thù
        player = new Player(50, 50, 100);
        enemy = new Enemy(300, 300, 50);

        // Tải hình nền
        loadBackgroundImages();

        timer = new Timer(50, this);
        timer.start();
    }

    private void loadBackgroundImages() {
        // Tải hình nền từ thư mục
        villageBackground = new ImageIcon("resources/images/vl.jpg").getImage();
        adventureBackground = new ImageIcon("resources/images/fr.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ hình nền theo trạng thái game
        if (gameState.equals("village")) {
            g.drawImage(villageBackground, 0, 0, getWidth(), getHeight(), null);
        } else if (gameState.equals("adventure")) {
            g.drawImage(adventureBackground, 0, 0, getWidth(), getHeight(), null);
        }

        // Vẽ nhân vật và kẻ thù
        player.draw(g);
        if (gameState.equals("adventure")) {
            enemy.draw(g);
        }

        // Vẽ trạng thái
        g.setColor(Color.BLACK);
        g.drawString("HP: " + player.getHealth(), 10, 20);

        if (gameState.equals("village")) {
            g.drawString("Ngôi làng yên bình", getWidth() / 2 - 50, getHeight() / 2 - 20);
        } else if (gameState.equals("adventure")) {
            g.drawString("Phiêu lưu tìm ký ức...", getWidth() / 2 - 50, getHeight() / 2 - 20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Logic cập nhật game
        if (gameState.equals("adventure")) {
            enemy.moveRandom();
            if (player.collidesWith(enemy)) {
                player.takeDamage(1);
                enemy.takeDamage(1);
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Di chuyển nhân vật
        if (key == KeyEvent.VK_UP) player.move(0, -5);
        if (key == KeyEvent.VK_DOWN) player.move(0, 5);
        if (key == KeyEvent.VK_LEFT) player.move(-5, 0);
        if (key == KeyEvent.VK_RIGHT) player.move(5, 0);

        // Tấn công
        if (gameState.equals("adventure") && key == KeyEvent.VK_SPACE) {
            if (player.collidesWith(enemy)) {
                enemy.takeDamage(10);
            }
        }

        // Chuyển trạng thái
        if (key == KeyEvent.VK_A) gameState = "adventure";
        if (key == KeyEvent.VK_V) gameState = "village";
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
