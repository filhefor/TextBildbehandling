package komprimering;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.text.html.HTMLDocument.Iterator;

import edu.princeton.cs.algs4.MinPQ;

public class HuffmanImage {
	private HashMap<String, Integer> freq;
	private HashMap<String, String> rgbBitstring = new HashMap<String, String>();
	private String[] completeBinaryString = {"","0", "00", "000", "0000", "00000", "000000", "0000000"};

	public HuffmanImage(BufferedImage img) {
		freq = calcFreq(img);
		compress(img);
	}

	public HashMap<String, Integer> calcFreq(BufferedImage img) {
		HashMap<String, Integer> freq = new HashMap<String, Integer>();
		WritableRaster raster = img.getRaster();
		StringBuilder originalBitstring = new StringBuilder();
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				// int r = raster.getSample(i, j, 0);
				String red = Integer.toBinaryString(raster.getSample(i, j, 0));
				String green = Integer.toBinaryString(raster.getSample(i, j, 1));
				String blue = Integer.toBinaryString(raster.getSample(i, j, 2));

				if(red.length() < 8) {
					int index = 8-red.length();
					red = completeBinaryString[index] + red;
				}
				if(green.length() < 8) {
					int index = 8-green.length();
					green = completeBinaryString[index] + green;
				}
				if(blue.length() < 8) {
					int index = 8-blue.length();
					blue = completeBinaryString[index] + blue;
				}

				String pixelString = red + green + blue;
				originalBitstring.append(pixelString);
				// System.out.println(pixelString);
				if (freq.containsKey(pixelString)) {
					int newVal = freq.get(pixelString);
					freq.put(pixelString, newVal + 1);
				} else {
					freq.put(pixelString, 1);
				}
			}
		}
		int bit = (int) Math.ceil(Math.log(freq.size()) / Math.log(2));
		System.out.println("Bildens okomprimerade storlek: " + (double) (originalBitstring.length() / 8 ) / 1000000);
		System.out.println("Antal färger i bilden: " + freq.size() + "\nBinärkodnings storlek: "
				+ (double) (bit * img.getWidth() * img.getHeight()) / 8000000);
		return freq;
	}

	private static Node buildTrie(HashMap<String, Integer> freq) {
		MinPQ<Node> pq = new MinPQ<Node>();
		System.out.println("FREQUENCIES");
		for (String s : freq.keySet()) {
			// System.out.println(s + ": " + freq.get(s));
			pq.insert(new Node(s, freq.get(s), null, null));
		}

		System.out.println();

		while (pq.size() > 1) { 
			Node x = pq.delMin();
			Node y = pq.delMin();
			Node parent = new Node("\0", x.freq + y.freq, x, y);
			pq.insert(parent);
		}
		return pq.delMin();
	}

	private void buildCode(Node x, String s) {
		if (x.isLeaf()) {
			rgbBitstring.put(x.pixelBitstring, s);
			// System.out.println(x.rgb + ": " + rgbBitstring.get(x.rgb) + "
			// bits: " + rgbBitstring.get(x.rgb).length());
			return;
		}
		buildCode(x.left, s + '0');
		buildCode(x.right, s + '1');
	}

	public void compress(BufferedImage img) {
		Node root = buildTrie(freq);

		// System.out.println("PIXELVÄRDENAS BITSTRINGS:");
		buildCode(root, "");
		System.out.println();

		StringBuilder sb = new StringBuilder();
		WritableRaster raster = img.getRaster();
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				String red = Integer.toBinaryString(raster.getSample(i, j, 0));
				String green = Integer.toBinaryString(raster.getSample(i, j, 1));
				String blue = Integer.toBinaryString(raster.getSample(i, j, 2));
				if(red.length() < 8) {
					int index = 8-red.length();
					red = completeBinaryString[index] + red;
				}
				if(green.length() < 8) {
					int index = 8-green.length();
					green = completeBinaryString[index] + green;
				}
				if(blue.length() < 8) {
					int index = 8-blue.length();
					blue = completeBinaryString[index] + blue;
				}
				String pixelString = red + green + blue;
				sb.append(rgbBitstring.get(pixelString));
				// for(int c = 0; c < 3; c++) {
				// sb.append(rgbBitstring.get(raster.getSample(i, j, c)));
				// }
			}
		}

		System.out.println("\nBildens komprimerade storlek: " + (double) sb.length() / 8000000);
	}

	private static class Node implements Comparable<Node> {
		private String pixelBitstring; 
		private int freq; 
		private final Node left, right;

		Node(String rgb, int freq, Node left, Node right) {
			this.pixelBitstring = rgb;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public int compareTo(Node that) {
			return this.freq - that.freq;
		}
	}

	public static void main(String[] args) throws IOException {
		File file = new File("images/green_boat.jpg");
		File file2 = new File("images/orange_flower.jpg");
		File file3 = new File("images/people.jpg");
		File file4 = new File("images/icebridge.jpg");
		File file5 = new File("images/colorful2.jpg");
		File file6 = new File("images/16mil.jpg");
		File file7 = new File("images/template1.png");
		File file8 = new File("images/singleBlack.jpg");
		BufferedImage img = ImageIO.read(file);
		new HuffmanImage(img);
	}
}
