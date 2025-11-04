package entities;

import java.awt.*;

public abstract class Brick {
    public int x, y, width, height;
    protected int health;
    protected Color color;
    public boolean destroyed = false;

    //constructor
    public Brick(int x, int y, int width, int height, int health, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.color = color;
    }

    //gamePanel sẽ gọi mỗi khi xảy ra va chạm
    public void hit() {
        if (health == Integer.MAX_VALUE) {
            return; // k thể phá hủy
        }
        else {
            health--;
        }
        if (health <= 0) destroyed = true;
    }
// gạch k thể phá, máu = gt siêu lớn
    public boolean isUnbreakable() {
        return health == Integer.MAX_VALUE;
    }
// để check va chạm dễ hơn
    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    //hàm vẽ, vẽ những gạch ch bị phá hủy
    public void draw(Graphics g) {
        if (!destroyed) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
    }
}
