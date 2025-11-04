package ui;

import engine.GameLoop;
import engine.GameState;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage; // (Import)
import utils.ImageLoader; // (Import)

public class LevelSelectionMenu extends MouseAdapter {

    private GameLoop gameLoop;

    // --- THÊM: Biến giữ ảnh nền ---
    private final BufferedImage background;
    // Đường dẫn chính xác đến file ảnh của bạn
    private static final String BACKGROUND_PATH = "/Graphics/AnhNen.png";
    // ----------------------------

    // vị trí các nút
    private Rectangle level1Button = new Rectangle(250, 150, 300, 50);
    private Rectangle level2Button = new Rectangle(250, 210, 300, 50);
    private Rectangle level3Button = new Rectangle(250, 270, 300, 50);
    private Rectangle level4Button = new Rectangle(250, 330, 300, 50);
    private Rectangle level5Button = new Rectangle(250, 390, 300, 50);
    private Rectangle backButton = new Rectangle(250, 480, 300, 50);

    public LevelSelectionMenu(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        // Tải ảnh nền
        this.background = ImageLoader.loadImage(BACKGROUND_PATH);
    }

    public void render(Graphics g) {
        // Vẽ ảnh nền
        if (background != null) {
            g.drawImage(background, 0, 0, 800, 600, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48)); // Font an toàn
        g.drawString("CHỌN LEVEL", 270, 80);

        Graphics2D g2d = (Graphics2D) g;
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20)); // Font an toàn

        // nút Lv1
        g.setColor(Color.GREEN.darker());
        g2d.fill(level1Button);
        g.setColor(Color.WHITE);
        g.drawString("LEVEL 1 (Làm quen - Yếu)", level1Button.x + 30, level1Button.y + 32);

        // nút Lv2
        g.setColor(Color.YELLOW.darker());
        g2d.fill(level2Button);
        g.setColor(Color.BLACK);
        g.drawString("LEVEL 2 (Trung bình - Mạnh)", level2Button.x + 20, level2Button.y + 32);

        // nút Lv3
        g.setColor(Color.RED.darker());
        g2d.fill(level3Button);
        g.setColor(Color.WHITE);
        g.drawString("LEVEL 3 (Khó - Unbreakable)", level3Button.x + 20, level3Button.y + 32);

        // nút Lv4
        g.setColor(Color.MAGENTA.darker());
        g2d.fill(level4Button);
        g.setColor(Color.WHITE);
        g.drawString("LEVEL 4 (Siêu khó)", level4Button.x + 80, level4Button.y + 32);

        // mút Lv5
        g.setColor(Color.BLUE.darker());
        g2d.fill(level5Button);
        g.setColor(Color.WHITE);
        g.drawString("LEVEL 5 (Tối đa)", level5Button.x + 90, level5Button.y + 32);

        // nút quay lại
        g.setColor(Color.GRAY.darker());
        g2d.fill(backButton);
        g.setColor(Color.WHITE);
        g.drawString("QUAY LẠI", backButton.x + 105, backButton.y + 32);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        boolean clicked = false;

        if (level1Button.contains(mx, my)) {
            gameLoop.startLevel(1);
            clicked = true;
        } else if (level2Button.contains(mx, my)) {
            gameLoop.startLevel(2);
            clicked = true;
        } else if (level3Button.contains(mx, my)) {
            gameLoop.startLevel(3);
            clicked = true;
        } else if (level4Button.contains(mx, my)) {
            gameLoop.startLevel(4);
            clicked = true;
        } else if (level5Button.contains(mx, my)) {
            gameLoop.startLevel(5);
            clicked = true;
        } else if (backButton.contains(mx, my)) {
            gameLoop.setGameState(GameState.MENU);
            clicked = true;
        }

        if (clicked) {
            gameLoop.getSoundManager().play("ClickSound");
        }
    }
}