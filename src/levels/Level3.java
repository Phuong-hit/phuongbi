package levels;

import entities.StrongBrick;
import entities.UnbreakableBrick;
import entities.WeakBrick;

public class Level3 extends Level {

    @Override
    protected void createLevel() {
        int cols = 9; // Giảm tổng số cột xuống còn 9
        int rows = 5;

        // Căn giữa 9 cột ( (800 - 9*70) / 2 = 85 )
        int startX = 85;
        int startY = 50;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * brickWidth;
                int y = startY + r * brickHeight;

                // 1. Hàng đáy r=4 là gạch k thể vỡ toàn bộ 9 gạch
                if (r == 4) {
                    bricks.add(new UnbreakableBrick(x, y, brickWidth, brickHeight));
                }
                // 2. Hàng trên cùng r=0 là strong toàn bộ 9 gạch
                else if (r == 0) {
                    bricks.add(new StrongBrick(x, y, brickWidth, brickHeight));
                }
                // 3. Hàng r=1 và r=3
                else if (r == 1 || r == 3) {
                    if (c == 0 || c == 8) { // Bỏ cột đầu và cột cuối
                        continue;
                    }
                    bricks.add(new WeakBrick(x, y, brickWidth, brickHeight));
                }
                // 4. Hàng r=2 (Hàng giữa - Bỏ 2 cột rìa mỗi bên -> còn 5 gạch)
                else if (r == 2) {
                    if (c < 2 || c > 6) { // Bỏ 2 cột đầu và 2 cột cuối
                        continue;
                    }
                    // Gạch ở giữa c=4 là strong
                    if (c == 4) {
                        bricks.add(new StrongBrick(x, y, brickWidth, brickHeight));
                    } else {
                        bricks.add(new WeakBrick(x, y, brickWidth, brickHeight));
                    }
                }
            }
        }
    }
}