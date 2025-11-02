package levels;

import entities.Brick;
import java.awt.*;
import java.util.ArrayList;

/**
 * Lớp cơ sở (abstract) cho tất cả các level.
 */
public abstract class Level {

    public ArrayList<Brick> bricks = new ArrayList<>();

    // Cung cấp các hằng số chung cho các lớp con
    protected final int brickWidth = 70;
    protected final int brickHeight = 20;

    /**
     * Constructor này đc gọi bởi các lớp con
     * Để gọi createLevel() để xây dựng các LV
     */
    public Level() {
        createLevel();
    }

    protected abstract void createLevel();

    // Phương thức vẽ gạch
    public void draw(Graphics g) {
        for (Brick b : bricks) b.draw(g);
    }

    // Phương thức đếm số gạch còn lại
    public int getRemainingBricks() {
        int count = 0;
        for (Brick b : bricks)
            if (!b.destroyed && !b.isUnbreakable()) count++;
        return count;
    }
}