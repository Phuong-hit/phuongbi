Bài tập lớn game Arkanoid
Cấu trúc game Arkanoid
│
├── ArkanoidGame.java       (class chính, tạo cửa sổ, chạy game loop)
├── GamePanel.java          (kế thừa JPanel, vẽ game, update game)
├── Paddle.java             (điều khiển thanh trượt)
├── Ball.java               (quản lý quả bóng)
├── Brick.java              (một viên gạch)
└── BrickManager.java       (quản lý mảng các viên gạch)

Các lớp của game
Lớp ArkanoidGame
Vai trò: entry point (có main), tạo JFrame, thêm GamePanel.
Không xử lý logic, chỉ khởi động game.

Lớp GamePanel
Kế thừa JPanel, implements Runnable, KeyListener.
Chứa:
Một đối tượng Paddle
Một đối tượng Ball
Một BrickManager
Game loop (run): update → repaint.
paintComponent(Graphics g): vẽ paddle, ball, bricks.

Lớp Paddle
Thuộc tính:
x, y, width, height
speed
Phương thức:
moveLeft(), moveRight()
draw(Graphics g)
getRect() → trả về Rectangle để kiểm tra va chạm.

Lớp Ball
Thuộc tính:
x, y, vx, vy, radius
Phương thức:
update() → cập nhật vị trí
checkCollision(Paddle p) → bóng bật lại khi chạm paddle
checkCollision(Brick b) → bóng bật lại khi chạm gạch
draw(Graphics g)


Lớp Brick
Thuộc tính:
x, y, width, height
visible (true/false)
Phương thức:
draw(Graphics g)
getRect()

Lớp BrickManager
Quản lý danh sách (mảng 2D hoặc ArrayList<Brick>).
Khởi tạo các viên gạch theo hàng/cột.
Phương thức:
draw(Graphics g)
checkCollision(Ball ball) → duyệt qua bricks, nếu va chạm thì cho visible = false.




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

