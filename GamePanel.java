import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    // Paddle
    private int paddleX = 350;
    private final int paddleY = 550;
    private final int paddleWidth = 100;
    private final int paddleHeight = 15;
    private final int paddleSpeed = 10;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw paddle
        g.setColor(Color.GREEN);
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
    }

    // Xử lý phím
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            paddleX -= paddleSpeed;
            if (paddleX < 0) paddleX = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            paddleX += paddleSpeed;
            if (paddleX > WIDTH - paddleWidth) paddleX = WIDTH - paddleWidth;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Không cần xử lý
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Không cần xử lý
    }
}
