import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private ArrayList<GameMap> maps; // Danh sách các map
    private int currentMapIndex;    // Chỉ số của map hiện tại

    public GamePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);

        // Khởi tạo danh sách maps
        maps = new ArrayList<>();
        createMaps();

        // Bắt đầu với map đầu tiên
        currentMapIndex = 0;
        GameMap currentMap = maps.get(currentMapIndex);
        player = new Player(currentMap.getPlayerStart().x, currentMap.getPlayerStart().y);

        // Timer để vẽ lại giao diện
        timer = new Timer(16, this); // 60 FPS
        timer.start();
    }

    private void createMaps() {
        // Map 1
        GameMap map1 = new GameMap(new Point(50, 400), new Rectangle(700, 450, 50, 50));
        map1.addPlatform(new Platform(0, 500, 800, 20));
        map1.addPlatform(new Platform(300, 400, 150, 20));
        map1.addPlatform(new Platform(600, 300, 100, 20));

        // Thêm quái vật vào map 1
        map1.addEnemy(new Enemy(350, 470, 40, 40, 2, 200)); // Quái vật di chuyển qua lại
        map1.addEnemy(new Enemy(600, 270, 40, 40, 1, 100)); // Quái vật trên platform

        maps.add(map1);

        // Map 2
        GameMap map2 = new GameMap(new Point(100, 450), new Rectangle(700, 450, 50, 50));
        map2.addPlatform(new Platform(0, 500, 800, 20));
        map2.addPlatform(new Platform(250, 400, 200, 20));
        map2.addPlatform(new Platform(500, 300, 150, 20));

        // Thêm quái vật vào map 2
        map2.addEnemy(new Enemy(500, 470, 50, 50, -3, 300));
        map2.addEnemy(new Enemy(300, 370, 30, 30, 1, 150));

        maps.add(map2);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Vẽ map hiện tại
        GameMap currentMap = maps.get(currentMapIndex);
        currentMap.draw(g);

        // Vẽ nhân vật
        player.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (movingLeft) {
            player.setVelocityX(-5); // Di chuyển sang trái
        } else if (movingRight) {
            player.setVelocityX(5); // Di chuyển sang phải
        } else {
            player.setVelocityX(0); // Dừng di chuyển nếu không nhấn phím
        }

        // Cập nhật logic của Player và quái vật
        GameMap currentMap = maps.get(currentMapIndex);
        player.update(currentMap.getPlatforms().toArray(new Platform[0]));

        for (Enemy enemy : currentMap.getEnemies()) {
            enemy.update();
        }

        // Kiểm tra va chạm và các điều kiện khác (như đã làm trước đó)
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : currentMap.getEnemies()) {
            if (player.isAttacking() && player.getAttackBounds().intersects(enemy.getBounds())) {
                enemiesToRemove.add(enemy);
            } else if (player.getBounds().intersects(enemy.getBounds())) {
                resetPlayerPosition();
            }
        }
        currentMap.getEnemies().removeAll(enemiesToRemove);

        if (player.getBounds().intersects(currentMap.getGoal())) {
            moveToNextMap();
        }

        repaint();
    }


    private void resetPlayerPosition() {
        GameMap currentMap = maps.get(currentMapIndex);
        player.setPosition(currentMap.getPlayerStart().x, currentMap.getPlayerStart().y);
    }



    private void moveToNextMap() {
        if (currentMapIndex < maps.size() - 1) {
            currentMapIndex++;
            GameMap nextMap = maps.get(currentMapIndex);
            player.setPosition(nextMap.getPlayerStart().x, nextMap.getPlayerStart().y);
        } else {
            JOptionPane.showMessageDialog(this, "Chúc mừng! Bạn đã hoàn thành tất cả các map!");
            System.exit(0); // Thoát game
        }
    }

    private boolean movingLeft = false;
    private boolean movingRight = false;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                movingLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                movingRight = true;
                break;
            case KeyEvent.VK_UP:
                player.jump(); // Nhảy ngay lập tức khi nhấn UP
                break;
            case KeyEvent.VK_SPACE:
                player.attack(); // Kích hoạt tấn công
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                movingLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                movingRight = false;
                break;
            case KeyEvent.VK_SPACE:
                player.stopAttack(); // Dừng tấn công
                break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // Không xử lý
    }
}
