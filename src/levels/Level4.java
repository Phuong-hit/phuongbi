package levels;

import entities.StrongBrick;
import entities.UnbreakableBrick;
import entities.WeakBrick;

public class Level4 extends Level {

    @Override
    protected void createLevel() {
        int rows = 6;
        int startX = 50, startY = 50;

        for (int r = 0; r < rows; r++) {
            int numBricks = 4 + 2 * Math.abs(2 - r);
            if (numBricks > 10) numBricks = 10;

            int offsetX = (10 - numBricks) * brickWidth / 2; // Căn giữa

            for (int c = 0; c < numBricks; c++) {
                int x = startX + offsetX + c * brickWidth;
                int y = startY + r * brickHeight;

                if (r == 0 || r == 5) {
                    bricks.add(new UnbreakableBrick(x, y, brickWidth, brickHeight));
                } else if (r == 2 || r == 3) {
                    bricks.add(new StrongBrick(x, y, brickWidth, brickHeight));
                } else {
                    bricks.add(new WeakBrick(x, y, brickWidth, brickHeight));
                }
            }
        }
    }
}