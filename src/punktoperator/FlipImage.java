package punktoperator;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klassen tar en bild på input, ändrar varje pixels contrast och brightness 
 * och skriver ut den ändrade bilden
 * @author filip heidfors
 *
 */
public class FlipImage {
//	private static BufferedImage image;
//
//	public static BufferedImage getImage() {
//		return image;
//	}
	
	/**
	 * Metoden ändrar varje pixel i en originalbild och skriver ut den nya bilden 
	 * @param img Originalbilden
	 * @param c Contrast
	 * @param b Brightness
	 */
	public static void flipImage(BufferedImage img, float c, int b) {		
		//rescaleOp.filter gör att för varje komponent i varje pixel, multiplicera med c och plussa med b.
		// g(x) = c * f(x) + b, där f(x) är komponenten i pixeln och c är kontrast värdet och b är brightness
		RescaleOp rescaleOp = new RescaleOp(c, b, null); 
		img = rescaleOp.filter(img, null);
		
		//Skriv ut den ändrade bilden
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
