package ui;

import engine.GameLoop;
import engine.GameState;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingMenu extends MouseAdapter {

    private GameLoop gameLoop;

    // Vị trí nút Tắt/Bật Âm thanh
    private Rectangle soundButton = new Rectangle(250, 200, 300, 50);
    // Vị trí nút Quay lại
    private Rectangle backButton = new Rectangle(300, 450, 200, 50);

    public SettingMenu(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void render(Graphics g) {
        g.setColor(new Color(50, 50, 50)); // Nền xám đậm
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("CÀI ĐẶT TRÒ CHƠI", 220, 100);

        Graphics2D g2d = (Graphics2D) g;
        g.setFont(new Font("Arial", Font.PLAIN, 24));

        // Nút Tắt/Bật Âm thanh
        boolean isSoundOn = gameLoop.isSoundOn();
        String soundText = "ÂM THANH: " + (isSoundOn ? "BẬT (ON)" : "TẮT (OFF)");
        Color soundColor = isSoundOn ? Color.GREEN : Color.RED;

        g.setColor(soundColor);
        g2d.fill(soundButton);
        g.setColor(Color.BLACK);
        g.drawString(soundText, soundButton.x + 20, soundButton.y + 35);

        // Nút Quay lại
        g.setColor(Color.DARK_GRAY);
        g2d.fill(backButton);
        g.setColor(Color.WHITE);
        g.drawString("QUAY LẠI", backButton.x + 45, backButton.y + 35);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (soundButton.contains(mx, my)) {
            gameLoop.toggleSound();
        } else if (backButton.contains(mx, my)) {
            gameLoop.setGameState(GameState.MENU);
        }
    }
}