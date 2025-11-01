package engine;

import entities.*;
import levels.Level;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel {

    private Paddle paddle;
    private Ball ball;
    private Level level;
    private java.util.List<PowerUp> powerUps = new ArrayList<>();

    private boolean gameOver;
    private boolean ballStuck;
    private int score;

    private GameLoop loop;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // --- THAY THẾ Listener gốc bằng các đối tượng Adapter để dễ dàng thêm/bỏ ---
    private MouseMotionAdapter gameMouseMotionListener;
    private MouseAdapter gameMouseListener;
    // --------------------------------------------------------------------------

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true); // Đảm bảo GamePanel nhận sự kiện

        // Khởi tạo GameLoop ngay lập tức, nhưng không chạy startGame()
        loop = new GameLoop(this);
        new Thread(loop).start();

        // Khởi tạo các Listener cho GamePlay
        gameMouseMotionListener = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (loop.getCurrentState() == GameState.IN_GAME) {
                    paddle.x = e.getX() - paddle.width / 2;
                    if (paddle.x < 0) paddle.x = 0;
                    if (paddle.x + paddle.width > WIDTH) paddle.x = WIDTH - paddle.width;
                }
            }
        };

        gameMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (loop.getCurrentState() == GameState.IN_GAME) {
                    if (ballStuck) {
                        ballStuck = false;
                        ball.setDirection(3, -3);
                    } else if (gameOver) {
                        // Trở về Menu thay vì tự khởi động lại game
                        loop.setGameState(GameState.MENU);
                    }
                }
            }
        };
    }

    // Phương thức mới để xóa tất cả các Mouse Listener
    public void clearListeners() {
        // Xóa tất cả MouseListener
        for (MouseListener ml : getMouseListeners()) {
            removeMouseListener(ml);
        }
        // Xóa tất cả MouseMotionListener
        for (MouseMotionListener mml : getMouseMotionListeners()) {
            removeMouseMotionListener(mml);
        }
    }

    // Phương thức mới để thêm lại Listener cho trạng thái IN_GAME
    public void addGameListeners() {
        addMouseListener(gameMouseListener);
        addMouseMotionListener(gameMouseMotionListener);
    }

    // Cập nhật startGame() để nhận levelNumber
    public void startGame(int levelNumber) {
        paddle = new Paddle(WIDTH / 2 - 50, HEIGHT - 50, 100, 10, Color.WHITE);
        ball = new Ball(paddle.x + paddle.width / 2 - 10, paddle.y - 20, 20, Color.YELLOW);

        // Tải level cụ thể
        level = new Level(levelNumber);

        powerUps.clear();
        score = 0;
        gameOver = false;
        ballStuck = true;
    }

    // ... (các phương thức và thuộc tính khác)

    public void updateGame() {
        if (gameOver) return;

        // 1. Cập nhật vị trí Paddle luôn chạy
        paddle.update();

        // 2. LOGIC CẬP NHẬT VỊ TRÍ BÓNG (PHẦN CẦN SỬA/KIỂM TRA)
        if (ballStuck) {
            // BỔ SUNG: Cập nhật vị trí bóng DÍNH theo Paddle
            ball.x = paddle.x + paddle.width / 2 - ball.size / 2;
            ball.y = paddle.y - ball.size;
        } else {
            // Khi không dính, bóng bắt đầu di chuyển (di chuyển bóng ở đây)
            ball.move();

            // --- LOGIC VA CHẠM (Chỉ chạy khi bóng đang di chuyển) ---

            // Tường
            if (ball.x <= 0 || ball.x + ball.size >= WIDTH) ball.dx = -ball.dx;
            if (ball.y <= 0) ball.dy = -ball.dy;

            // Dưới (Game Over)
            if (ball.y + ball.size >= HEIGHT) {
                gameOver = true;
                System.out.println("GAME OVER - Returning to Menu");
                // Không setGameState ở đây, để user thấy màn hình GAME OVER rồi click về menu.
                return; // Dừng update khi Game Over
            }

            // Paddle
            if (ball.getRect().intersects(paddle.getRect())) {
                // ... (Logic va chạm Paddle hiện tại của bạn)
                ball.dy = -Math.abs(ball.dy);
            }

            // Bricks
            for (Brick b : level.bricks) {
                if (!b.destroyed && ball.getRect().intersects(b.getRect())) {
                    b.hit();

                    // xác định hướng nảy đúng
                    double ballCenterX = ball.x + ball.size / 2.0;
                    double ballCenterY = ball.y + ball.size / 2.0;
                    double brickCenterX = b.x + b.width / 2.0;
                    double brickCenterY = b.y + b.height / 2.0;
                    double dx = Math.abs(ballCenterX - brickCenterX);
                    double dy = Math.abs(ballCenterY - brickCenterY);
                    if (dx > dy) ball.dx = -ball.dx;
                    else ball.dy = -ball.dy;

                    if (b.destroyed && !b.isUnbreakable()) {
                        score += 10;
                        PowerUp p = PowerUp.maybeDrop(b.x + b.width / 2, b.y + b.height / 2);
                        if (p != null) powerUps.add(p);
                    }
                    break;
                }
            }
        }

        // PowerUps (Luôn chạy, không phụ thuộc vào ballStuck)
        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            p.move();
            if (p.getRect().intersects(paddle.getRect())) {
                activatePowerUp(p.type);
                it.remove();
            } else if (p.y > HEIGHT) {
                it.remove();
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Chỉ vẽ game khi ở trạng thái IN_GAME
        if (loop.getCurrentState() == GameState.IN_GAME) {
            paddle.draw(g);
            ball.draw(g);
            level.draw(g);
            for (PowerUp p : powerUps) p.draw(g);

            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 20, 20);
            g.drawString("Bricks left: " + level.getRemainingBricks(), 20, 40);

            if (ballStuck && !gameOver)
                g.drawString("Click chuột để bắt đầu!", WIDTH / 2 - 80, HEIGHT / 2 + 100);

            if (gameOver) {
                g.setFont(new Font("Arial", Font.BOLD, 36));
                g.setColor(Color.RED);
                g.drawString("GAME OVER", WIDTH / 2 - 120, HEIGHT / 2);
                g.setFont(new Font("Arial", Font.PLAIN, 18));
                // Cập nhật hướng dẫn: Click để về menu
                g.drawString("Click để trở về Menu chính", WIDTH / 2 - 100, HEIGHT / 2 + 40);
            }
        }
        // Vẽ Menu hoặc Setting tùy thuộc vào trạng thái
        else if (loop.getCurrentState() == GameState.MENU) {
            loop.getMainMenu().render(g);
        } else if (loop.getCurrentState() == GameState.LEVEL_SELECT) {
            loop.getLevelSelectionMenu().render(g);
        } else if (loop.getCurrentState() == GameState.SETTING) {
            loop.getSettingMenu().render(g);
        }
    }

    // Trong GamePanel.java

// ... (Các thuộc tính khác)

    // Thêm thuộc tính kiểm soát FireBall
    private boolean isFireBall = false;

// ... (Các phương thức khác)

    private void activatePowerUp(String type) {
        switch (type) {
            case "EXTEND_PADDLE":
                // 1. Kéo dài Paddle
                paddle.width += 40;
                // Giới hạn chiều rộng tối đa của paddle để tránh quá lớn
                if (paddle.width > WIDTH - 50) {
                    paddle.width = WIDTH - 50;
                }
                break;

            case "FIRE_BALL":
                // 2. Chuyển bóng thành FireBall
                ball.setColor(Color.ORANGE);
                isFireBall = true;
                break;

            case "MULTI_BALL":
                // 3. Tạo thêm bóng (cần logic phức tạp hơn, tạm thời chỉ in thông báo)
                System.out.println("⚡ MULTI BALL: Kích hoạt tạo thêm bóng!");
                // Thêm logic tạo bóng phụ ở đây, ví dụ:
                // createNewBall(ball.x, ball.y, -ball.dx, ball.dy);
                break;
        }
    }

// ⚠️ LƯU Ý QUAN TRỌNG: Cần cập nhật logic va chạm gạch để xử lý FireBall
// Hiện tại, logic va chạm gạch của bạn đang kết thúc bằng 'break;'
// Logic FireBall phải cho phép bóng xuyên qua gạch yếu (WeakBrick)
// và chỉ bị bật lại khi chạm StrongBrick hoặc UnbreakableBrick.

    // ... (phần còn lại của GamePanel) ...

}