package punktoperator;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FlipImage {
	private static BufferedImage image;

	public static BufferedImage getImage() {
		return image;
	}

	public static void flipImage(BufferedImage img, float c, int b) {		
		RescaleOp rescaleOp = new RescaleOp(c, b, null);
		img = rescaleOp.filter(img, null);
		
		try {
			ImageIO.write(img, "PNG", new File("images/changed_image.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}

	public static void main(String[] args) {
		File file = new File("images/green_boat.jpg");
		try {
			BufferedImage img = ImageIO.read(file);
			flipImage(img, -1.0f, 255);
			//ImageIO.write(getImage(), "PNG", new File("images/changed_image3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
