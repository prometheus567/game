import java.util.ArrayList;
import java.awt.*;
public class GameMap {
    private ArrayList<Platform> platforms;
    private ArrayList<Enemy> enemies; // Danh sách quái vật
    private Point playerStart;
    private Rectangle goal;

    public GameMap(Point playerStart, Rectangle goal) {
        this.platforms = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.playerStart = playerStart;
        this.goal = goal;
    }

    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Point getPlayerStart() {
        return playerStart;
    }

    public Rectangle getGoal() {
        return goal;
    }

    public void draw(Graphics g) {
        for (Platform platform : platforms) {
            platform.draw(g);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        g.setColor(Color.YELLOW);
        g.fillRect(goal.x, goal.y, goal.width, goal.height);
    }
}
