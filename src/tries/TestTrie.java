package tries;

/**
 * Testklass för att testa Trie.java
 * @author filip heidfors
 *
 */
public class TestTrie {
	public static void main(String[] args) {
		Trie trie = new Trie();
		String s1 = "Sluta David";
		String s2 = "Hej";
		String s3 = "Otto Göransson";
		String s4 = "Testar";
		String s5 = "Test";
		String s6 = "Sluta"; 
		//Lägg till strängar i trien
		trie.put(s1); 
		trie.put(s2);
		trie.put(s3);
		//Sträng s1, s2 och s3 ska returnerna true för dom är tillagda i trien medan s4 returnerar false för den har inte lagts till
		System.out.println("Finns " + s1 + ": " + trie.get(s1));
		System.out.println("Finns " + s2 + ": " + trie.get(s2));
		System.out.println("Finns " + s3 + ": " + trie.get(s3));
		System.out.println("Finns " + s4 + ": " + trie.get(s4));
		trie.put(s4);
		//s4 true, s5 false
		System.out.println("Finns " + s4 + ": " + trie.get(s4));
		System.out.println("Finns " + s5 + ": " + trie.get(s5));
		trie.put(s5);
		//s5 true, s6 false
		System.out.println("Finns " + s5 + ": " + trie.get(s5));
		System.out.println("Finns " + s6 + ": " + trie.get(s6));
		
	}
}
