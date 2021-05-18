package friends;

import java.util.ArrayList;
import structures.Queue;
import structures.Stack;

// @author: Ashwin Anand aa2041 aa2041@scarletmail.rutgers.edu


public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {

	//exit with null if arguements are empty
	if (p1==null || p2==null || g == null) 
	{
		return null;
	}

	boolean[] visittrue = new boolean[g.members.length];
	
	Person[] traversed = new Person[g.members.length];
	
	//list for returned values
	ArrayList<String> shortlist = new ArrayList<String>();

	// load up the queue
	Queue<Person> que = new Queue<Person>();
		
	int ind = g.map.get(p1);
	
	que.enqueue(g.members[ind]);
	
	visittrue[ind] = true;
	
    //loop thru the queue with the logic of finding the shortest path 
	while (que.isEmpty() == false) 
	{
		Person plist = que.dequeue();
		
		int pindex = g.map.get(plist.name);
	
		visittrue[pindex] = true;
		
		Friend friendname = plist.first;
		
		// return back with null if the name is null
		if (friendname == null)
		{ 
			return null;
		}
		
		// make the shortlist of names for shortest path
		while (friendname != null) 
		{
			
			if (visittrue[friendname.fnum] == false) 
			{
				visittrue[friendname.fnum] = true;

				traversed[friendname.fnum] = plist; 

				que.enqueue(g.members[friendname.fnum]);
				
				if (g.members[friendname.fnum].name.equals(p2)) 
				{
					plist = g.members[friendname.fnum];
					
					while (plist.name.equals(p1) == false) 
					{
						shortlist.add(0, plist.name);

						plist = traversed[g.map.get(plist.name)];
					}
					shortlist.add(0, p1);

					return shortlist;
				}
			}
			friendname = friendname.next;
		}
	}

	return null;
}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
	
	// return null is input arguements are null
	if (g == null || school == null) 
	{
		return null;
	}

	boolean[] rectouched = new boolean[g.members.length];

	//create return list array
	ArrayList<ArrayList<String>> cliqueslist= new ArrayList<ArrayList<String>>();

	//call and return value from searching school values
	return bsearch(g, g.members[0], cliqueslist, school, rectouched);

	}	

	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		// return null if input graph is null
		if (g == null) 
		{
			return null;
		}
		
		ArrayList<String> earlierrec = new ArrayList<String>();	

		ArrayList<String> listofconnectors = new ArrayList<String>();
			
		int[] searchnum= new int[g.members.length];
		
		boolean[] visitstat = new boolean[g.members.length];
		
		int[] past = new int[g.members.length];
		
		for (int h = 0; h < g.members.length; h++)
		{
			if (visitstat[h] == false) 
			{
				//call depth first search helper method
				listofconnectors = dsearch(listofconnectors, g, visitstat, g.members[h], new int[] {0,0}, earlierrec, searchnum, past, true);
			}
		}
		
		return listofconnectors;
	}


	// Helpder method to perform search student cliques at a particular school based on breadth first search
	// start with a node and then traverse across node one by one, everytime a node is visited it is tagged
	// a combination of enqueue and dequeue is used to load up modes not accessed and then delete them once accessed
	private static ArrayList<ArrayList<String>> bsearch(Graph g, Person start, ArrayList<ArrayList<String>> lcliques, String school, boolean[] touched){
		
		Queue<Person> qlist = new Queue<Person>();
		
		qlist.enqueue(start);
		
		touched[g.map.get(start.name)] = true;
		
		ArrayList<String> results = new ArrayList<String>();
	
		Person pv = new Person();
		
		Friend personname;

		boolean var=false;
		
		if ( start.school == null || start.school.equals(school) == false) 
		{
			qlist.dequeue();
			
			for (int w = 0; w < touched.length; w++) 
			{
				if (touched[w] == false) 
				{
					return bsearch(g, g.members[w], lcliques, school, touched);
				}
			}
		}
		
		while (qlist.isEmpty() == false) 
		{
			
			pv = qlist.dequeue();
	
			personname = pv.first;
	
			results.add(pv.name);
			
			while (personname != null) 
			{
				
				if (touched[personname.fnum] == false) 
				{
					
					if (g.members[personname.fnum].school == null) 
					{
						var=true;
					}
					else 
					{
						if (g.members[personname.fnum].school.equals(school)) 
						{
							qlist.enqueue(g.members[personname.fnum]);
						}
					}
					touched[personname.fnum] = true;
				}
				personname = personname.next;
			}
		}
		
		if (results.isEmpty() && lcliques.isEmpty() == false) 
		{
			var=true;
		} 
		else 
		{
			lcliques.add(results);
		}
		
		for (int x = 0; x < touched.length; x++) 
		{
			if (touched[x] == false) 
			{
				return bsearch(g, g.members[x], lcliques, school, touched);
			}
		}		
		return lcliques;
	}

    //helpder method based on depth first search approach
	//search based on moving forward and backwards to ensure all nodes are traveresed, recurrsion is also implemented
	//each node traversed is tagged so it is not touched again
	private static ArrayList<String> dsearch(ArrayList<String> friconnect, Graph g, boolean[] visrec, Person start,  int[] count, ArrayList<String> backw, int[] numsearch, int[] back, boolean started){
		
		visrec[g.map.get(start.name)] = true;
		
		Friend f = start.first;
		
		numsearch[g.map.get(start.name)] = count[0];

		back[g.map.get(start.name)] = count[1];

		while (f!= null) 
		{
			
			if (visrec[f.fnum] == false) 
			{
				
				count[0]+=1;

				count[1]+=1;
				
				friconnect = dsearch(friconnect, g, visrec, g.members[f.fnum], count, backw, numsearch, back, false);
				
				if (numsearch[g.map.get(start.name)] <= back[f.fnum]) 
				{
					
					if ((friconnect.contains(start.name) == false && started == false) || (friconnect.contains(start.name) == false && backw.contains(start.name))) 
					{
						friconnect.add(start.name);
					}
				}
				else 
				{
					int f1 = back[g.map.get(start.name)];
					
					int f2 = back[f.fnum];
					
					if (f2 > f1) 
					{
						back[g.map.get(start.name)] = f1;
					}
					else 
					{
						back[g.map.get(start.name)] = f2;
					} 
				}		
			backw.add(start.name);

			}
			else 
			{
				int f3 = back[g.map.get(start.name)];

				int f4 = numsearch[f.fnum];
				
				if (f4 > f3) 
				{
					back[g.map.get(start.name)] = f3;
				}
				
				else
				{ 
					back[g.map.get(start.name)] = f4;
				}
			}
			f = f.next;
		}
		return friconnect;
	}
}