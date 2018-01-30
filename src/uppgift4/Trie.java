package uppgift4;

import java.util.LinkedList;

public class Trie {
	//En länkad lista med rotens barnnoder (tom från början)
	private LinkedList<Node> root;

	public Trie() {
		root = new LinkedList<Node>();
	}
	
	/**
	 * Metoden sätter in en sträng i trien
	 * @param s Strängen som ska sättas in
	 */
	public void put(String s) {		
		LinkedList<Node> current_node = root; //Länkad lista över barnnoder för aktuell nod (börjar på roten)
		int N = s.length();
		//Loopa igenom strängen tecken för tecken och lägg till dem i aktuell nod
		for(int i = 0; i < N; i++) {
			char c = s.charAt(i); 
			/*
			 * Om aktuell nods länkade lista har en nod vars värde/value är samma som aktuellt tecken
			 * (om aktuell nod har en barnnod med aktuellt tecken)
			 * sätt aktuell node till den barnnoden
			 */
			if(getNode(current_node, c) != null) { 
				current_node = getNode(current_node, c);
			}else { //Annars så skapa en ny nod och lägg till den till aktuell nods länkade lista över barnnoder och sätt den som aktuell nod
				Node new_node = new Node(c);
				current_node.add(new_node);
				current_node = new_node.getList();
			}
		}
		/*
		 * När alla tecken har lagts till så lägg till en ny nod i aktuell nods länkade lista över barnnoder,
		 *  vars värde är ett dollartecken. Detta är för att visa att den här noden representerar ett ord som
		 *  är tillagt i trien
		 */
		current_node.add(new Node('$'));
	}
	
	/*
	 * Metoden loopar igenom en nods länkade lista med barnnoder
	 * och kollar om någon av dom har ett värde/vale som är samma som ett specifikt tecken (c)
	 * och returnerar den noden om det gör det, annars returnerar den null
	 */
	public LinkedList<Node> getNode(LinkedList<Node> node, char c) {
		for(int i = 0; i < node.size(); i++) {
			if(node.get(i).getChar() == c) {
				return node.get(i).getList();
			}
		}
		return null;
	}
	
	/*
	 * Metoden kollar om en söksträng s finns i trien
	 */
	public boolean get(String s) {
		LinkedList<Node> current_node = root; //Sätt aktuell nod till rooten där vi börjar
		int N = s.length();
		for(int i = 0; i < N; i++) {
			char c = s.charAt(i);
			/**
			 * För varje tecken i söksträngen, kolla om aktuell nods länkade lista har en barnnod
			 * vars värde/value är samma som aktuellt tecken i söksträngen. 
			 * Om det är sant, sätt aktuell nod till den barnnoden och fortsätt med nästa tecken.
			 * Om aktuell nods länkade lista över barnnoder inte innehåller någon nod som har samma värde/value är det mismatch. 
			 * Söksträngen finns då inte i trien
			 */
			if(getNode(current_node, c) != null) { 
				current_node = getNode(current_node, c);
			}else {
				return false;
			}
		}
		/*
		 * När vi loopat igenom varje tecken i söksträngen utan att det blev mismatch,
		 * kolla om aktuell nods länkade lista över barnnoder innehåller en nod vars värde/value är ett dollartecken.
		 * Isåfall är det matchning. Om det inte finns en nod som innehåller ett dollartecken så är söksträngen bara
		 * en delsträng (prefix) av ett tillagd ord i trien. Returnera då false för mismatch och true för match
		 */
		if(getNode(current_node, '$') != null) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * Inre klass som representerar en nod. Varje nod har ett värde samt en länkad list över sina barnnoder
	 */
	private class Node {
		private LinkedList<Node> list;
		private char ch;
		
		private Node(char c) {
			ch = c;
			list = new LinkedList<Node>();
		}

		private LinkedList<Node> getList() {
			return list;
		}
		
		private char getChar() {
			return ch;
		}
	}
}
