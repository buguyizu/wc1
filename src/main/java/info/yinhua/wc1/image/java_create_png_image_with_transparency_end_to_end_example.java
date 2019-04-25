// https://stackoverflow.com/questions/17271812/save-buffered-image-with-transparent-background

package info.yinhua.wc1.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// ready to use end to end example
// it will create png picture with transparency and 2 x rectangles
// compilation time - 2019_04_10__00_12_03_236
public class java_create_png_image_with_transparency_end_to_end_example {

    public static void main(String[] args) throws IOException {
        Path outPath = Paths.get(".\\img\\");
        if (!Files.exists(outPath)) {
            Files.createDirectory(outPath);
        }

        String filename = "test_png_pic__.png";
        File absOutFile = outPath.resolve(filename).toFile();

        int width = 300;
        int height = 300;

        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, width, height);

        g2d.setComposite(AlphaComposite.Src);
        int alpha = 127; // 50% transparent
        g2d.setColor(new Color(255, 100, 100, alpha));
        g2d.fillRect(100, 100, 123, 123);

        g2d.setColor(new Color(0, 0, 0));
        g2d.fillRect(30, 30, 60, 60);

        g2d.dispose();

        ImageIO.write(bufferedImage, "png", absOutFile);
        System.out.println("File saved to:");
        System.out.println(absOutFile);
    }
}