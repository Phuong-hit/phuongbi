package levels;

import entities.Brick;
import java.awt.*;
import java.util.ArrayList;

 // Lớp cơ sở (abstract) cho tất cả các level.
public abstract class Level {

    public ArrayList<Brick> bricks = new ArrayList<>();

    // kích thước gạch
    protected final int brickWidth = 70;
    protected final int brickHeight = 20;

    // tạo LV
    public Level() {
        createLevel();
    }

    protected abstract void createLevel();

    // vẽ gạch
    public void draw(Graphics g) {
        for (Brick b : bricks) b.draw(g);
    }

    // đếm số gạch còn lại
    public int getRemainingBricks() {
        int count = 0;
        for (Brick b : bricks)
            if (!b.destroyed && !b.isUnbreakable()) count++;  //đếm gach chua bị phá và
                                                              // gạch k phải gạch k thể phá
        return count;
    }
}