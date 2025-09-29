// danh sách các thư viện
import javax.swing.*;   //tạo cửa sổ, nút bấm, menu, bảng ,...
import java.awt.*;     //các công cụ vẽ vd như màu, font ...
import java.awt.event.*; // nhận các input từ máy tính(chuột, bàn phím)
import java.util.ArrayList; // danh sách động

public class ArkanoidGame extends JFrame {
    public ArkanoidGame() {
        setTitle("Arkanoid Game"); //tên game
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ấn close để thoát
        setResizable(true);  // có thể thay đổi kích thước ô cửa sổ(to nhỏ)

        // nơi xử lí, vẽ game
        GamePanel panel = new GamePanel(); 
        add(panel);
        pack();

        setLocationRelativeTo(null); //cửa sổ game nằm giữa màn hình
        setVisible(true); //hiển thị cửa sổ
    }

