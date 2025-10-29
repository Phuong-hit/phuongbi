package entities;

import java.awt.*;
import java.util.Random;

public class PowerUp {
    public int x, y, size = 20;
    public int dy = 2;
    public String type;
    private Color color;

    public PowerUp(int x, int y, String type) {
        this.x = x; this.y = y;
        this.type = type;
        switch (type) {
            case "MULTI_BALL" -> color = Color.MAGENTA;
            case "FIRE_BALL" -> color = Color.ORANGE;
            case "EXTEND_PADDLE" -> color = Color.GREEN;
            default -> color = Color.WHITE;
        }
    }

    public void move() { y += dy; }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, size, size);
    }

    public static PowerUp maybeDrop(int x, int y) {
        if (new Random().nextDouble() < 0.2) { // 20% cơ hội rơi ra
            String[] types = {"MULTI_BALL", "FIRE_BALL", "EXTEND_PADDLE"};
            String type = types[new Random().nextInt(types.length)];
            return new PowerUp(x, y, type);
        }
        return null;
    }
}
