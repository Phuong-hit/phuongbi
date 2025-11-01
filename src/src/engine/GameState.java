package engine;

/**
 * Định nghĩa các trạng thái hoạt động của trò chơi (Menu, Cài đặt, Trong game, v.v.).
 */
public enum GameState {
    MENU,            // Giao diện chính (Bắt đầu, Level, Cài đặt)
    LEVEL_SELECT,    // Màn hình chọn level
    SETTING,         // Màn hình cài đặt
    IN_GAME,         // Đang trong trận đấu
    PAUSE            // Tạm dừng trận đấu
}