package entities;

import java.awt.*;

public abstract class Brick {
    public int x, y, width, height;
    protected int health;
    protected Color color;
    public boolean destroyed = false;

    public Brick(int x, int y, int width, int height, int health, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.color = color;
    }

    public void hit() {
        if (health == Integer.MAX_VALUE) return; // Unbreakable
        health--;
        if (health <= 0) destroyed = true;
    }

    public boolean isUnbreakable() {
        return health == Integer.MAX_VALUE;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        if (!destroyed) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
    }

    public int getHealth() {
        return health;
    }
}
