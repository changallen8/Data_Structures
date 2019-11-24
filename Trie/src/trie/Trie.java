package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	private static int findPrefix(String word1, String word2) {
		int count = 0;
		for(int i = 0; i < word1.length() && i < word2.length(); i++) {
			if(word1.charAt(i) == word2.charAt(i)) count++;
			else break;
		}
		return count;
	}
	
	
	public static TrieNode buildTrie(String[] allWords) {
		TrieNode root = new TrieNode(null, null, null);
		TrieNode ptr = root.firstChild;
		TrieNode ptr2 = ptr;
		String prefix = "";
		
		if(allWords.length > 0) {
		TrieNode insert = new TrieNode(null, null, null);
		insert.substr = new Indexes(0, (short)0, (short)(allWords[0].length()-1));
		root.firstChild = insert;
		ptr = insert;
		}
		
		for(int i = 1; i<allWords.length; i++) {	
			ptr = root.firstChild;
			ptr2 = ptr;
			System.out.println("insert word: " + allWords[i]);
			System.out.println("Start ptr: "+allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex, ptr.substr.endIndex + 1));
			
			while (ptr != null) {
				
				prefix = allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex, ptr.substr.endIndex + 1);
				
				System.out.println("current prefix: "+prefix);			
				
				/* if (ptr.firstChild == null) { // second word
					System.out.println("leaf node");
					if(findPrefix(allWords[i].substring(ptr.substr.startIndex), prefix)>0) {
						System.out.println("match");
						TrieNode insert = new TrieNode(null, null, null);
						ptr.firstChild = insert;
						ptr.substr.endIndex = (short) (findPrefix(prefix,allWords[i].substring(ptr.substr.startIndex))+ptr.substr.startIndex-1);
						insert.substr = new Indexes( ptr.substr.wordIndex , (short) (ptr.substr.endIndex + 1) , (short) (allWords[ptr.substr.wordIndex].length() - 1));
						insert.sibling = new TrieNode( null , null , null);
						insert.sibling.substr = new Indexes( i , (short)(ptr.substr.endIndex + 1) , (short)(allWords[i].length() - 1));
						break;
					}
					
					
				} 
				
				else {*/ // subtree root

					if(findPrefix(allWords[i].substring(ptr.substr.startIndex),prefix) == prefix.length()) {
						System.out.println("child check");
						ptr = ptr.firstChild;
						ptr2 = ptr;
						continue;
					}
					//&& findPrefix(allWords[i].substring(ptr.substr.startIndex),prefix) < prefix.length()
					else if(findPrefix(allWords[i].substring(ptr.substr.startIndex), prefix)>0 ) {
						TrieNode temp = ptr.firstChild;
						System.out.println("partial");
						TrieNode insert = new TrieNode(null,temp,null);
						insert.substr = new Indexes(ptr.substr.wordIndex, (short)(ptr.substr.startIndex + findPrefix(allWords[i].substring(ptr.substr.startIndex),prefix)), ptr.substr.endIndex);
						ptr.substr.endIndex = (short) (findPrefix(allWords[i].substring(ptr.substr.startIndex),prefix)+ptr.substr.startIndex-1);
						ptr.firstChild = insert;
						insert.sibling = new TrieNode(null,null,null);
						insert.sibling.substr = new Indexes(i , (short)(ptr.substr.endIndex + 1) , (short)(allWords[i].length() - 1));
						break;
						
					}
					
					
				//}
				ptr = ptr.sibling;
				if(ptr!=null) {
					System.out.println("next");
					ptr2 = ptr;
				}
				if(ptr == null) {
					System.out.println("insert sibling");
					TrieNode insert = new TrieNode(null,null,null);
					insert.substr = new Indexes(i,ptr2.substr.startIndex,(short)(allWords[i].length() - 1));
					ptr2.sibling = insert;
				}
			}
			
		}
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return root;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		
		ArrayList<TrieNode> list = new ArrayList<TrieNode>();
		TrieNode ptr = root.firstChild;
		if(ptr==null) {
			return null;
		}
		while(ptr!=null) {
			if(findPrefix(prefix.substring(ptr.substr.startIndex), allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex,ptr.substr.endIndex + 1)) > 0) {
				
				System.out.println("num match: " + findPrefix(prefix.substring(ptr.substr.startIndex), allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex,ptr.substr.endIndex + 1)));
				
				if(findPrefix(prefix.substring(ptr.substr.startIndex), allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex,ptr.substr.endIndex + 1)) == prefix.substring(ptr.substr.startIndex).length()) {
					System.out.println("full match, recursive method called");
					list.addAll(helpCompleteList(ptr, allWords, prefix));
					return list;
				}
				else {
					System.out.println("partial match");
					ptr = ptr.firstChild;
				}
			}
			else {
				System.out.println("check sibling");
				ptr = ptr.sibling;
			}
			
		}
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return null;
	}
	
	//FIX THIS
	private static ArrayList<TrieNode> helpCompleteList(TrieNode root, String[] allWords, String prefix){
		ArrayList<TrieNode> list = new ArrayList<TrieNode>();
		if(root.firstChild == null) {
			list.add(root);
			return list;
		}
		TrieNode ptr = root.firstChild;
		TrieNode ptr2 = ptr;

		while (ptr2 != null) {
			
				if (ptr.firstChild != null) {
					list.addAll(helpCompleteList(ptr, allWords, prefix));
					
				} else {
					list.add(ptr);
				}
				
			ptr = ptr2.sibling;
			ptr2 = ptr;
			
		} 
		return list;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
