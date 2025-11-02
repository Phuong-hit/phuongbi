package engine;

import ui.MainMenu;
import ui.SettingMenu;
import ui.LevelSelectionMenu;
import utils.SoundManager;

public class GameLoop implements Runnable {
    private GamePanel panel;
    private boolean running = true;

    private GameState currentState;
    private MainMenu mainMenu;
    private SettingMenu settingMenu;
    private LevelSelectionMenu levelSelectionMenu;
    private SoundManager soundManager;

    private boolean isSoundOn = true;

    public GameLoop(GamePanel panel) {
        this.panel = panel;

        this.currentState = GameState.MENU;
        this.mainMenu = new MainMenu(this);
        this.settingMenu = new SettingMenu(this);
        this.levelSelectionMenu = new LevelSelectionMenu(this);

        this.soundManager = new SoundManager();
        this.soundManager.loadSounds();

        if (isSoundOn) {
            soundManager.playMusic("NhacNen");
        }

        panel.addMouseListener(mainMenu);
        panel.addMouseMotionListener(mainMenu);
    }

    @Override
    public void run() {
        while (running) {
            // --- SỬA: Chỉ update khi ở IN_GAME ---
            if (currentState == GameState.IN_GAME) {
                panel.updateGame();
            }
            // (repaint() luôn chạy để vẽ cả menu pause)
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

    public GameState getCurrentState() { return currentState; }

    public void setGameState(GameState newState) {
        // --- SỬA: Xử lý nhạc nền khi chuyển trạng thái ---
        if (currentState == GameState.IN_GAME && newState != GameState.IN_GAME) {
            // Tạm dừng nhạc nếu rời khỏi game (ví dụ: về Menu)
            // (Tùy chọn: bạn có thể xóa nếu muốn nhạc nền chạy liên tục)
            // soundManager.stopMusic("NhacNen");
        }

        if (newState == GameState.MENU || newState == GameState.LEVEL_SELECT || newState == GameState.SETTING) {
            // Bật lại nhạc nền khi về Menu
            soundManager.playMusic("NhacNen");
        }
        // ----------------------------------------------

        this.currentState = newState;
        panel.clearListeners();

        if (newState == GameState.MENU) {
            panel.addMouseListener(mainMenu);
            panel.addMouseMotionListener(mainMenu);
        } else if (newState == GameState.SETTING) {
            panel.addMouseListener(settingMenu);
        } else if (newState == GameState.LEVEL_SELECT) {
            panel.addMouseListener(levelSelectionMenu);
        }
        // --- SỬA: PAUSE và IN_GAME dùng chung listener ---
        else if (newState == GameState.IN_GAME || newState == GameState.PAUSE) {
            panel.addGameListeners();
        }
    }

    public void startLevel(int levelNumber) {
        panel.startGame(levelNumber);
        setGameState(GameState.IN_GAME);
    }

    public boolean isSoundOn() { return isSoundOn; }

    public void toggleSound() {
        this.isSoundOn = !this.isSoundOn;
        // SoundManager sẽ tự xử lý việc tắt/bật nhạc
        soundManager.setMuted(!isSoundOn);

        if (isSoundOn) {
            System.out.println("Sound is now: ON");
        } else {
            System.out.println("Sound is now: OFF");
        }
    }

    public SoundManager getSoundManager() { return soundManager; }
    public MainMenu getMainMenu() { return mainMenu; }
    public SettingMenu getSettingMenu() { return settingMenu; }
    public LevelSelectionMenu getLevelSelectionMenu() { return levelSelectionMenu; }
}