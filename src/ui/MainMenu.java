package ui;

import engine.GameLoop;
import engine.GameState;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utils.ImageLoader;

public class MainMenu extends MouseAdapter {

    //Lớp điều khiển GameLoop
    private final GameLoop gameLoop;

    //Hình nền và các nút
    private final BufferedImage background; // Thêm 'final'

    // Vị trí và kích thước các nút
    private final Rectangle levelButton = new Rectangle(300, 200, 200, 50);
    private final Rectangle settingButton = new Rectangle(300, 270, 200, 50);
    private final Rectangle exitButton = new Rectangle(300, 340, 200, 50);

    private static final String BACKGROUND_PATH = "C:/Users/DELL/Downloads/unnamed.png";

    public MainMenu(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        this.background = ImageLoader.loadImage(BACKGROUND_PATH);
    }

    // Phương thức vẽ giao diện (render)
    public void render(Graphics g) {
        // 1. Vẽ hình nền
        if (background != null) {
            g.drawImage(background, 0, 0, 800, 600, null);
        } else {
            g.setColor(new Color(20, 20, 40));
            g.fillRect(0, 0, 800, 600);
            g.setColor(Color.RED);
            g.drawString("KHÔNG TẢI ĐƯỢC HÌNH NỀN! Vui lòng kiểm tra file.", 200, 150);
        }

        Graphics2D g2d = (Graphics2D) g;

        // 2. Vẽ Tiêu đề
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("BRICK BREAKER", 200, 100);

        // 3. Vẽ các nút
        g.setFont(new Font("Arial", Font.PLAIN, 24));

        // Nút Level
        g.setColor(Color.BLUE);
        g2d.fill(levelButton);
        g.setColor(Color.WHITE);
        g.drawString("CHỌN LEVEL", levelButton.x + 35, levelButton.y + 35);

        // Nút Setting
        g.setColor(Color.ORANGE);
        g2d.fill(settingButton);
        g.setColor(Color.WHITE);
        g.drawString("CÀI ĐẶT", settingButton.x + 55, settingButton.y + 35);

        // Nút Exit
        g.setColor(Color.RED);
        g2d.fill(exitButton);
        g.setColor(Color.WHITE);
        g.drawString("THOÁT", exitButton.x + 65, exitButton.y + 35);
    }

    // Xử lý sự kiện click chuột
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (levelButton.contains(mx, my)) {
            gameLoop.setGameState(GameState.LEVEL_SELECT);
        } else if (settingButton.contains(mx, my)) {
            gameLoop.setGameState(GameState.SETTING);
        } else if (exitButton.contains(mx, my)) {
            System.exit(0);
        }
    }
}