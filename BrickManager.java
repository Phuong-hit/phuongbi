import java.awt.*;

public class BrickManager {
    private Brick[][] bricks;
    private int rows, cols;

    public BrickManager(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        bricks = new Brick[rows][cols];
        int brickWidth = 80, brickHeight = 25;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bricks[i][j] = new Brick(
                        60 + j * (brickWidth + 5),
                        50 + i * (brickHeight + 5),
                        brickWidth,
                        brickHeight
                );
            }
        }
    }

    public void draw(Graphics g) {
        for (Brick[] row : bricks)
            for (Brick b : row)
                b.draw(g);
    }

    public void checkCollision(Ball ball) {
        for (Brick[] row : bricks) {
            for (Brick b : row) {
                if (b.visible && ball.getRect().intersects(b.getRect())) {
                    b.visible = false;
                    ball.vy = -ball.vy;
                    return;
                }
            }
        }
    }
}
