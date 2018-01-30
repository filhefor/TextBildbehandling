package tries;

import java.util.LinkedList;

/**
 * Klassen representerar en nod i huffmanträd
 * @author filip heidfors
 *
 */
public class Node {
	private LinkedList<Node> list;
	private char ch;
	private int value;
	
	/**
	 * Konstruktor som tar emot ett tecken och ett värde
	 * @param c Tecknet för noden
	 * @param val Värde för noden (vilket suffix tecknet tillhör)
	 */
	public Node(char c, int val) {
		ch = c;
		value = val;
		list = new LinkedList<Node>();
	}
	
	/**
	 * Metoden hämtar länkad lista över barnnoder
	 * @return Länkade listan med barnnoder
	 */
	public LinkedList<Node> getList() {
		return list;
	}
	
	/**
	 * Metoden hämtar nodens tecken
	 * @return
	 */
	public char getChar() {
		return ch;
	}
	
	/**
	 * Metoden hämtar nodens värde
	 * @return
	 */
	public int getValue() {
		return value;
	}
}
