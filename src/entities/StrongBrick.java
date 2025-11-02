package entities;

import java.awt.*;

public class StrongBrick extends Brick {
    public StrongBrick(int x, int y, int w, int h) {
        super(x, y, w, h, 3, Color.RED);
    }

    @Override
    public void hit() {
        super.hit();
        if (!destroyed) {
            // đổi màu nhạt dần khi yếu đi
            int r = Math.min(255, color.getRed() + 50);
            int g = Math.min(255, color.getGreen() + 30);
            color = new Color(r, g, color.getBlue());
        }
    }
}