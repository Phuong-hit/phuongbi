package entities;

import java.awt.*;

public class Ball {
    public int x, y, size;
    public int dx = 3, dy = 3;
    private Color color;

// constructor
    public Ball(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

// cập nhật vị trí của Ball
    public void move() {
        x += dx;
        y += dy;
    }

// vẽ qủa bóng lên màn hình
    public void draw(Graphics g) {
        g.setColor(color); //màu
        g.fillOval(x, y, size, size);  // hình tròn
    }

    // hình chữ nhật để kiểm tra giao nhau dễ hơn
    public Rectangle getRect() {
        return new Rectangle(x, y, size, size);
    }

    //
    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // dùng thay dổi màu bóng cho powerups
    public void setColor(Color color) {
        this.color = color;
    }

    // hàm đc gọi khi ăn Multi ball để nó biết Ball
    // đang màu gì, ví dụ đang màu đỏ thì x2 bóng đỏ
    public Color getColor() {
        return this.color;
    }
}