package entities;

import java.awt.*;

//constructor
public class StrongBrick extends Brick {
    public StrongBrick(int x, int y, int w, int h) {
        super(x, y, w, h, 3, Color.RED);
    }

    @Override
    public void hit() {
        super.hit();
        if (!destroyed) {
            // đổi màu nhạt dần khi yếu đi
            int r = Math.min(255, color.getRed() + 80);
            int g = Math.min(255, color.getGreen() + 50);
            color = new Color(r, g, color.getBlue()); //câp nhật lại màu của gạch
        }
    }
}