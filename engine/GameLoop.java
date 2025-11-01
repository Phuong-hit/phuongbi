package engine;

import ui.MainMenu;
import ui.SettingMenu;
import ui.LevelSelectionMenu;

public class GameLoop implements Runnable {
    private GamePanel panel;
    private boolean running = true;
    private GameState currentState;
    private MainMenu mainMenu;
    private SettingMenu settingMenu;
    private LevelSelectionMenu levelSelectionMenu;
    private boolean isSoundOn = true;

    public GameLoop(GamePanel panel) {
        this.panel = panel;
        this.currentState = GameState.MENU;
        this.mainMenu = new MainMenu(this);
        this.settingMenu = new SettingMenu(this);
        this.levelSelectionMenu = new LevelSelectionMenu(this);

        // thao tác chuột (chỉ cho MainMenu)
        panel.addMouseListener(mainMenu);
        panel.addMouseMotionListener(mainMenu);
    }

    @Override
    public void run() {
        while (running) {
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

    //GETTER & SETTER MỚI CHO TRẠNG THÁI VÀ CÀI ĐẶT
    public GameState getCurrentState() { return currentState; }

    public void setGameState(GameState newState) {
        this.currentState = newState;
        panel.clearListeners();

        if (newState == GameState.MENU) {
            panel.addMouseListener(mainMenu);
            panel.addMouseMotionListener(mainMenu);
        } else if (newState == GameState.SETTING) {
            panel.addMouseListener(settingMenu);
        } else if (newState == GameState.LEVEL_SELECT) {
            panel.addMouseListener(levelSelectionMenu);
        } else if (newState == GameState.IN_GAME) {
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
    }

    // Getter cho các menu
    public MainMenu getMainMenu() { return mainMenu; }
    public SettingMenu getSettingMenu() { return settingMenu; }
    public LevelSelectionMenu getLevelSelectionMenu() { return levelSelectionMenu; }
    // --------------------------------------------------------
}