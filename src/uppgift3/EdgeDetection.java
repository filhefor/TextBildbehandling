package uppgift3;

import java.awt.Color;
import java.awt.Window.Type;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EdgeDetection {
	private int[][] pixelMatrix = new int[3][3];

	public EdgeDetection(BufferedImage img) {
		processImage(img);
	}

	public void processImage(BufferedImage img) {
		BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		image = img;
		WritableRaster inraster = image.getRaster();
		BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster outraster = newImage.getRaster();
		for (int i = 1; i < img.getWidth()-1; i++) {
			for (int j = 1; j < img.getHeight()-1; j++) {
				
				pixelMatrix[0][0] = inraster.getSample(i - 1, j - 1, 0);
				pixelMatrix[0][1] = inraster.getSample(i - 1, j, 0);
				pixelMatrix[0][2] = inraster.getSample(i - 1, j + 1, 0);
				pixelMatrix[1][0] = inraster.getSample(i, j - 1, 0);
				pixelMatrix[1][2] = inraster.getSample(i, j + 1, 0);
				pixelMatrix[2][0] = inraster.getSample(i + 1, j - 1, 0);
				pixelMatrix[2][1] = inraster.getSample(i + 1, j, 0);
				pixelMatrix[2][2] = inraster.getSample(i + 1, j + 1, 0);

				int conv = (int) convolution(pixelMatrix);
				outraster.setSample(i, j, 0, conv);
			}
		}
		
		BufferedImage binaryImg = binaryImage(newImage);
		try {
			ImageIO.write(newImage, "PNG", new File("images/grayscale.png"));
			ImageIO.write(binaryImg, "PNG", new File("images/binary.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double convolution(int[][] pixelMatrix) {
		int gy = (pixelMatrix[0][0] * -1) + (pixelMatrix[0][1] * -2) + (pixelMatrix[0][2] * -1) + (pixelMatrix[2][0] * 1)
				+ (pixelMatrix[2][1] * 2) + (pixelMatrix[2][2] * 1);
		int gx = (pixelMatrix[0][0] * 1) + (pixelMatrix[0][2] * -1) + (pixelMatrix[1][0] * 2) + (pixelMatrix[1][2] * -2)
				+ (pixelMatrix[2][0] * 1) + (pixelMatrix[2][2] * -1);
		return Math.sqrt(Math.pow(gy, 2) + Math.pow(gx, 2));
	}

	public BufferedImage binaryImage(BufferedImage img) {
		int t = 16;
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		WritableRaster inraster = img.getRaster();
		WritableRaster outraster = newImg.getRaster();
		
		for(int i = 0; i < img.getWidth(); i++) {
			for(int j = 0; j < img.getHeight(); j++) {
				if(inraster.getSample(i, j, 0) >= t) {
					outraster.setSample(i, j, 0, 0);
				}else {
					outraster.setSample(i, j, 0, 255);
				}
				
			}
		}
		
		return newImg;
	}

	public static void main(String[] args) {
		File file = new File("images/green_boat.jpg");
		try {
			BufferedImage img = ImageIO.read(file);
			new EdgeDetection(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
