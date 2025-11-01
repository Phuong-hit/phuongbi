package engine;

public enum GameState {
    MENU,            // Giao diện chính (Bắt đầu, Level, Cài đặt)
    LEVEL_SELECT,    // Màn hình chọn level
    SETTING,         // Màn hình cài đặt
    IN_GAME,         // Đang trong trận đấu
    PAUSE            // Tạm dừng trận đấu
}