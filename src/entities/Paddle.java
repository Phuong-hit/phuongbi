package entities;

import java.awt.*;

public class Paddle {
    public int x, y, width, height;
    private Color color;
    private boolean left, right;
    private int speed = 6;

    public Paddle(int x, int y, int width, int height, Color color) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.color = color;
    }

    public void update() {
        if (left) x -= speed;
        if (right) x += speed;
        if (x < 0) x = 0;
        if (x + width > 800) x = 800 - width;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public Rectangle getRect() { return new Rectangle(x, y, width, height); }
}
