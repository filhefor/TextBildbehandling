package uppgift5;

import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Huffman {
	private HashMap<Character, Integer> freq;
	private HashMap<Character,String> charBitstring = new HashMap<Character,String>();
	private static int R = 256;

	public Huffman(String input) {
		freq = calcFreq(input);
		compress(input);
	}

	public HashMap<Character, Integer> calcFreq(String input) {
		HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (freq.containsKey(c)) {
				int newVal = freq.get(c);
				freq.put(c, newVal + 1);
			} else {
				freq.put(c, 1);
			}
		}
		return freq;
	}

	private static Node buildTrie(HashMap<Character, Integer> freq, String input) {
		// Initialize priority queue with singleton trees.
		MinPQ<Node> pq = new MinPQ<Node>();
		ArrayList<Character> chars = new ArrayList<Character>();
		System.out.println("FREQUENCIES");
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);

			if (freq.get(c) > 0 && !chars.contains(c)) {
				System.out.println(c + ": " + freq.get(c));
				pq.insert(new Node(c, freq.get(c), null, null));
			}
			
			chars.add(c);
		}
		System.out.println("");

		while (pq.size() > 1) { // Merge two smallest trees.
			Node x = pq.delMin();
			Node y = pq.delMin();
			Node parent = new Node('\0', x.freq + y.freq, x, y);
			pq.insert(parent);
		}
		return pq.delMin();
	}

	private void buildCode(Node x, String s) {
		if (x.isLeaf()) {
//			st[x.ch] = s;
			charBitstring.put(x.ch, s);
			System.out.println(x.ch + ": " + charBitstring.get(x.ch));
			return;
		}
		buildCode(x.left, s + '0');
		buildCode(x.right, s + '1');
	}

	public void compress(String input) {
		// Build Huffman code trie.
		Node root = buildTrie(freq, input);
		
		System.out.println("BOKSTÄVERNAS BITSTRINGS:");
		buildCode(root, "");
		System.out.println();
		
		char[] c = input.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < c.length; i++) {
			sb.append(charBitstring.get(c[i]));
		}
		System.out.println("Hela inputs bitstring: " + sb.toString() + "\nInputs komprimerade längd: " + sb.length() + " bits");
	}

	private static class Node implements Comparable<Node> {
		private char ch; // unused for internal nodes
		private int freq; // unused for expand
		private final Node left, right;

		Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
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

	public static void main(String[] args) {
		String input = "abcdefffffffffffffffgh";
		System.out.println("INPUT: " + input + "\n");
		new Huffman(input);
	}
}
