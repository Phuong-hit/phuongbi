package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;

/**
 * Class tiện ích để tải hình ảnh (ví dụ: hình nền menu) từ resource folder.
 */
public class ImageLoader {

    /**
     * Tải BufferedImage từ đường dẫn resource.
     * @param path Đường dẫn tương đối đến file ảnh (ví dụ: "/background.png").
     * @return BufferedImage đã tải, hoặc null nếu lỗi.
     */
    public static BufferedImage loadImage(String path) {
        try {
            // Sử dụng ClassLoader để tải từ resource folder (thường là cùng thư mục với code đã compile)
            InputStream is = ImageLoader.class.getResourceAsStream(path);
            if (is == null) {
                System.err.println("Không tìm thấy file resource: " + path);
                // Để test, bạn có thể thử tải từ đường dẫn file tuyệt đối nếu không dùng resource
                // Ví dụ: return ImageIO.read(new File(path));
                return null;
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file ảnh: " + path);
            e.printStackTrace();
            return null;
        }
    }
}