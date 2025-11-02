package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;

public class ImageLoader {

    /**
     * Tải BufferedImage từ đường dẫn resource.
     * @param path Đường dẫn tuyệt đối từ gốc resources (ví dụ: "/images/background.png").
     * @return BufferedImage đã tải, hoặc null nếu lỗi.
     */
    public static BufferedImage loadImage(String path) {
        try {
            // Sử dụng ClassLoader để tải từ resource folder
            InputStream is = ImageLoader.class.getResourceAsStream(path);

            if (is == null) {
                System.err.println("Không tìm thấy file resource: " + path);
                return null;
            }

            System.out.println("ImageLoader: Đã tải ảnh thành công từ: " + path);
            return ImageIO.read(is);

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file ảnh: " + path);
            e.printStackTrace();
            return null;
        }
    }
}