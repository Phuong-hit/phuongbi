package levels;

import entities.StrongBrick;
import entities.WeakBrick;

public class Level2 extends Level {

    @Override
    protected void createLevel() {
        int cols = 10, rows = 5;
        int startX = 50, startY = 50;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * brickWidth;
                int y = startY + r * brickHeight;

                if (r == 0 || r == 4) {
                    bricks.add(new StrongBrick(x, y, brickWidth, brickHeight));
                } else if (c % 2 == 0) {
                    bricks.add(new StrongBrick(x, y, brickWidth, brickHeight));
                } else {
                    bricks.add(new WeakBrick(x, y, brickWidth, brickHeight));
                }
            }
        }
    }
}