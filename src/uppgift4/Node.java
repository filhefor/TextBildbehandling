package uppgift4;

import java.util.LinkedList;

public class Node {
	private LinkedList<Node> list;
	private char ch;
	private int value;
	
	public Node(char c, int val) {
		ch = c;
		value = val;
		list = new LinkedList<Node>();
	}

	public LinkedList<Node> getList() {
		return list;
	}
	
	public char getChar() {
		return ch;
	}
	
	public int getValue() {
		return value;
	}
}
