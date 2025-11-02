package ui;

import engine.GamePanel;
import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel panel) {
        setTitle("Arkanoid - Revamp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true); // setVisible(true) phải là dòng cuối cùng
    }
}