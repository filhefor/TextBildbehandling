package tries;

import java.util.LinkedList;


public class SuffixTrie {
	private LinkedList<Node> root;

	public SuffixTrie(String T) {
		root = new LinkedList<Node>();

		for(int i = 0; i < T.length(); i++) {
			String suffix = T.substring(i);
//			System.out.println(suffix);
			put(suffix.toCharArray(), i);
		}
	}
		
	public void put(char[] chars, int val) {		
		LinkedList<Node> current_node = root;
		int N = chars.length;
		for(int i = 0; i < N; i++) {
			char c = chars[i];
			if(getNode(current_node, c) != null) {
				current_node = getNode(current_node, c);
			}else {
//				System.out.println("PUT: lägger till " + c + " med value: " + val);
				Node new_node = new Node(c,val);
				current_node.add(new_node);
				current_node = new_node.getList();
			}
		}
		current_node.add(new Node('$', chars.length));
	}
	
	public LinkedList<Node> getNode(LinkedList<Node> node, char c) {
		for(int i = 0; i < node.size(); i++) {
			if(node.get(i).getChar() == c) {
				return node.get(i).getList();
			}
		}
		return null;
	}
	
	public int getNodeValue(LinkedList<Node> node, char c) {
		for(int i = 0; i < node.size(); i++) {
			if(node.get(i).getChar() == c) {
				return node.get(i).getValue();
			}
		}
		return -1;
	}
	
	public int longestMatch(String P) {
		LinkedList<Node> current_node = root;
		char[] pat = P.toCharArray();
		char c;
		int index = -1;
		if(getNode(current_node,pat[0]) == null) {
			System.out.println("Pattern finns inte alls");
			return index;
		}
		String s = "";
		for(int i = 0; i < pat.length; i++) {
			c = pat[i];
			if(getNode(current_node, c) != null) {
				s += c;
				System.out.println(s + " finns");
				index = getNodeValue(current_node,c);
				current_node = getNode(current_node, c);	
			}else {
				s += c;
				System.out.println(s + " finns inte");
				break;
			}
		}
		
		return index;
	}
	
	public boolean get(String s) {
		LinkedList<Node> current_node = root;
		int N = s.length();
		for(int i = 0; i < N; i++) {
			char c = s.charAt(i);
			if(getNode(current_node, c) != null) {
				current_node = getNode(current_node, c);
			}else {
				return false;
			}
		}
		if(getNode(current_node, '$') != null) {
			return true;
		}else {
			return false;
		}
	}
}
