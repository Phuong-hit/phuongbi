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
    private MouseMotionAdapter gameMouseMotionListener;
    private MouseAdapter gameMouseListener;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        loop = new GameLoop(this);
        new Thread(loop).start();

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
                        // Trở về Menu
                        loop.setGameState(GameState.MENU);
                    }
                }
            }
        };
    }

    // Phương thức xóa tất cả các Mouse Listener
    public void clearListeners() {
        for (MouseListener ml : getMouseListeners()) {
            removeMouseListener(ml);
        }
        // Xóa tất cả MouseMotionListener
        for (MouseMotionListener mml : getMouseMotionListeners()) {
            removeMouseMotionListener(mml);
        }
    }

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

    public void updateGame() {
        if (gameOver) return;

        //Cập nhật vị trí Paddle
        paddle.update();

        //LOGIC CẬP NHẬT VỊ TRÍ BÓNG
        if (ballStuck) {
            // Cập nhật vị trí bóng theo Paddle
            ball.x = paddle.x + paddle.width / 2 - ball.size / 2;
            ball.y = paddle.y - ball.size;
        } else {
            ball.move();
            //Logic
            // Tường
            if (ball.x <= 0 || ball.x + ball.size >= WIDTH) ball.dx = -ball.dx;
            if (ball.y <= 0) ball.dy = -ball.dy;

            // Dưới (Game Over)
            if (ball.y + ball.size >= HEIGHT) {
                gameOver = true;
                System.out.println("GAME OVER - Returning to Menu");
                return; // Dừng update khi Game Over
            }

            // Paddle
            if (ball.getRect().intersects(paddle.getRect())) {
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

        // PowerUps
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
        // Vẽ Menu hoặc Setting
        else if (loop.getCurrentState() == GameState.MENU) {
            loop.getMainMenu().render(g);
        } else if (loop.getCurrentState() == GameState.LEVEL_SELECT) {
            loop.getLevelSelectionMenu().render(g);
        } else if (loop.getCurrentState() == GameState.SETTING) {
            loop.getSettingMenu().render(g);
        }
    }
    //  kiểm soát FireBall
    private boolean isFireBall = false;


    private void activatePowerUp(String type) {
        switch (type) {
            case "EXTEND_PADDLE":
                //Kéo dài Paddle
                paddle.width += 40;
                // Giới hạn chiều rộng tối đa của paddle để tránh quá lớn
                if (paddle.width > WIDTH - 50) {
                    paddle.width = WIDTH - 50;
                }
                break;

            case "FIRE_BALL":

                ball.setColor(Color.ORANGE);
                isFireBall = true;
                break;

            case "MULTI_BALL":

                System.out.println("⚡ MULTI BALL: Kích hoạt tạo thêm bóng!");
                break;
        }
    }
}