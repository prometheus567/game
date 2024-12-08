import javax.swing.*;

public class AdventureGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lựa chọn cuối cùng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Tạo panel chính cho game
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setVisible(true);
    }
}
