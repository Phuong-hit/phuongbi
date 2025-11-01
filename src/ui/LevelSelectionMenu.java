package ui;

import engine.GameLoop;
import engine.GameState;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelSelectionMenu extends MouseAdapter {

    private GameLoop gameLoop;

    // Vị trí và kích thước các nút Level
    private Rectangle level1Button = new Rectangle(300, 150, 200, 50);
    private Rectangle level2Button = new Rectangle(300, 220, 200, 50);
    private Rectangle level3Button = new Rectangle(300, 290, 200, 50);
    private Rectangle backButton = new Rectangle(300, 400, 200, 50);

    public LevelSelectionMenu(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void render(Graphics g) {
        g.setColor(new Color(30, 30, 60)); // Nền xanh đậm
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("CHỌN LEVEL", 280, 80);

        Graphics2D g2d = (Graphics2D) g;
        g.setFont(new Font("Arial", Font.PLAIN, 24));

        // Nút Level 1
        g.setColor(Color.GREEN);
        g2d.fill(level1Button);
        g.setColor(Color.BLACK);
        g2d.draw(level1Button);
        g.drawString("LEVEL 1 ", level1Button.x + 35, level1Button.y + 35);

        // Nút Level 2
        g.setColor(Color.YELLOW);
        g2d.fill(level2Button);
        g.setColor(Color.BLACK);
        g2d.draw(level2Button);
        g.drawString("LEVEL 2 ", level2Button.x + 10, level2Button.y + 35);

        // Nút Level 3
        g.setColor(Color.RED);
        g2d.fill(level3Button);
        g.setColor(Color.BLACK);
        g2d.draw(level3Button);
        g.drawString("LEVEL 3 )", level3Button.x + 30, level3Button.y + 35);

        // Nút Quay lại
        g.setColor(Color.DARK_GRAY);
        g2d.fill(backButton);
        g.setColor(Color.WHITE);
        g2d.draw(backButton);
        g.drawString("QUAY LẠI", backButton.x + 45, backButton.y + 35);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (level1Button.contains(mx, my)) {
            gameLoop.startLevel(1);
        } else if (level2Button.contains(mx, my)) {
            gameLoop.startLevel(2);
        } else if (level3Button.contains(mx, my)) {
            gameLoop.startLevel(3);
        } else if (backButton.contains(mx, my)) {
            gameLoop.setGameState(GameState.MENU);
        }
    }
}