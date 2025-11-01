package entities;

public enum BrickType {
    NORMAL(1),
    WEAK(1),
    STRONG(3),
    UNBREAKABLE(Integer.MAX_VALUE);

    private final int hitPoints;

    BrickType(int hp) {
        this.hitPoints = hp;
    }

    public int getHitPoints() {
        return hitPoints;
    }
}
