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
            case "STRONG_BALL" -> color = Color.RED.darker();
            case "ADD_LIFE" -> color = Color.CYAN;
            default -> color = Color.WHITE;
        }
    }

    public void move() { y += dy; }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10)); // Sửa

        String text = "";
        int offsetX = 0;
        int offsetY = 14;

        switch (type) {
            case "FIRE_BALL":
                text = "F";
                offsetX = 7;
                break;
            case "MULTI_BALL":
                text = "x2";
                offsetX = 5;
                break;
            case "EXTEND_PADDLE":
                text = "EX";
                offsetX = 4;
                break;
            case "STRONG_BALL":
                text = "S";
                offsetX = 7;
                break;
            case "ADD_LIFE":
                text = "L+";
                offsetX = 4;
                break;
        }

        g.drawString(text, x + offsetX, y + offsetY);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, size, size);
    }

    public static PowerUp maybeDrop(int x, int y) {
        if (new Random().nextDouble() < 0.09) {
            String[] types = {"MULTI_BALL", "FIRE_BALL", "EXTEND_PADDLE", "STRONG_BALL", "ADD_LIFE"};
            String type = types[new Random().nextInt(types.length)];
            return new PowerUp(x, y, type);
        }
        return null;
    }
}