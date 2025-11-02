package levels;

/**
 * Lớp Factory tạo ra đối tượng Level
 * Đây là nơi duy nhất chứa câu lệnh 'switch' để chọn Level.
 */
public class LevelFactory {

    /**
     * Phương thức static để chọn Lv cụ thể
     */
    public static Level getLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return new Level1();
            case 2:
                return new Level2();
            case 3:
                return new Level3();
            case 4:
                return new Level4();
            case 5:
                return new Level5();
            default:
                // Mặc định luôn trả về Level 1 nếu số không hợp lệ
                return new Level1();
        }
    }
}