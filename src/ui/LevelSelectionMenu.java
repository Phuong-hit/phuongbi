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
    private Rectangle backButton   = new Rectangle(300, 400, 200, 50);

    public LevelSelectionMenu(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }


    public void render(Graphics g) {
        // background
        g.setColor(new Color(30, 30, 60));
        g.fillRect(0, 0, 800, 600);

        // title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics titleFm = g.getFontMetrics();
        String title = "CHỌN LEVEL";
        int titleX = (800 - titleFm.stringWidth(title)) / 2;
        int titleY = 80;
        g.drawString(title, titleX, titleY);

        Graphics2D g2d = (Graphics2D) g;

        // common font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 28);
        g.setFont(buttonFont);

        // draw buttons (fill -> border -> text centered)
        drawButton(g2d, level1Button, Color.GREEN, Color.BLACK, "LEVEL 1", buttonFont);
        drawButton(g2d, level2Button, Color.YELLOW, Color.BLACK, "LEVEL 2", buttonFont);
        drawButton(g2d, level3Button, Color.RED, Color.BLACK, "LEVEL 3", buttonFont);
        drawButton(g2d, backButton, Color.DARK_GRAY, Color.WHITE, "QUAY LẠI", buttonFont);
    }

    // Helper: draw rectangle button with centered text
    private void drawButton(Graphics2D g2d, Rectangle rect, Color fillColor, Color textColor, String text, Font font) {
        // fill
        g2d.setColor(fillColor);
        g2d.fillRect(rect.x, rect.y, rect.width, rect.height);

        // border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(rect.x, rect.y, rect.width, rect.height);

        // text centered
        drawCenteredString(g2d, text, rect, font, textColor);
    }

    // Center text horizontally and vertically inside rect
    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font, Color textColor) {
        Font prev = g.getFont();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int x = rect.x + (rect.width - textWidth) / 2;
        // Baseline y: top of rect + (available height - textHeight)/2 + ascent
        int y = rect.y + (rect.height - textHeight) / 2 + fm.getAscent();

        g.setColor(textColor);
        g.drawString(text, x, y);
        g.setFont(prev);
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
