import engine.GamePanel;
import ui.GameWindow;
import javax.swing.SwingUtilities;

/**
 * Lớp chính để khởi chạy game.
 * BẮT BUỘC dùng SwingUtilities.invokeLater để tránh màn hình trắng/đen.
 */
public class Main {

    public static void main(String[] args) {

        // Khởi chạy toàn bộ game trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Tạo GamePanel (chứa logic game)
                GamePanel panel = new GamePanel();

                // 2. Tạo GameWindow (cửa sổ JFrame)
                new GameWindow(panel);
            }
        });
    }
}