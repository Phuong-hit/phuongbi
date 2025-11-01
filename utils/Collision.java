package utils;

import entities.Ball;
import entities.Paddle;
import entities.Brick;

import java.awt.*;

public class Collision {

    /**
     * Kiểm tra va chạm giữa bóng và thanh paddle.
     * Nếu chạm, đảo hướng Y của bóng (nảy lên).
     */
    public static void checkPaddleCollision(Ball ball, Paddle paddle) {
        Rectangle ballRect = ball.getRect();
        Rectangle paddleRect = paddle.getRect();

        if (ballRect.intersects(paddleRect)) {
            // Đưa bóng lên trên paddle để tránh bị "kẹt"
            ball.y = paddle.y - ball.size;
            // Đảo hướng di chuyển theo trục Y
            ball.dy = -Math.abs(ball.dy);
        }
    }

    public static int checkBrickCollision(Ball ball, java.util.List<Brick> bricks) {
        int scoreGain = 0;

        for (Brick b : bricks) {
            if (!b.destroyed && ball.getRect().intersects(b.getRect())) {
                b.destroyed = true;
                ball.dy = -ball.dy;
                scoreGain += 10;
            }
        }

        return scoreGain;
    }

    public static void checkWallCollision(Ball ball, int panelWidth, int panelHeight) {
        if (ball.x <= 0 || ball.x + ball.size >= panelWidth) {
            ball.dx = -ball.dx;
        }
        if (ball.y <= 0) {
            ball.dy = -ball.dy;
        }
    }

    public static boolean isBallLost(Ball ball, int panelHeight) {
        return ball.y + ball.size >= panelHeight;
    }
}
