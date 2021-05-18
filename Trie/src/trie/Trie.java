package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Ashwin Anand aa2041 aa2041Ascarletmail.rutgers.edu
 *
**/                                                            
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
	 * 
	 **/                                                   
	public static TrieNode buildTrie(String[] allWords) {

		/** COMPLETE THIS METHOD            
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
        **/

		// Creating head for tree to be built off
		TrieNode head = new TrieNode(null,null,null);
        
		// checking length of word list and returning true if null file
		if(allWords.length==0) 
		{
			return head;
		}
        
	    // making first branch of the tree
		head.firstChild = new TrieNode(new Indexes((0),(short) (0),(short) (allWords[0].length() - 1)), null, null);
        
        // Trie Node type refnode and previous to make  children and parent references later on when needed 
		TrieNode previous = head.firstChild;
		TrieNode refnode = head.firstChild;
		
		// int variables coming from head of tree and up 
		int words = -1;
		int start = -1;
		int end = -1;
		int similaruntil = -1;		
		
		// for loop to go through all words in list and make a tree
		for(int pos = 1; pos < allWords.length; pos++) 
		{
			
			// Holding current word that is going to be added on tree
			String currentword = allWords[pos];
			 
			// checking if a firstChild exists in the tree
			while (refnode!= null) 
			{ 
				// Setting index to match location of tree it has to be added towards
			    words = refnode.substr.wordIndex;
				start = refnode.substr.startIndex;
				end = refnode.substr.endIndex;
								
				// if loop to check if the difference between 2 same words is still before the word length ends
				if (start > currentword.length()) 
				{
					// setting node references based of loop
				    previous = refnode;
					refnode = refnode.sibling;

					// continuation of program after refereces are set up
					continue;
				}
				 
				// int value returned till where a word is matching the tree built
				similaruntil = samematch(allWords[words].substring(start, end + 1), currentword.substring(start));
				
				// if loop to check similarity number if true it will increase start by an unspecificed similar prefix amount
				if(similaruntil!=-1) 
				{
				    similaruntil+=start;
				}
				
				// if loop to check similarity number if true it will make new refereces to next word for previous and refnode Nodes
				if(similaruntil==-1) 
				{
					previous = refnode;
					refnode = refnode.sibling;
				}

			    // else loop will go in if either if loops are not met
				else 
				{
					// if loop to check whether the similaruntil variable is equal to last letter in word , 
					// if true then it will make new references for the previous and refnode Nodes
					if (similaruntil == end) 
					{
						previous = refnode;
						refnode = refnode.firstChild;
					}

					// else loop will go in if the full word is longer than  similaruntil count
					else 
					{
						 previous = refnode;
						 break;
					}
				} 
			}
			 
			// if loop to check whether refnode node has no reference assigned to it 
			if(refnode==null) 
			{
				Indexes indexref = new Indexes(pos, (short) start, (short) (currentword.length() - 1));
				previous.sibling = new TrieNode(indexref, null, null);
			}
			
			// else loop will go in if refnode has a reference assigned to it 
			else 
			{

				Indexes currpos = previous.substr;
				TrieNode currfchild = previous.firstChild;
		 
				Indexes currNewInd = new Indexes(currpos.wordIndex, (short) (similaruntil + 1), currpos.endIndex);
				currpos.endIndex = (short) similaruntil;
				previous.firstChild = new TrieNode(currNewInd, null, null);
				previous.firstChild.firstChild = currfchild;
				previous.firstChild.sibling = new TrieNode(new Indexes((short) pos, (short) (similaruntil + 1), (short) (currentword.length() - 1)), null, null);
			}
			 
			// reseting the values of variables that are going to be reused in the next for loop iteration
			previous = head.firstChild;
			refnode = head.firstChild;
			words = -1;
			start = -1; 
			end = -1; 
			similaruntil = -1;
		}
          
		// returning tree head node which contains all the words 
		return head;
	}
	
	// Helper method to check similarity between a word and prefix 
	private static int samematch(String str, String ins) 
	{

		int c = 0;

		// while loop to go through full word while counter c is being incremented based on similarity 
		while (c < str.length() && c < ins.length() && str.charAt(c) == ins.charAt(c))
		{
		  c++;
		}

		// returns how many letters in a word are similar to a prefix
		return (c - 1);

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
	**/                                                                            
	public static ArrayList<TrieNode> completionList(TrieNode root,String[] allWords, String prefix) {
		
		/** COMPLETE THIS METHOD                    
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
	    **/
		ArrayList<TrieNode> completed = new ArrayList<TrieNode>();

		if(prefix.length() == 0) {
			return retrieveSubstrings(root, allWords, prefix);
		}

		if(root.substr == null) {

			if(root.firstChild == null) {
				return null;
			} else {

				return completionList(root.firstChild, allWords, prefix);
			}
		}

		int prefixnumbers = prefixCheck(root, prefix, allWords);
		if(prefixnumbers == -1) {

			if (root.sibling == null) {
				return null;
			} else {
				return completionList(root.sibling, allWords, prefix);
			}
		}

		if (prefixnumbers == 1) {
              //while (root.firstChild!=null)
			  if (root.firstChild==null) 
				  completed.add(root);
			  else
				completed.addAll(retrieveSubstrings(root.firstChild, allWords, prefix));
			//	root=root.sibling;
			  }
		
		if (prefixnumbers == 2) {

			if(root.firstChild == null) {
				return null;
			}
			return completionList(root.firstChild, allWords, prefix);
		}

		if(completed.isEmpty()) {
			return null; }
		else {
			return completed;
		}
	
	//return completed;
}

	private static ArrayList<TrieNode> retrieveSubstrings(TrieNode ptr, String[] allWords, String prefix){

		ArrayList<TrieNode> temp = new ArrayList<TrieNode>();
		
	    if (ptr.firstChild ==null && ptr.sibling==null) {
			temp.add(ptr);
			return temp;
		}
		if (ptr.firstChild!=null && ptr.sibling==null) {
            temp.addAll(retrieveSubstrings(ptr.firstChild, allWords, prefix));
		}
	    if(ptr.firstChild == null && ptr.sibling!=null) {
			temp.add(ptr);
		    temp.addAll(retrieveSubstrings(ptr.sibling, allWords, prefix));
		}
		if (ptr.firstChild!=null && ptr.sibling!=null){
			temp.addAll(retrieveSubstrings(ptr.firstChild, allWords, prefix));
		}

		if (ptr.sibling!=null && ptr.firstChild!=null) {
			temp.addAll(retrieveSubstrings(ptr.sibling, allWords, prefix));
		}
	
		return temp;
	}

	private static int prefixCheck(TrieNode node, String prefix, String[] allWords) {

		String key = allWords[node.substr.wordIndex];
		int finalIndex = node.substr.endIndex;

		if (prefix.equals(key.substring(0, Math.min(finalIndex+1, prefix.length())))) {
			return 1;
		} else {
			if (prefix.length()-1 > finalIndex) {

				if ((prefix.substring(0, finalIndex+1).equals(key.substring(0, finalIndex+1)))) {
					return 2;
				}
			}
			return -1;
		}
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
