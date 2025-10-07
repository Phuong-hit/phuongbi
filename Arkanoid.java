import javax.swing.JFrame;

public class Arkanoid {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Arkanoid Game 502 ");
        GamePanel panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
