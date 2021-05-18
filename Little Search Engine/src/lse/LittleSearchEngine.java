package lse;

import java.io.*;
import java.util.*;

// @author: Ashwin Anand aa2041 aa2041@scarletmail.rutgers.edu

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		
		// Scanner object class to scan in words from text
		Scanner scannerobject = new Scanner(new File(docFile));
		
		//index hashmap to hold words being scanned in
		HashMap<String,Occurrence> index = new HashMap <String,Occurrence>();
		
		// while loop to scan in all words and put them in a hashmap
		while(scannerobject.hasNext())
		{
			String key = scannerobject.next();
			key = getKeyword(key);
		
			if(key != null)
			{
				Occurrence onetime = index.get(key);
		
				if(onetime!=null)
				{
					onetime.frequency++;
				}
				else
				{
					onetime = new Occurrence(docFile,1);
					index.put(key, onetime);
				}
			}
		}
		return index;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/

		// for loop to go through kws till its done scanning 
		for (Map.Entry<String, Occurrence> input : kws.entrySet())
		{
			Occurrence Occurval = input.getValue();
			String words = input.getKey();
			ArrayList<Occurrence> list = keywordsIndex.get(words);
			if(list!=null)
			{
				list.add(Occurval);
				insertLastOccurrence(list);
			} 
			else
			{
				list = new ArrayList<>();
				list.add(Occurval);
				keywordsIndex.put(words,list);
			}
		}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code

		word = word.toLowerCase();
		
		boolean val=true;

		// goes through a document and scans through for commas or colons or more
		while(val)
		{ 
			if(word.length()==0)
			{
				break;
			}
			char lastletters = word.charAt(word.length()-1);
			if(lastletters == '!' || lastletters == ';' 
			 || lastletters == ':' || lastletters == '?' || lastletters == '.' 
			 || lastletters == ',')
			{
              word = word.substring(0, word.length()-1);
			}
			else
			{
              break;
			}
		}
		// goes through full word to compar letter with character from a text file
		for(int counter = 0; counter < word.length();counter++)
		{
			char letter = word.charAt(counter);
			if(!Character.isLetter(letter))
			{
				word = null;
				break;
			}
		}
		if(word != null && word.length() == 0)
		{
			word = null;
		}
		if(word != null && noiseWords.contains(word))
		{
			word = null;
		}
		return word;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code

		ArrayList<Integer> listmiddle = new ArrayList<>();
		int freqoccurence = occs.get(occs.size()-1).frequency;
		int insertcount = 0;
		int middle = 0;
		int lowerbounds = 0;
		int upperbounds = occs.size()-2;
		boolean val=true;

		// goes through ocurences till occrurences are done or reach 0
		while(val)
		{
			middle = (lowerbounds + upperbounds)/2;
		
			listmiddle.add(middle);
		
			Occurrence middleoccurence = occs.get(middle);
		
			if(middleoccurence.frequency==freqoccurence)
			{
				insertcount = middle;
				break;
			}
			else if (middleoccurence.frequency<freqoccurence)
			{
				upperbounds = middle - 1;
		
				if(upperbounds<lowerbounds)
				{
					insertcount = middle;
					break;
				}
			}
			else 
			{
				lowerbounds = middle + 1;
		
				if(upperbounds<lowerbounds)
                {
				   insertcount = middle + 1;
				   break;
				}
			}
		} 
		
		if(insertcount != occs.size()-1)
		{
			Occurrence temphold = occs.get(occs.size()-1);
		
			occs.remove(occs.size()-1);
		
			occs.add(insertcount,temphold);
		}
			 
		return listmiddle;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code

		HashMap<String, String> returnList = new HashMap<>();
        ArrayList<String> finallist = new ArrayList<>();
        ArrayList<Occurrence> firstoccurence = keywordsIndex.get(kw1.toLowerCase());
        ArrayList<Occurrence> secondoccurence = keywordsIndex.get(kw2.toLowerCase());

        int count1 =0;
		int count2 =0;

        if (firstoccurence == null) 
		{
			count1 = -1;
		}
		else
		{
			count1 = 0;
		}

		if (secondoccurence == null) 
		{
			count2 = -1;
		}
		else
		{
			count2 = 0;
		}
		Occurrence temp1=null;
		Occurrence temp2=null;
        while ((count1 >= 0 || count2 >= 0) && (finallist.size() < 5)) 
		{
 
			if (count1 >= 0 ) 
			{
				temp1=firstoccurence.get(count1);
			}
			else
			{
				temp1=null;
			}
            
			if (count2 >= 0)
            {
       	
	  		 if (secondoccurence!= null) 
	   			{
		   			temp2=secondoccurence.get(count2);
	  			 }
	   
			}
            else
            {
                temp2 = null;
		    }

            Occurrence firsttime;
            
			if (temp1 == null) 
			{
                firsttime = temp2;
                count2++;
            } 
			else if (temp2 == null) 
			{
                firsttime = temp1;
                count1++;
            } 
			else 
			{
                if (temp1.frequency >= temp2.frequency) 
					{
                        firsttime = temp1;
                        count1 += 1;
                    } else 
					{
                        firsttime = temp2;
                        count2 += 1;
                    }
                }
                if (firsttime != null) 
				{
                    if (!returnList.containsKey(firsttime.document)) 
					{
                        finallist.add(firsttime.document);
                        returnList.put(firsttime.document, firsttime.document);
                    }
                }
                if (count1 == firstoccurence.size() && count1 >= 0)
                {
					count1 = -1;
				}
                if (secondoccurence != null && count2 == secondoccurence.size() && count2 >= 0)
				{
					count2 = -1;
				}
            }
			if (finallist.size()==0)
			{
				return null;
			}
			else
			{
				return finallist;
			}
         }
	
	}
