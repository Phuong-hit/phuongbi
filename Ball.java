import java.awt.*;

public class Ball {
    public double x, y;
    public int size;
    public double vx, vy;

    public Ball(double x, double y, int size, double vx, double vy) {
        this.x = x; this.y = y; this.size = size; this.vx = vx; this.vy = vy;
    }

    public void update() {
        x += vx;
        y += vy;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) x, (int) y, size, size);
    }

    public Rectangle getRect() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    public double getCenterX() { return x + size / 2.0; }
}

