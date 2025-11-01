package levels;

import entities.*;
import java.awt.*;
import java.util.ArrayList;

public class Level {
    public ArrayList<Brick> bricks = new ArrayList<>();

    // Cập nhật constructor để nhận số level
    public Level(int levelNumber) {
        loadLevel(levelNumber);
    }

    // Phương thức tải level tương ứng
    private void loadLevel(int number) {
        int brickWidth = 70;
        int brickHeight = 20;

        switch (number) {
            case 1 -> createLevel1(brickWidth, brickHeight);
            case 2 -> createLevel2(brickWidth, brickHeight);
            case 3 -> createLevel3(brickWidth, brickHeight);
            default -> createLevel1(brickWidth, brickHeight); // Mặc định Level 1
        }
    }

    // Level 1
    private void createLevel1(int w, int h) {
        int cols = 10, rows = 3;
        int startX = 50, startY = 50;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * (w + 5);
                int y = startY + r * (h + 5);

                if (r == 0) bricks.add(new StrongBrick(x, y, w, h));
                else bricks.add(new WeakBrick(x, y, w, h));
            }
        }
    }

    // Level 2
    private void createLevel2(int w, int h) {
        int cols = 10, rows = 4;
        int startX = 50, startY = 50;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * (w + 5);
                int y = startY + r * (h + 5);

                if (r == 0 || r == 3) {
                    bricks.add(new UnbreakableBrick(x, y, w, h));
                } else if (c % 2 == 0) {
                    bricks.add(new StrongBrick(x, y, w, h));
                } else {
                    bricks.add(new WeakBrick(x, y, w, h));
                }
            }
        }
    }

    // Level 3: Khó
    private void createLevel3(int w, int h) {
        int cols = 10, rows = 5;
        int startX = 50, startY = 50;

        for (int r = 0; r < rows; r++) {
            int numBricks = 10 - 2 * Math.abs(2 - r);
            int offsetX = Math.abs(2 - r) * (w + 5);

            for (int c = 0; c < numBricks; c++) {
                int x = startX + offsetX + c * (w + 5);
                int y = startY + r * (h + 5);

                if (r == 2) {
                    bricks.add(new StrongBrick(x, y, w, h));
                } else {
                    bricks.add(new WeakBrick(x, y, w, h));
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (Brick b : bricks) b.draw(g);
    }

    public int getRemainingBricks() {
        int count = 0;
        for (Brick b : bricks)
            if (!b.destroyed && !b.isUnbreakable()) count++;
        return count;
    }
}