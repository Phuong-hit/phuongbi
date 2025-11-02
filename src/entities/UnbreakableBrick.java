package entities;

import java.awt.*;

public class UnbreakableBrick extends Brick {
    public UnbreakableBrick(int x, int y, int w, int h) {
        super(x, y, w, h, Integer.MAX_VALUE, Color.GRAY);
    }

    @Override
    public void hit() {
        // Không thể bị phá
    }
}