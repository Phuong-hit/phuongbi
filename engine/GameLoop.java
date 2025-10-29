package engine;

import ui.MainMenu;
import ui.SettingMenu;
import ui.LevelSelectionMenu;

public class GameLoop implements Runnable {
    private GamePanel panel;
    private boolean running = true;

    // --- THÊM VÀO ---
    private GameState currentState;
    private MainMenu mainMenu;
    private SettingMenu settingMenu;
    private LevelSelectionMenu levelSelectionMenu;

    private boolean isSoundOn = true; // Biến cài đặt âm thanh
    // ----------------

    public GameLoop(GamePanel panel) {
        this.panel = panel;

        //  KHỞI TẠO CÁC OBJECT MENU VÀ TRẠNG THÁI ---
        this.currentState = GameState.MENU;
        this.mainMenu = new MainMenu(this);
        this.settingMenu = new SettingMenu(this);
        this.levelSelectionMenu = new LevelSelectionMenu(this);

        // Gắn lắng nghe chuột ban đầu (chỉ cho MainMenu)
        panel.addMouseListener(mainMenu);
        panel.addMouseMotionListener(mainMenu);
        // ---------------------------------------------
    }

    @Override
    public void run() {
        while (running) {
            // Chỉ update game khi đang ở trạng thái IN_GAME
            if (currentState == GameState.IN_GAME) {
                panel.updateGame();
            }
            panel.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }

    // --- GETTER & SETTER MỚI CHO TRẠNG THÁI VÀ CÀI ĐẶT ---
    public GameState getCurrentState() { return currentState; }

    public void setGameState(GameState newState) {
        this.currentState = newState;

        // Thay đổi Mouse Listener phù hợp với trạng thái mới
        // Xóa tất cả listener cũ trước khi thêm listener mới
        panel.clearListeners();

        if (newState == GameState.MENU) {
            panel.addMouseListener(mainMenu);
            panel.addMouseMotionListener(mainMenu);
        } else if (newState == GameState.SETTING) {
            panel.addMouseListener(settingMenu);
        } else if (newState == GameState.LEVEL_SELECT) {
            panel.addMouseListener(levelSelectionMenu);
        } else if (newState == GameState.IN_GAME) {
            // Thêm lại listener điều khiển paddle và click start
            panel.addGameListeners();
        }
    }

    // Phương thức để GamePanel bắt đầu một level cụ thể
    public void startLevel(int levelNumber) {
        panel.startGame(levelNumber);
        setGameState(GameState.IN_GAME);
    }

    // Quản lý âm thanh
    public boolean isSoundOn() { return isSoundOn; }
    public void toggleSound() {
        this.isSoundOn = !this.isSoundOn;
        System.out.println("Sound is now: " + (isSoundOn ? "ON" : "OFF"));
        // Thêm logic bật/tắt âm thanh thực tế ở đây
    }

    // Getter cho các menu (để GamePanel có thể vẽ chúng)
    public MainMenu getMainMenu() { return mainMenu; }
    public SettingMenu getSettingMenu() { return settingMenu; }
    public LevelSelectionMenu getLevelSelectionMenu() { return levelSelectionMenu; }
    // --------------------------------------------------------
}