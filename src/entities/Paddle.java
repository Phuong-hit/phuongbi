package entities;

import java.awt.*;

public class Paddle {
    public int x, y, width, height;
    private Color color;
    private boolean left, right;
    private int speed = 6;

    //constructor
    public Paddle(int x, int y, int width, int height, Color color) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.color = color;
    }

    // di chuyển Paddle
    public void update() {
        if (left) x -= speed;
        if (right) x += speed;
        if (x < 0)   // chekc chạm tưởng
        if (x + width > 800) x = 800 - width;
    }

    // vẽ paddle
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    // tạo vùng va chạm
    public Rectangle getRect() { return new Rectangle(x, y, width, height); }
}