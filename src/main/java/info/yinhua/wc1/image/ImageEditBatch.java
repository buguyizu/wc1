package info.yinhua.wc1.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.filters.Canvas;
import net.coobird.thumbnailator.filters.ImageFilter;
import net.coobird.thumbnailator.filters.Transparency;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import net.coobird.thumbnailator.resizers.configurations.AlphaInterpolation;
import net.coobird.thumbnailator.util.BufferedImages;
import info.yinhua.wc1.image.imageFilter.CanvasAlpha;

// https://github.com/coobird/thumbnailator
// https://github.com/blueimp/jQuery-File-Upload/wiki
public class ImageEditBatch {

	static String BASE = ".\\img";
	static String OUTPUT = BASE + "\\output\\";
	static String FORMAT = "png";
	static String SUFFIX = "-thumbnail";

	static String WATERMARK_FILE = ".\\img\\w\\HTB1Ke.png";
	static String WATERMARK_OUTPUT = BASE + "\\wm\\";

	protected final Logger log = LoggerFactory.getLogger(getClass());

	// arg: 1.square, 2.watermark
	public static void main(String[] arg) {

		ImageEditBatch b = new ImageEditBatch();
		if (arg.length > 0 && "1".equals(arg[0])) {
			// 1.square
			File outputFile = new File(OUTPUT);

			if (outputFile.exists())
				for (File f : outputFile.listFiles())
					f.delete();
			else
				outputFile.mkdir();

			if (arg.length > 1 && arg[1].matches("\\d+")) {
				b.square(BASE, Integer.parseInt(arg[1]));
			} else {
				b.square(BASE, -1);
			}
		} else if (arg.length > 0 && "2".equals(arg[0])) {
			// 2.watermark
			File outputFile2 = new File(WATERMARK_OUTPUT);

			if (outputFile2.exists())
				for (File f : outputFile2.listFiles())
					f.delete();
			else
				outputFile2.mkdir();

			b.watermark(BASE);
		} else {
			b.other();
		}
	}

	// 加水印
	public void watermark(String path) {

		try {
			BufferedImage watermarkImage = ImageIO.read(new File(WATERMARK_FILE));
			for (File f : new File(path).listFiles()) {
				if (!f.isFile())
					continue;

				String fileName = f.getName(), name = fileName.substring(0, fileName.indexOf("."));
				BufferedImage image = ImageIO.read(f);

				Thumbnails.of(image).imageType(BufferedImage.TYPE_INT_ARGB)
						.watermark(Positions.TOP_RIGHT, watermarkImage, 0.8f).scale(1).outputFormat(FORMAT)
						.toFile(WATERMARK_OUTPUT + name + SUFFIX);

				log.info("Output: " + WATERMARK_OUTPUT + name + SUFFIX);
			}
			log.info("watermark done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 不改变比例，调整成方形，周边透明。
	public void square(String path, int side) {
		try {

			for (File f : new File(path).listFiles()) {
				if (!f.isFile())
					continue;

				String fileName = f.getName(), name = fileName.substring(0, fileName.indexOf("."));
				BufferedImage image = ImageIO.read(f);

				int width = image.getWidth(), height = image.getHeight(), squareSide;
				if (width > height)
					squareSide = width;
				else
					squareSide = height;

				if (side > 0) {
					squareSide = side;
				}

				// 方法一，用定义的ImageFilter。
				// ImageFilter transparency = new Transparency(1);
				ImageFilter canvas = new Canvas(squareSide, squareSide, Positions.CENTER);

				// 方法二，手动建立透明BufferedImage，然后绘制原图。
				// BufferedImage pngImage = new BufferedImage(squareSide,
				// squareSide, BufferedImage.TYPE_INT_ARGB);
				// Graphics2D g = pngImage.createGraphics();
				// g.setComposite(AlphaComposite.Clear);
				// g.fillRect(0, 0, squareSide, squareSide);
				//
				// g.setComposite(AlphaComposite.Src);
				// g.drawImage(image, 20, 0, null);
				//// g.drawRenderedImage(image, null);
				// g.dispose();

				Thumbnails.of(image).imageType(BufferedImage.TYPE_INT_ARGB).addFilter(canvas).scale(1)
						.outputFormat(FORMAT).toFile(OUTPUT + name + SUFFIX);
				log.info("Output: " + OUTPUT + name + SUFFIX);
			}
			log.info("square done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void test() {

		try {

			BufferedImage originalImage = ImageIO
					.read(new File("C:\\Users\\n\\Downloads\\clothing\\racing\\test\\2030970151.jpg"));

			Thumbnails.of(new File("C:\\Users\\n\\Downloads\\clothing\\racing\\test").listFiles()).size(640, 480)
					.outputFormat("png").toFiles(Rename.NO_CHANGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void other() {
		log.info("Do nothing!");
	}
}
