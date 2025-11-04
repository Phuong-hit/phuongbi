package levels;

import entities.WeakBrick;

public class Level1 extends Level {

    @Override
    protected void createLevel() {
        int cols = 10, rows = 4;  // tạo 10 cột 4 hàng
        int startX = 50, startY = 50;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * brickWidth; // Dùng biến từ lớp cha Level
                int y = startY + r * brickHeight; // Dùng biến từ lớp cha Level
                bricks.add(new WeakBrick(x, y, brickWidth, brickHeight));
            }
        }
    }
}