package ui;

import engine.GameLoop;
import engine.GameState;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage; // <<< THÊM IMPORT
import utils.ImageLoader; // <<< THÊM IMPORT

public class SettingMenu extends MouseAdapter {

    private GameLoop gameLoop;

    private final BufferedImage background;
    // Đường dẫn chính xác đến file ảnh của bạn
    private static final String BACKGROUND_PATH = "/Graphics/AnhNen.png";

    private Rectangle soundButton = new Rectangle(250, 200, 300, 50);
    private Rectangle backButton = new Rectangle(300, 450, 200, 50);

    public SettingMenu(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        // --- THÊM: Tải ảnh nền khi khởi tạo ---
        this.background = ImageLoader.loadImage(BACKGROUND_PATH);
    }

    // --- KHÔI PHỤC: Logic vẽ (render) ---
    public void render(Graphics g) {
        // 1. Vẽ ảnh nền
        if (background != null) {
            g.drawImage(background, 0, 0, 800, 600, null);
        } else {
            // Nếu tải thất bại, vẽ nền tối và báo lỗi
            /*g.setColor(new Color(50, 50, 50));
            g.fillRect(0, 0, 800, 600);
            g.setColor(Color.RED);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g.drawString("KHÔNG TẢI ĐƯỢC HÌNH NỀN!", 250, 100);*/
        }

        // 2. Vẽ Tiêu đề
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40)); // Font an toàn
        g.drawString("CÀI ĐẶT TRÒ CHƠI", 220, 100);

        Graphics2D g2d = (Graphics2D) g;
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22)); // Font an toàn

        // 3. Vẽ Nút Tắt/Bật Âm thanh
        boolean isSoundOn = gameLoop.isSoundOn();
        String soundText = "ÂM THANH: " + (isSoundOn ? "BẬT (ON)" : "TẮT (OFF)");
        Color soundColor = isSoundOn ? Color.GREEN : Color.RED;

        g.setColor(soundColor);
        g2d.fill(soundButton);
        g.setColor(Color.BLACK);
        g.drawString(soundText, soundButton.x + 35, soundButton.y + 33);

        // ve nút quay lạii
        g.setColor(Color.DARK_GRAY);
        g2d.fill(backButton);
        g.setColor(Color.WHITE);
        g.drawString("QUAY LẠI", backButton.x + 45, backButton.y + 33);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (soundButton.contains(mx, my)) {
            gameLoop.getSoundManager().play("ClickSound");
            gameLoop.toggleSound();
        } else if (backButton.contains(mx, my)) {
            gameLoop.getSoundManager().play("ClickSound");
            gameLoop.setGameState(GameState.MENU);
        }
    }
}