package com.foo.pkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class Main {

	/* index to keep words sorted by length */
	TreeMap<Integer,ArrayList<String>> map;
	/* index to keep words sorted alphabetically */
	Trie trie;
	
	public Main(){
		map = new TreeMap<Integer, ArrayList<String>>();
		trie = new Trie();
	}
	
	
	/*
	 * This method creates both indexes by calling 
	 * insertWord() and buildMap()
	 */
	public void buildTrie(String word){
		//for(String word : words){
			trie.insertWord(word);
			buildMap(word);
		//}
	}
	
	/*
	 * This method groups words of same length into an ArrayList
	 * and indexes these lists by their length
	 */
	public void buildMap(String word){
		int key = word.length();
		ArrayList<String> wordList = map.get(key);
		if(wordList == null){
			wordList = new ArrayList<String>();
			wordList.add(word);
			map.put(key, wordList);
		}
		else{
			wordList.add(word);
		}
	}
	
	
	/*
	 * This method recursively checks the presence of compound words
	 * in the list
	 */
	public boolean isCompoundWord(String word,boolean isAtomicWord){
		
		// to filter atomic words
		if(trie.containsWord(word) && isAtomicWord == false)
			return true;
		
		for(int i=0;i<word.length();i++){
			String firstPart = word.substring(0, i);
			String lastPart = word.substring(i);
			if(trie.containsWord(firstPart) && isCompoundWord(lastPart, false)){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * This method gets the list of words from the map index
	 * then it checks to see if they are compound
	 * It also keeps a counter to count all such words in the list
	 */
	public void findLongestWord() throws IOException{
		
		Iterator<Integer> keySetIter = map.descendingKeySet().iterator();
		ArrayList<String> wordList;
		int countNumOfCompoundWords = 0;
		//String fileName = "/home/subhendu/Desktop/result.txt";
		//BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		
		while(keySetIter.hasNext()){
			wordList = map.get(keySetIter.next());
			for(String word : wordList){
				if(isCompoundWord(word,true)){
					countNumOfCompoundWords++;
					//bw.write(word);
					//bw.newLine();
					//if(countNumOfCompoundWords < 3)
						//prints only first two longest compound words
						//System.out.println(word);
				}
			}
		}
		//bw.flush();bw.close();
		System.out.println("Total number of compound words: " + countNumOfCompoundWords);
	}
	
	public static void main(String[] args) throws IOException {
		
		Main obj = new Main();
		String path = "wordsforproblem.txt";
		BufferedReader br = new BufferedReader(new FileReader(path));
		String word;
		//long start = System.currentTimeMillis();
		
		//read the file one line at a time and build both indexes
		while((word = br.readLine())!=null){
			obj.buildTrie(word);
		}
		br.close();
		
		long start = System.currentTimeMillis();
		obj.findLongestWord();
		long stop = System.currentTimeMillis();
		System.out.println("Execution time: " + (stop - start) + " ms");
	}

}
