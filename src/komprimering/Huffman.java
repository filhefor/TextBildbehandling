package komprimering;

import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

/**
 * Klassen kpmprimerar en sträng med hjälp av prefixfri variabellängskodning/huffmankodning
 * @author filip heidfors
 *
 */
public class Huffman {
	private HashMap<Character, Integer> freq; //Hashmap över frequencies för varje tecken
	//Hashmap över varje teckens kod 
	private HashMap<Character,String> charBitstring = new HashMap<Character,String>(); 
	private int originalLength;

	/**
	 * Konstruktor
	 * @param input Strängen som ska komprimeras
	 */
	public Huffman(String input) {
		originalLength = input.length()*8;
		freq = calcFreq(input);
		compress(input);
	}
	
	/**
	 * Metoden kollar hur ofta varje tecken i input förekommer och sparar i en hashmap
	 * @param input Strängen som ska komprimeras
	 * @return Hashmapen som innehåller frekvenserna för varje tecken
	 */
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
		MinPQ<Node> pq = new MinPQ<Node>(); //Prioritetskö som tar bort noden med lägst värde först
		ArrayList<Character> chars = new ArrayList<Character>();
		System.out.println("FREQUENCIES");
		//För varje tecken i strängen
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			//Om tecknet förekommer minst en gång och tecknet inte redan lagts till chars
			if (freq.get(c) > 0 && !chars.contains(c)) {
				System.out.println(c + ": " + freq.get(c));
				pq.insert(new Node(c, freq.get(c), null, null)); //Lägg då till det i prioritetskön
			}
			
			chars.add(c);
		}
		System.out.println("");
		
		//Medan prioritetskön har minst 2 noder
		while (pq.size() > 1) { 
			//Ta bort dom två noderna med lägst frequencies och spara i x och y
			Node x = pq.delMin();
			Node y = pq.delMin();
			//Skapa en föräldernod till x och y vars frequency är barnens frequencies tillsammans/plussade
			Node parent = new Node('\0', x.freq + y.freq, x, y);
			pq.insert(parent); //Lägg in föräldernoden i prioritetskön
		}
		return pq.delMin();
	}
	
	/**
	 * Metoden bygger rekursivt bitsträngar/variabellängskod för varje tecken med
	 * hjälp av huffmanträdet
	 * @param x Aktuell nod
	 * @param s Bitsträngen/variabellängdskoden hittills
	 */
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
	
	/**
	 * Metoden komprimerar text med hjälp av hashmapen freq som tidigare byggts
	 * @param input Texten som ska komprimeras
	 */
	public void compress(String input) {
		Node root = buildTrie(freq, input); //Bygg ett huffmanträd med hjälp av freq
		
		System.out.println("BOKSTÄVERNAS BITSTRINGS:");
		buildCode(root, ""); //Bygg bitsträngar/variabellängdskoder med hjälp av huffmanträd
		System.out.println();
		
		char[] c = input.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < c.length; i++) {
			sb.append(charBitstring.get(c[i]));
		}
		double cFactor = ((double)sb.length()/(double)originalLength);
		System.out.println("Hela inputs bitstring: " + sb.toString() + "\nInputs komprimerade storlek: " + sb.length() + " bits\n"
				+ "Komprimeringsfaktor: " + Math.round(cFactor*100) + "% av originalsträngens storlek");
	}
	
	/**
	 * Inre klass som representerar en nod i huffmanträdet
	 * @author filip heidfors
	 *
	 */
	private static class Node implements Comparable<Node> {
		private char ch;
		private int freq;
		private final Node left, right;
		
		/**
		 * Konstruktor
		 * @param ch Nodens tecken
		 * @param freq Tecknets frequency/hur ofta tecknet förekommer
		 * @param left Barnnod till vänster i huffmanträdet
		 * @param right Barnnod till höger i huffmanträdet
		 */
		Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}
		
		/**
		 * Kollar om noden är ett löv
		 * @return True om det är ett löv, annars false
		 */
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		public int compareTo(Node that) {
			return this.freq - that.freq;
		}
	}

	public static void main(String[] args) {
		String input = "abcdefffffffffffffffgh";
		System.out.println("INPUT: " + input + "\nInputs originalstorlek: " + input.length()*8 + "\n");
		new Huffman(input);
	}
}
