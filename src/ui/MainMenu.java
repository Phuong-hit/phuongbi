package ui;

import engine.GameLoop;
import engine.GameState;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utils.ImageLoader; // (Cần file ImageLoader.java)

public class MainMenu extends MouseAdapter {

    private final GameLoop gameLoop;
    private final BufferedImage background; // biến để giữ ảnh nền

    // vị trí các nút
    private final Rectangle levelButton = new Rectangle(300, 200, 200, 50);
    private final Rectangle settingButton = new Rectangle(300, 270, 200, 50);
    private final Rectangle exitButton = new Rectangle(300, 340, 200, 50);
    private static final String BACKGROUND_PATH = "/Graphics/AnhNen.png";

    public MainMenu(GameLoop gameLoop) {
        this.gameLoop = gameLoop;

        // tải hình nền
        this.background = ImageLoader.loadImage(BACKGROUND_PATH);
    }

    public void render(Graphics g) {
        // 1. vẽ hình nền
        if (background != null) {
            g.drawImage(background, 0, 0, 800, 600, null);
        }

        Graphics2D g2d = (Graphics2D) g;

        // 3. vẽ các nút
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        // nút Lv
        g.setColor(Color.BLUE);
        g2d.fill(levelButton);
        g.setColor(Color.WHITE);
        g.drawString("CHỌN LEVEL", levelButton.x + 35, levelButton.y + 33);

        // nút setting
        g.setColor(Color.ORANGE);
        g2d.fill(settingButton);
        g.setColor(Color.WHITE);
        g.drawString("CÀI ĐẶT", settingButton.x + 55, settingButton.y + 33);

        // nút exit
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