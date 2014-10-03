package com.foo.pkg;

import java.util.Collection;
import java.util.LinkedList;

/*
 * This is a supporting class
 * Trie is data-structure that works well 
 * with string representation
 * 
 */


public class Trie {
	
	
	/*
	 * inner class for individual nodes in the trie
	 */
	class TrieNode{
		char val; //stores actual character
		boolean wordEnd; //marker to denote word ending
		Collection<TrieNode> children; //list of all children
		
		public TrieNode(char ch){
			val = ch;
			wordEnd = false;
			children = new LinkedList<Trie.TrieNode>();
		}
		
		/*
		 * This method returns the child node which contains
		 * the character passed as parameter else returns null
		 */
		
		public TrieNode getChild(char ch){
			if(children != null){
				for(TrieNode child : children){
					if(child.val == ch)
						return child;
				}
			}
			return null;
		}
		
	}
	
	
	TrieNode root; //root is the first node in trie
	
	public Trie(){
		root = new TrieNode(' '); //dummy value at root
	}
	
	
	/*
	 * This method inserts a word into the trie
	 * and set appropriate markers to denote word ending
	 * Complexity of this method is O(n) where n is 
	 * length of the inserted word
	 */
	public void insertWord(String word){
		TrieNode current = root;
		int len = word.length();
		char ch;
		for(int i=0;i<len;i++){
			ch = word.charAt(i);
			TrieNode child = current.getChild(ch);
			if(child == null){
				// create new node if prefix not present
				current.children.add(new TrieNode(ch));
				current = current.getChild(ch);
			}
			else{
				current = child;
			}
			if(i == len - 1)
				current.wordEnd = true; //set marker to denote word end
		}
	}
	
	
	/*
	 * This is a search method that finds if a word exists within
	 * the trie. 
	 * Complexity is O(n) where n is length of the word
	 * 
	 */
	public boolean containsWord(String word){
		TrieNode current = root;
		int len = word.length();
		char ch;
		while(current != null){
			for(int i=0;i<len;i++){
				ch = word.charAt(i);
				if(current.getChild(ch) == null)
					return false;
				else
					current = current.getChild(ch);
			}
			if(current.wordEnd == true)
				return true;
			else
				return false;
		}
		return false;
	}
	
}
