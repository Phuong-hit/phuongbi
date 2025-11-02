package ui;

import engine.GameLoop;
import engine.GameState;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utils.ImageLoader;

public class MainMenu extends MouseAdapter {

    private final GameLoop gameLoop;
    private final BufferedImage background;
    private final Rectangle levelButton = new Rectangle(300, 200, 200, 50);
    private final Rectangle settingButton = new Rectangle(300, 270, 200, 50);
    private final Rectangle exitButton = new Rectangle(300, 340, 200, 50);

    private static final String BACKGROUND_PATH = "/images/background.png"; // Cập nhật đường dẫn nếu cần

    public MainMenu(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        this.background = ImageLoader.loadImage(BACKGROUND_PATH);
    }

    public void render(Graphics g) {
        if (background != null) {
            g.drawImage(background, 0, 0, 800, 600, null);
        } else {
            g.setColor(new Color(20, 20, 40));
            g.fillRect(0, 0, 800, 600);
            g.setColor(Color.RED);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14)); // Sửa
            g.drawString("KHÔNG TẢI ĐƯỢC HÌNH NỀN!", 250, 150);
            g.drawString("Kiểm tra đường dẫn: " + BACKGROUND_PATH, 230, 170);
        }

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.YELLOW);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50)); // Sửa
        g.drawString("BRICK BREAKER", 200, 100);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20)); // Sửa

        // Nút Level
        g.setColor(Color.BLUE);
        g2d.fill(levelButton);
        g.setColor(Color.WHITE);
        g.drawString("CHỌN LEVEL", levelButton.x + 35, levelButton.y + 33);

        // Nút Setting
        g.setColor(Color.ORANGE);
        g2d.fill(settingButton);
        g.setColor(Color.WHITE);
        g.drawString("CÀI ĐẶT", settingButton.x + 55, settingButton.y + 33);

        // Nút Exit
        g.setColor(Color.RED);
        g2d.fill(exitButton);
        g.setColor(Color.WHITE);
        g.drawString("THOÁT", exitButton.x + 65, exitButton.y + 33);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (levelButton.contains(mx, my)) {
            gameLoop.getSoundManager().play("ClickSound");
            gameLoop.setGameState(GameState.LEVEL_SELECT);
        } else if (settingButton.contains(mx, my)) {
            gameLoop.getSoundManager().play("ClickSound");
            gameLoop.setGameState(GameState.SETTING);
        } else if (exitButton.contains(mx, my)) {
            gameLoop.getSoundManager().play("ClickSound");
            System.exit(0);
        }
    }
}