package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path)); // ✅ Đọc file từ ổ đĩa thật
        } catch (IOException e) {
            System.err.println(" Không thể đọc ảnh: " + path);
            e.printStackTrace();
            return null;
        }
    }
}
