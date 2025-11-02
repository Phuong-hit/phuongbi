package engine;

import entities.*;
import levels.Level;
import levels.LevelFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel {
    //Thuộc tính
    private Paddle paddle;
    private java.util.List<Ball> balls = new ArrayList<>();
    private Level level;
    private java.util.List<PowerUp> powerUps = new ArrayList<>();

    private boolean gameOver;
    private boolean gameWon = false;
    private boolean ballStuck;
    private int score;
    private int lives = 3;
    private int currentLevel;

    // Nút bấm
    private Rectangle replayButton;
    private Rectangle nextLevelButton;
    private Rectangle exitButton;
    private static final int MAX_LEVELS = 5;

    // Nút Cài đặt
    private Rectangle settingsCogButton;

    // Nút (Menu Pause)
    private Rectangle pauseResumeButton;
    private Rectangle pauseMuteButton;
    private Rectangle pauseMenuButton;

    // Timer
    private long powerUpTimer = 0;
    private static final long POWERUP_DURATION = 12000;

    // Trạng thái PowerUp
    private boolean isFireBall = false;
    private boolean isStrongBall = false;
    private int extendCount = 0;
    private static final int BASE_PADDLE_WIDTH = 100;

    // Cấu hình Game
    private GameLoop loop;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BALL_BASE_SPEED = 4;

    // Listeners
    private MouseMotionAdapter gameMouseMotionListener;
    private MouseAdapter gameMouseListener;


    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK); // Nền đen
        setFocusable(true);

        loop = new GameLoop(this);
        new Thread(loop).start();

        // Khởi tạo vị trí các nút Win/Loss
        int buttonWidth = 140;
        int buttonHeight = 50;
        int buttonY = HEIGHT / 2 + 100;
        replayButton = new Rectangle(WIDTH / 2 - buttonWidth - 10, buttonY, buttonWidth, buttonHeight);
        nextLevelButton = new Rectangle(WIDTH / 2 + 10, buttonY, buttonWidth, buttonHeight);
        exitButton = new Rectangle(WIDTH / 2 + 10, buttonY, buttonWidth, buttonHeight);

        // Khởi tạo vị trí các nút Pause
        int pauseBtnWidth = 250;
        int pauseBtnHeight = 50;
        int pauseBtnX = WIDTH / 2 - (pauseBtnWidth / 2);
        pauseResumeButton = new Rectangle(pauseBtnX, HEIGHT / 2 - 80, pauseBtnWidth, pauseBtnHeight);
        pauseMuteButton = new Rectangle(pauseBtnX, HEIGHT / 2, pauseBtnWidth, pauseBtnHeight);
        pauseMenuButton = new Rectangle(pauseBtnX, HEIGHT / 2 + 80, pauseBtnWidth, pauseBtnHeight);

        // Khởi tạo nút setting
        settingsCogButton = new Rectangle(WIDTH - 50, 10, 40, 40);

        // Listener di chuyển Paddle
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

        // Listener click chuột
        gameMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GameState state = loop.getCurrentState();

                if (state == GameState.IN_GAME) {

                    // Kiểm tra click setting
                    if (settingsCogButton.contains(e.getPoint())) {
                        loop.getSoundManager().play("ClickSound");
                        loop.setGameState(GameState.PAUSE);
                        return;
                    }

                    if (ballStuck && !balls.isEmpty()) {
                        ballStuck = false;
                        balls.get(0).setDirection(3, -3);
                    } else if (gameWon) {
                        if (replayButton.contains(e.getPoint())) {
                            loop.getSoundManager().play("ClickSound");
                            loop.startLevel(currentLevel);
                        } else if (nextLevelButton.contains(e.getPoint())) {
                            loop.getSoundManager().play("ClickSound");
                            if (currentLevel < MAX_LEVELS) {
                                loop.startLevel(currentLevel + 1);
                            } else {
                                loop.setGameState(GameState.MENU);
                            }
                        }
                    } else if (gameOver) {
                        if (replayButton.contains(e.getPoint())) {
                            loop.getSoundManager().play("ClickSound");
                            loop.startLevel(currentLevel);
                        } else if (exitButton.contains(e.getPoint())) {
                            loop.getSoundManager().play("ClickSound");
                            System.exit(0);
                        }
                    }
                }
                // Xử lý click khi PAUSE
                else if (state == GameState.PAUSE) {
                    if (pauseResumeButton.contains(e.getPoint())) {
                        loop.getSoundManager().play("ClickSound");
                        loop.setGameState(GameState.IN_GAME);
                    } else if (pauseMuteButton.contains(e.getPoint())) {
                        loop.getSoundManager().play("ClickSound");
                        loop.toggleSound();
                    } else if (pauseMenuButton.contains(e.getPoint())) {
                        loop.getSoundManager().play("ClickSound");
                        loop.setGameState(GameState.MENU);
                    }
                }
            }
        };
    }

    // Xóa Listeners
    public void clearListeners() {
        for (MouseListener ml : getMouseListeners()) {
            removeMouseListener(ml);
        }
        for (MouseMotionListener mml : getMouseMotionListeners()) {
            removeMouseMotionListener(mml);
        }
    }

    // Thêm Listeners
    public void addGameListeners() {
        addMouseListener(gameMouseListener);
        addMouseMotionListener(gameMouseMotionListener);
    }

    // Bắt đầu Game
    public void startGame(int levelNumber) {
        this.currentLevel = levelNumber;
        paddle = new Paddle(WIDTH / 2 - 50, HEIGHT - 50, BASE_PADDLE_WIDTH, 10, Color.WHITE);
        balls.clear();
        balls.add(new Ball(paddle.x + paddle.width / 2 - 10, paddle.y - 20, 20, Color.WHITE)); // Bóng trắng

        level = LevelFactory.getLevel(levelNumber);

        powerUps.clear();
        score = 0;
        gameOver = false;
        gameWon = false;
        ballStuck = true;
        lives = 3;
        isFireBall = false;
        isStrongBall = false;
        extendCount = 0;
        powerUpTimer = 0; // Reset timer
    }

    // Reset khi mất mạng
    private void resetAfterLifeLost() {
        paddle.x = WIDTH / 2 - 50;
        paddle.width = BASE_PADDLE_WIDTH;
        balls.clear();
        Ball newBall = new Ball(paddle.x + paddle.width / 2 - 10, paddle.y - 20, 20, Color.WHITE); // Bóng trắng
        balls.add(newBall);
        ballStuck = true;
        isFireBall = false;
        isStrongBall = false;
        extendCount = 0;
        powerUpTimer = 0; // Reset timer
        powerUps.clear();
    }

    // Tắt PowerUp
    private void deactivatePowerUps() {
        System.out.println("PowerUp hết thời gian!");
        paddle.width = BASE_PADDLE_WIDTH;

        isFireBall = false;
        isStrongBall = false;
        for (Ball b : balls) {
            b.setColor(Color.WHITE); // Reset về màu trắng
        }

        extendCount = 0;
        powerUpTimer = 0; // Tắt timer
    }


    // Vòng lặp cập nhật Game
    public void updateGame() {
        if (gameOver || gameWon) return;

        if (powerUpTimer > 0 && System.currentTimeMillis() - powerUpTimer > POWERUP_DURATION) {
            deactivatePowerUps();
        }

        paddle.update();

        if (ballStuck) {
            if (!balls.isEmpty()) {
                Ball mainBall = balls.get(0);
                // Cập nhật vị trí bóng DÍNH theo Paddle
                mainBall.x = paddle.x + paddle.width / 2 - mainBall.size / 2;
                mainBall.y = paddle.y - mainBall.size;
            }
        } else { // Chỉ di chuyển và va chạm khi bóng đã rời paddle
            Iterator<Ball> ballIterator = balls.iterator();
            while (ballIterator.hasNext()) {
                Ball ball = ballIterator.next();

                int prevBallBottomY = ball.y + ball.size;
                ball.move();

                // 1. Va chạm Tường
                if (ball.x <= 0 || ball.x + ball.size >= WIDTH) ball.dx = -ball.dx;
                if (ball.y <= 0) ball.dy = -ball.dy;
                // 2. Bóng rơi
                if (ball.y + ball.size >= HEIGHT) {
                    ballIterator.remove();
                    continue;
                }

                // 3. Va chạm Paddle
                if (ball.getRect().intersects(paddle.getRect())) {
                    if (ball.dy > 0 && prevBallBottomY <= paddle.y) {
                        double hitPoint = (ball.x + ball.size / 2.0) - (paddle.x + paddle.width / 2.0);
                        double normalizedHit = hitPoint / (paddle.width / 2.0);
                        if (normalizedHit > 1.0) normalizedHit = 1.0;
                        if (normalizedHit < -1.0) normalizedHit = -1.0;
                        double maxBounceAngle = Math.PI / 3;
                        double bounceAngle = normalizedHit * maxBounceAngle;
                        ball.dx = (int) (BALL_BASE_SPEED * Math.sin(bounceAngle));
                        ball.dy = (int) (BALL_BASE_SPEED * -Math.cos(bounceAngle));
                        if (ball.dy == 0) ball.dy = -BALL_BASE_SPEED;
                        ball.y = paddle.y - ball.size;
                        loop.getSoundManager().play("BallTap");
                    } else {
                        if (ball.dx > 0) {
                            ball.x = paddle.x - ball.size;
                        } else {
                            ball.x = paddle.x + paddle.width;
                        }
                        ball.dx = -ball.dx;
                        loop.getSoundManager().play("BallTap");
                    }
                }

                // 4. Va chạm Bricks
                Iterator<Brick> brickIterator = level.bricks.iterator();
                while (brickIterator.hasNext()) {
                    Brick b = brickIterator.next();
                    if (!b.destroyed && ball.getRect().intersects(b.getRect())) {
                        boolean bounce = true;
                        if (isFireBall && !b.isUnbreakable()) {
                            bounce = false;
                        }
                        if (isStrongBall && b instanceof StrongBrick) {
                            b.hit();
                        }
                        b.hit();
                        if (b.destroyed && !b.isUnbreakable()) {
                            score += 10;
                            PowerUp p = PowerUp.maybeDrop(b.x + b.width / 2, b.y + b.height / 2);
                            if (p != null) powerUps.add(p);
                            loop.getSoundManager().play("BallTap");
                        } else if (!b.destroyed && bounce) {
                            loop.getSoundManager().play("BallTap");
                        }
                        if (bounce) {
                            Rectangle ballRect = ball.getRect();
                            Rectangle brickRect = b.getRect();
                            Rectangle intersection = ballRect.intersection(brickRect);

                            if (intersection.width < intersection.height) {
                                ball.dx = -ball.dx;
                                if (ball.x < brickRect.x) ball.x = brickRect.x - ball.size;
                                else ball.x = brickRect.x + brickRect.width;
                            } else {
                                ball.dy = -ball.dy;
                                if (ball.y < brickRect.y) ball.y = brickRect.y - ball.size;
                                else ball.y = brickRect.y + brickRect.height;
                            }
                            break;
                        }
                    }
                }
            }

            // Xử lý Mất mạng
            if (balls.isEmpty() && !gameOver) {
                lives--;
                if (lives <= 0) {
                    gameOver = true;
                    loop.getSoundManager().play("GameOverSound");
                } else {
                    resetAfterLifeLost();
                }
            }

            // Kiểm tra Thắng
            if (level.getRemainingBricks() == 0) {
                gameWon = true;
                balls.clear();
                loop.getSoundManager().play("LevelCompleteSound");
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
}