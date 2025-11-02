package levels;

import entities.StrongBrick;
import entities.UnbreakableBrick;
import entities.WeakBrick;

public class Level5 extends Level {

    @Override
    protected void createLevel() {
        int cols = 10, rows = 8;
        int startX = 50, startY = 50;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * brickWidth;
                int y = startY + r * brickHeight;

                if (r % 2 == 0 && c % 2 == 0) {
                    bricks.add(new UnbreakableBrick(x, y, brickWidth, brickHeight));
                } else if (r % 2 != 0 && c % 2 != 0) {
                    bricks.add(new StrongBrick(x, y, brickWidth, brickHeight));
                } else {
                    bricks.add(new WeakBrick(x, y, brickWidth, brickHeight));
                }
            }
        }
    }
}