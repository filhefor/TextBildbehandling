package tries;

import java.util.LinkedList;

/**
 * Klassen representerar ett suffixtrie. Metoder som bygger ett suffixtrie av en specifik sträng.
 * Hitta längsta prefix av en söksträng och startpositionen på den söksträngen
 * @author filip heidfors
 *
 */
public class SuffixTrie {
	private LinkedList<Node> root;
	
	/**
	 * Konstruktor. Lägger in alla suffix av en sträng i suffixtrie.
	 * @param T strängen det ska byggas suffixtrie av
	 */
	public SuffixTrie(String T) {
		root = new LinkedList<Node>();
		String suffix;
		
		for(int i = 0; i < T.length(); i++) {
			suffix = T.substring(i);
//			System.out.println(suffix);
			put(suffix.toCharArray(), i);
		}
	}
	
	/**
	 * Metoden lägger in ett suffix i suffixtrie.
	 * @param chars Array av tecknen i suffixet
	 * @param val Värde som representerar suffixets startposition i originalsträngen.
	 */
	public void put(char[] chars, int val) {		
		LinkedList<Node> current_node = root; //Länkad lista över barnnoder (börjar på roten)
		int N = chars.length;
		//För varje tecken i suffixet
		for(int i = 0; i < N; i++) {
			char c = chars[i];
			if(getNode(current_node, c) != null) { //Om nuvarande nod har en barnnod som innehåller aktuellt tecken
				current_node = getNode(current_node, c); //Sätt den noden till nuvarande nod
			}else { //Annars skapa ny nod och sätt den noden som nuvarande nod
//				System.out.println("PUT: lägger till " + c + " med value: " + val);
				Node new_node = new Node(c,val);
				current_node.add(new_node);
				current_node = new_node.getList();
			}
		}
		/*
		 * När alla tecken lagts till, lägg även till ett dollartecken
		 * på nuvarande nod (noden längst ner) för att berätta att denna noden
		 * representerar ett tillagt suffix i trien.
		 */
		current_node.add(new Node('$', chars.length)); 
	}
	
	/**
	 * Metoden kollar om en länkad lista över barnnoder innehåller en nod vars tecken (ch) är
	 * samma som ett specifikt tecken (c)
	 * @param node Länkade listan det ska kollas i
	 * @param c Tecknet som ska kollas om det finns
	 * @return Den hittade nodens länkade lista över barnnoder (eller null om ingen nod innehöll c)
	 */
	public LinkedList<Node> getNode(LinkedList<Node> node, char c) {
		for(int i = 0; i < node.size(); i++) {
			if(node.get(i).getChar() == c) {
				return node.get(i).getList();
			}
		}
		return null;
	}
	
	/**
	 * Metoden kollar om en länkad lista över barnnoder har en nod som innehåller ett specifikt tecken
	 * och returnerar isåfall den nodens värde (val), det vill säga vilket suffix noden tillhör,
	 * eller mer korrekt, startposition i originalsträngen som suffixet noden tillhör har.
	 * @param node Länkad lista över barnnoder det ska kollas i
	 * @param c Tecknet som ska kollas om det finns
	 * @return Startposition för suffixet som noden tillhör (eller -1 om det inte hittades en barnnod som innehöll c)
	 */
	public int getNodeValue(LinkedList<Node> node, char c) {
		for(int i = 0; i < node.size(); i++) {
			if(node.get(i).getChar() == c) {
				return node.get(i).getValue();
			}
		}
		return -1;
	}
	
	/**
	 * Metoden letar efter längsta prefix av en söksträng i suffixtriet.
	 * @param P Söksträngen
	 * @return Startposition i originalsträngen för längsta hittade prefix av söksträngen
	 */
	public int longestMatch(String P) {
		LinkedList<Node> current_node = root; //Börja på roten
		char[] pat = P.toCharArray();
		char c;
		int index = -1; 
		//Kolla direkt om första tecknet finns i roten för att se om det finns nåt prefix överhuvudtaget
		if(getNode(current_node,pat[0]) == null) { 
			System.out.println("Pattern finns inte alls");
			return index;
		}
		String s = "";
		//För varje tecken i söksträngen/pattern
		for(int i = 0; i < pat.length; i++) {
			c = pat[i];
			if(getNode(current_node, c) != null) { //om aktuell nods har en barnnod som innehåller aktuellt tecken
				s += c;
				System.out.println(s + " finns");
				index = getNodeValue(current_node,c); //Sätt index till aktuell nods värde
				current_node = getNode(current_node, c); //Sätt aktuell nod till den barnnod som innehöll aktuellt tecken
			}else { //Annars avsluta loopen
				s += c;
				System.out.println(s + " finns inte");
				break;
			}
		}
		
		return index;
	}
	
	
//	public boolean get(String s) {
//		LinkedList<Node> current_node = root;
//		int N = s.length();
//		for(int i = 0; i < N; i++) {
//			char c = s.charAt(i);
//			if(getNode(current_node, c) != null) {
//				current_node = getNode(current_node, c);
//			}else {
//				return false;
//			}
//		}
//		if(getNode(current_node, '$') != null) {
//			return true;
//		}else {
//			return false;
//		}
//	}
}
