package tries;

public class TestSuffixTrie {
	public static void main(String[] args) {
		String t = "bananas";
		String pattern1 = "an";
		String pattern2 = "anax";
		SuffixTrie st = new SuffixTrie(t);
		System.out.println("T: " + t + "\n");
		System.out.println("Pattern: " + pattern1);
		System.out.println(pattern1 + " börjar på position" + st.longestMatch(pattern1));
//		System.out.println("Längsta prefixlängd av " + pattern1 + " som finns i " + t + ": " + st.longestMatch(pattern1));
		System.out.println("\nPattern: " + pattern2);
		System.out.println(pattern2 + " börjar på position " + st.longestMatch(pattern2));
//		System.out.println("Längsta prefixlängd av " + pattern2 + " som finns i " + t + ": " + st.longestMatch(pattern2));
	}
}
