package levels;

//lớp LvFac tạo ra những đối tượng LV
public class LevelFactory {

    //switch case để chọn Lv
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
                // luôn trả về Lv 1 nếu số không hợp lệ
                return new Level1();
        }
    }
}